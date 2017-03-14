package nl.qnh.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor that does some basic logging.
 *
 * {@inheritDoc}
 */
public class LoggingInterceptor implements HandlerInterceptor {

    /**
     * The logger to use.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    /**
     * Transform the request into a log line.
     */
    private String getLogLine(final HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
            throws Exception {
        LOGGER.info("Invoked '{}'.", getLogLine(request));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView)
            throws Exception {
        // Nothing to do.
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final Exception exception)
            throws Exception {
        LOGGER.info("End of '{}'.", getLogLine(request));
    }


}
