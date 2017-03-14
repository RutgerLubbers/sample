package nl.qnh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.jetse.app.Jetse;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * The base controller test for testing controllers.
 */
@SpringBootTest(classes = Jetse.class)
@Ignore
public class BaseMockMvcTest extends AbstractSecuredMockMvcTestBase {

    /**
     * The JSON object mapper.
     */
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Converts the object to a JSON {@code String} using the underlying {@code {@link ObjectMapper}}.
     * Used for converting input objects to JSON.
     *
     * @param object the object to convert
     * @return the JSON as a string
     */
    protected String toJsonString(final Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Converts the JSON {@code String} to a value object.
     *
     * @param json      the JSON as a string
     * @param valueType the type of the value object
     * @param <T>       the type to return
     * @return the value object
     */
    protected <T> T fromJsonStringToObject(String json, Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }
}
