package nl.qnh.usermanagement.repository;

import nl.qnh.usermanagement.model.EmailAddress;
import org.springframework.jdbc.core.RowMapper;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * {@inheritDoc}
 * <p>
 * Maps a result set row onto a {@link nl.qnh.usermanagement.model.User}.
 */
public class EmailAddressRowMapper implements RowMapper<EmailAddress> {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.ShortVariable")
    @Override
    public EmailAddress mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final EmailAddress emailAddress = new EmailAddress();

        emailAddress.setConfirmed(getBoolean(rs, "email_confirmed"));
        final String email = rs.getString("email_address");
        try {
            emailAddress.setAddress(new InternetAddress(email));
        } catch (AddressException e) {
            throw new SQLException("Could not convert '" + email + "' into an email address.", e);
        }

        return emailAddress;
    }

    @SuppressWarnings({"PMD.ShortVariable", "PMD.PrematureDeclaration"})
    private Boolean getBoolean(final ResultSet rs, final String key) throws SQLException {
        final Boolean bool = rs.getBoolean(key);
        if (rs.wasNull()) {
            return null;
        }
        return bool;
    }
}
