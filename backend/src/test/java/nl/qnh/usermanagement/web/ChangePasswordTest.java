package nl.qnh.usermanagement.web;

import nl.qnh.usermanagement.web.input.ChangePasswordInput;
import org.junit.Before;
import org.junit.Test;

import static nl.qnh.usermanagement.web.controller.Paths.CHANGE_PASSWORD;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ChangePasswordTest extends UserManagementTestBase {

    protected String generatedEmailAddress;

    @Before
    public void setUp() throws Exception {
        this.generatedEmailAddress = generateEmailAddress();
    }

    @Test
    public void changePasswordFlow() throws Exception {
        Integer userId = createUser(generatedEmailAddress);

        ChangePasswordInput changePassword = new ChangePasswordInput();
        changePassword.setCurrentPassword(INITIAL_PASSWORD);
        changePassword.setNewPassword("some-new-password");

//        // Verify user must be logged in.
//        mockMvc.perform(
//                        post(CHANGE_PASSWORD, userId)
//                        .contentType(APPLICATION_JSON_UTF8)
//                        .content(toJsonString(changePassword))
//                    )
//                .andExpect(status().isUnauthorized());

        // Change the password from the initial password to ...
        mockMvc.perform(
                        post(CHANGE_PASSWORD, userId)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(changePassword))
                )
                .andExpect(status().isOk());

        // Change the password from the initial password to ... AGAIN
        mockMvc.perform(
                        post(CHANGE_PASSWORD, userId)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(toJsonString(changePassword))
                )
                .andExpect(status().isUnauthorized());

        deleteUser(userId);
    }
}
