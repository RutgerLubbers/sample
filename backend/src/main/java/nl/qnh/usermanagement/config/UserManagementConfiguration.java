package nl.qnh.usermanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Class to hold the configuration properties for user management.
 */
@Component
@ConfigurationProperties(prefix = "hawaii.userManagement")
public class UserManagementConfiguration {

    /**
     * The property for user invitation expiration in days. May be null.
     */
    private Integer userInvitationExpiresInDays;

    @SuppressWarnings("PMD.CommentRequired")
    public Integer getUserInvitationExpiresInDays() {
        return userInvitationExpiresInDays;
    }

    @SuppressWarnings("PMD.CommentRequired")
    public void setUserInvitationExpiresInDays(final Integer userInvitationExpiresInDays) {
        this.userInvitationExpiresInDays = userInvitationExpiresInDays;
    }

}
