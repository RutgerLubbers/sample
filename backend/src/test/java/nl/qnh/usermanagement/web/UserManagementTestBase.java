package nl.qnh.usermanagement.web;

import nl.qnh.BaseMockMvcTest;
import nl.qnh.usermanagement.web.input.CreateSsoAccountInput;
import nl.qnh.usermanagement.web.resource.UserInvitationResource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Ignore;

import java.util.UUID;

import static nl.qnh.usermanagement.web.controller.Paths.USER;
import static nl.qnh.usermanagement.web.controller.Paths.USERS;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATIONS;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATION_CREATE_ACCOUNT;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
public class UserManagementTestBase extends BaseMockMvcTest {
    protected static final Long UNKNOWN_USER_ID = -1L;

    protected static final String INITIAL_PASSWORD = "pwd";

    protected String generateEmailAddress() {
        return  UUID.randomUUID().toString() + "@test.lab";
    }

    protected Integer createUser(final String emailAddr) throws Exception {
        // Create new user
        JSONObject user = new JSONObject();
        user.put("first_name", "Test");
        user.put("last_name", "Doe");
        JSONObject email = new JSONObject();
        email.put("confirmed", false);
        email.put("email_address", emailAddr);
        user.put("email_address", email);

        JSONArray roles = new JSONArray();
        roles.put("admin");
        roles.put("super-admin");
        user.put("roles", roles);

        String content = mockMvc
                .perform(
                        post(USERS)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(user))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first_name", is("Test")))
                .andExpect(jsonPath("$.last_name", is("Doe")))
                .andExpect(jsonPath("$.roles", containsInAnyOrder("admin", "super-admin")))
                .andReturn().getResponse().getContentAsString();



        Integer userId = new JSONObject(content).getInt("id");

        // Create user invitation
        content = mockMvc.perform(
                        post(USER_INVITATIONS, userId)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        UserInvitationResource userInvitationResource = fromJsonStringToObject(content, UserInvitationResource.class);
        String invitationId = userInvitationResource.getId();

        CreateSsoAccountInput createSsoAccountInput = new CreateSsoAccountInput();
        createSsoAccountInput.setPassword(INITIAL_PASSWORD);

        mockMvc.perform(
                        post(USER_INVITATION_CREATE_ACCOUNT, invitationId)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(createSsoAccountInput))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.user_does_not_exist_in_sso", is(false)))
                .andExpect(jsonPath("$.invitation_accepted", is(true)));


        return userId;
    }


    protected void deleteUser(Integer userId) throws Exception {
        // Delete user
        mockMvc.perform(
                        delete(USER, userId)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(userId)));
    }
}
