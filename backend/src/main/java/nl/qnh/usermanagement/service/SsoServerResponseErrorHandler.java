package nl.qnh.usermanagement.service;

import nl.qnh.web.exception.UnauthorizedRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

/**
 * Handle SSO Server Response Errors.
 */
@Component
public class SsoServerResponseErrorHandler extends DefaultResponseErrorHandler {

    /**
     * The logger to use.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoServerResponseErrorHandler.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        LOGGER.error("Got error from SSO: '{} {}'.", response.getStatusCode(), response.getStatusText());
        if (HttpStatus.UNAUTHORIZED == response.getStatusCode()) {
            throw new UnauthorizedRequestException();
        }
    }
}
