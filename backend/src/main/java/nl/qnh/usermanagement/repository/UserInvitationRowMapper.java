package nl.qnh.usermanagement.repository;

import nl.qnh.usermanagement.model.User;
import nl.qnh.usermanagement.model.UserInvitation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class UserInvitationRowMapper implements RowMapper<UserInvitation> {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.ShortVariable")
    @Override
    public UserInvitation mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final UserInvitation userInvitation = new UserInvitation();
        userInvitation.setId(UUID.fromString(rs.getString("id")));

        final User user = new User();
        user.setId(rs.getLong("user_id"));
        userInvitation.setUser(user);
        userInvitation.setCreated(getDateTime(rs, "created"));
        userInvitation.setExpires(getDateTime(rs, "expires"));
        return userInvitation;
    }

    @SuppressWarnings({"PMD.ShortVariable", "PMD.PrematureDeclaration", "PMD.LawOfDemeter"})
    private LocalDateTime getDateTime(final ResultSet rs, final String key) throws SQLException {
        final Timestamp timestamp = rs.getTimestamp(key);
        if (rs.wasNull()) {
            return null;
        }
        return timestamp.toLocalDateTime();
    }
}
