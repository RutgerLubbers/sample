package nl.qnh.usermanagement.repository;

import nl.qnh.usermanagement.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
public class UserRowMapper implements RowMapper<User> {

    /**
     * The email address rowmapper.
     */
    private final EmailAddressRowMapper emailAddressRowMapper;

    /**
     * The constructor.
     */
    public UserRowMapper() {
        this.emailAddressRowMapper = new EmailAddressRowMapper();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.ShortVariable")
    @Override
    public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final User user = new User();
        user.setId(rs.getLong("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));

        user.setEmailAddress(emailAddressRowMapper.mapRow(rs, rowNum));

        addSubject(rs.getString("subject_id"), user);

        return user;
    }

    private void addSubject(final String subjectId, final User user) {
        if (subjectId == null || subjectId.isEmpty()) {
            return;
        }

        user.setSubjectId(UUID.fromString(subjectId));
    }

}
