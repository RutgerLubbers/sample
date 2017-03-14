package nl.qnh.usermanagement.web;

import nl.qnh.usermanagement.web.input.CreateSsoAccountInput;
import nl.qnh.usermanagement.web.input.EmailAddressInput;
import nl.qnh.usermanagement.web.input.UserInput;
import nl.qnh.usermanagement.web.resource.UserInvitationResource;
import nl.qnh.usermanagement.web.resource.UserResource;
import org.junit.Before;
import org.junit.Test;

import static nl.qnh.usermanagement.web.controller.Paths.USER;
import static nl.qnh.usermanagement.web.controller.Paths.USERS;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATION;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATIONS;
import static nl.qnh.usermanagement.web.controller.Paths.USER_INVITATION_CREATE_ACCOUNT;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserInvitationControllerTest extends UserManagementTestBase {

    private String generatedEmailAddress;

    @Before
    public void setUp() throws Exception {
        this.generatedEmailAddress = generateEmailAddress();
    }



    @Test
    public void testInviteUser() throws Exception {
        // Create new user
        UserInput userInput = new UserInput();
        userInput.setFirstName("Test");
        userInput.setLastName("Doe");
        EmailAddressInput emailAddressInput = new EmailAddressInput();
        emailAddressInput.setEmailAddress(generatedEmailAddress);
        emailAddressInput.setConfirmed(false);
        userInput.setEmailAddress(emailAddressInput);

        String content = mockMvc
                .perform(
                        post(USERS)
                                .contentType(APPLICATION_JSON_UTF8)
                                .content(toJsonString(userInput))
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        UserResource userResource = fromJsonStringToObject(content, UserResource.class);
        Integer id = userResource.getId().intValue();

        // Create user invitation
        content = mockMvc
                .perform(
                        post(USER_INVITATIONS, id)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        UserInvitationResource userInvitationResource = fromJsonStringToObject(content, UserInvitationResource.class);
        String invitationId = userInvitationResource.getId();

        assertThat(invitationId, is(notNullValue()));

        // Accept user invitation
        mockMvc.perform(
                post(USER_INVITATION, invitationId)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.user_does_not_exist_in_sso", is(true)))
                .andExpect(jsonPath("$.invitation_accepted", is(false)));

        CreateSsoAccountInput createSsoAccountInput = new CreateSsoAccountInput();
        createSsoAccountInput.setPassword("I-am-hard-to-guess");

        mockMvc.perform(
                post(USER_INVITATION_CREATE_ACCOUNT, invitationId)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(createSsoAccountInput))
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.user_does_not_exist_in_sso", is(false)))
                .andExpect(jsonPath("$.invitation_accepted", is(true)));

        // Delete user
        mockMvc.perform(
                delete(USER, id)
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(id)));
    }

}
