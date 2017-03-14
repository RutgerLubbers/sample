package nl.qnh.usermanagement.web.controller;


import nl.qnh.usermanagement.model.User;
import nl.qnh.usermanagement.service.UserManagementService;
import nl.qnh.usermanagement.web.input.ChangePasswordInput;
import nl.qnh.usermanagement.web.input.ChangePasswordInputValidator;
import nl.qnh.usermanagement.web.input.CreateUserInputValidator;
import nl.qnh.usermanagement.web.input.UserInput;
import nl.qnh.usermanagement.web.input.UserInputConverter;
import nl.qnh.usermanagement.web.resource.UserResourceAssembler;
import org.hawaiiframework.web.annotation.Delete;
import org.hawaiiframework.web.annotation.Get;
import org.hawaiiframework.web.annotation.Post;
import org.hawaiiframework.web.annotation.Put;
import org.hawaiiframework.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static nl.qnh.usermanagement.web.controller.Paths.CHANGE_PASSWORD;
import static nl.qnh.usermanagement.web.controller.Paths.USER;
import static nl.qnh.usermanagement.web.controller.Paths.USERS;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST interface for User Management.
 */
@RestController
public class UserManagementController {

    /**
     * The User -> UserResource assembler.
     */
    private final UserResourceAssembler userResourceAssembler;

    /**
     * The User management service.
     */
    private final UserManagementService userManagementService;

    /**
     * The UserInput -> User converter.
     */
    private final UserInputConverter userInputConverter;

    /**
     * The create user input validator.
     */
    private final CreateUserInputValidator createUserInputValidator;

    /**
     * The change password input validator.
     */
    private final ChangePasswordInputValidator changePasswordInputValidator;

    /**
     * The constructor.
     */
    @Autowired
    public UserManagementController(final UserResourceAssembler userResourceAssembler
            , final UserManagementService userManagementService
            , final UserInputConverter userInputConverter
            , final CreateUserInputValidator createUserInputValidator
            , final ChangePasswordInputValidator changePasswordInputValidator) {
        this.userResourceAssembler = requireNonNull(userResourceAssembler);
        this.userManagementService = requireNonNull(userManagementService);
        this.userInputConverter = requireNonNull(userInputConverter);
        this.createUserInputValidator = requireNonNull(createUserInputValidator);
        this.changePasswordInputValidator = requireNonNull(changePasswordInputValidator);
    }

    /**
     * Create a new user.
     *
     * @param userInput the user to create.
     * @return the created user.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Post(path = USERS, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@RequestBody final UserInput userInput) {
        createUserInputValidator.validateAndThrow(userInput);

        final User user = userInputConverter.convert(userInput);

        userManagementService.createUser(user);

        return ResponseEntity.ok().body(userResourceAssembler.toResource(user));
    }

    /**
     * Retrieve (find) a list of users
     *
     * @return a possibly empty list of users.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Get(path = USERS, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUsers() {
        final List<User> users = userManagementService.findAll();

        return ResponseEntity.ok().body(userResourceAssembler.toResources(users));
    }

    /**
     * Update a user.
     *
     * @param userId    the user's id
     * @param userInput the user to updateUser (with the modifications).
     * @return the modified user or HTTP_NOT_FOUND if the user is not present.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Put(path = USER, produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable final Long userId, @RequestBody final UserInput userInput) {
        final User user = userInputConverter.convert(userInput);

        userManagementService.getUser(userId).orElseThrow(ResourceNotFoundException::new);
        userManagementService.updateUser(user);

        return ResponseEntity.ok().body(userResourceAssembler.toResource(user));
    }

    /**
     * Retrieve a user.
     *
     * @param userId the user's id.
     * @return the user or HTTP_NOT_FOUND if the user is not present.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Get(path = USER, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUser(@PathVariable final Long userId) {
        final User user = userManagementService.getUser(userId).orElseThrow(ResourceNotFoundException::new);

        return ResponseEntity.ok().body(userResourceAssembler.toResource(user));
    }

    /**
     * Delete a user.
     *
     * @param userId the user's id.
     * @return HTTP_OK or HTTP_NOT_FOUND if the user is not present.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Delete(path = USER, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteUser(@PathVariable final Long userId) {
        final User user = userManagementService.getUser(userId).orElseThrow(ResourceNotFoundException::new);
        userManagementService.deleteUser(user);

        return ResponseEntity.ok().body(userResourceAssembler.toResource(user));
    }

    /**
     * Change a user's password.
     *
     * @param userId the user's id.
     * @return HTTP_OK or HTTP_NOT_FOUND if the user is not present.
     */
    @SuppressWarnings("PMD.LawOfDemeter")
    @Post(path = CHANGE_PASSWORD, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@PathVariable final Long userId, @RequestBody final ChangePasswordInput changePasswordInput) {
        changePasswordInputValidator.validateAndThrow(changePasswordInput);

        final User user = userManagementService.getUser(userId).orElseThrow(ResourceNotFoundException::new);

        userManagementService.changePassword(user, changePasswordInput.getCurrentPassword(), changePasswordInput.getNewPassword());

        return ResponseEntity.ok().build();
    }

}
