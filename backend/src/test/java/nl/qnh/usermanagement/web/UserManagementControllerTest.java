package nl.qnh.usermanagement.web;

import nl.qnh.usermanagement.web.input.EmailAddressInput;
import nl.qnh.usermanagement.web.input.UserInput;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static nl.qnh.usermanagement.web.controller.Paths.USER;
import static nl.qnh.usermanagement.web.controller.Paths.USERS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserManagementControllerTest extends UserManagementTestBase {

    private static final String FIRST_NAME_REQUIRED = "{\"code\":\"required\",\"field\":\"first_name\"}";
    private static final String LAST_NAME_REQUIRED = "{\"code\":\"required\",\"field\":\"last_name\"}";
    private static final String EMAIL_ADDRESS_REQUIRED = "{\"code\":\"required\",\"field\":\"email_address\"}";
    private static final String ID_FORBIDDEN = "{\"code\":\"forbidden\",\"field\":\"id\"}";

    private int originalNumberOfUsers = 0;
    private String generatedEmailAddress;

    @Before
    public void setUp() throws Exception {
        // Get current users
        String content = mockMvc.perform(get(USERS))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        JSONArray array = new JSONArray(content);
        originalNumberOfUsers = array.length();

        this.generatedEmailAddress = generateEmailAddress();
    }

    @Test
    public void createUpdateAndDeleteUser() throws Exception {
        // Create new user
        JSONObject user = new JSONObject();
        user.put("first_name", "Test");
        user.put("last_name", "Doe");
        JSONObject email = new JSONObject();
        email.put("confirmed", false);
        email.put("email_address", generatedEmailAddress);
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

        Integer id = new JSONObject(content).getInt("id");

        content = mockMvc
                .perform(
                            get(USERS)
                        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        assertThat(new JSONArray(content).length(), is(originalNumberOfUsers + 1));

        // Update user
        user.put("id", id);
        user.put("first_name", "John");
        roles.remove(0);
        mockMvc.perform(
                        put(USER, id)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(user.toString())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));

        mockMvc.perform(
                        get(USER, id)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.first_name", is("John")))
                .andExpect(jsonPath("$.roles", containsInAnyOrder("super-admin")))
                .andReturn().getResponse().getContentAsString();


        // Delete user
        mockMvc.perform(
                    delete(USER, id)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(id)));

        content = mockMvc.perform(
                        get(USERS)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        assertThat(new JSONArray(content).length(), is(originalNumberOfUsers));
    }



    @Test
    public void getUnknownUserReturnsNotFound() throws Exception {
        mockMvc.perform(
                    get(USER, UNKNOWN_USER_ID)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void updateUnknownUserReturnsNotFound() throws Exception {
        mockMvc.perform(
                        put(USER, UNKNOWN_USER_ID)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content("{}")
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void deleteUnknownUserReturnsNotFound() throws Exception {
        mockMvc.perform(
                    delete(USER, UNKNOWN_USER_ID)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void createUserValidations() throws Exception {
        JSONObject user = new JSONObject();
        checkUserValidations(user, FIRST_NAME_REQUIRED, LAST_NAME_REQUIRED, EMAIL_ADDRESS_REQUIRED);

        user.put("first_name", "Jane");
        checkUserValidations(user, LAST_NAME_REQUIRED, EMAIL_ADDRESS_REQUIRED);

        user.put("last_name", "Doe");
        checkUserValidations(user, EMAIL_ADDRESS_REQUIRED);

        JSONObject emailAddress = new JSONObject();
        user.put("email_address", emailAddress);
        checkUserValidations(user, EMAIL_ADDRESS_REQUIRED);

        emailAddress.put("email_address", generatedEmailAddress);
        user.put("id", 101L);
        checkUserValidations(user, ID_FORBIDDEN);
    }

    private void checkUserValidations(JSONObject user, String... expectedErrors) throws Exception {
        String content = mockMvc.perform(
                        post(USERS)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(user.toString())
                )
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();

        JSONObject json = new JSONObject(content);
        JSONArray errors = json.getJSONArray("errors");
        String errorsAsString = errors.toString();
        for (String expectedError : expectedErrors) {
            assertThat(errorsAsString, containsString(expectedError));
        }

        assertThat(errorsAsString, errors.length(), is(expectedErrors.length));

        // check user is not created, after errors.
        content = mockMvc.perform(
                        get(USERS)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn().getResponse().getContentAsString();

        assertThat(new JSONArray(content).length(), is(originalNumberOfUsers));
    }
}
