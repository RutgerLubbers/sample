package nl.qnh.usermanagement.repository;

import nl.qnh.usermanagement.model.UserInvitation;
import org.hawaiiframework.sql.SqlQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@Component
public class JdbcUserInvitationRepository implements UserInvitationRepository {

    /**
     * The logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcUserRepository.class);

    /**
     * The queryresolver to load the queries
     */
    private final SqlQueryResolver sqlQueryResolver;

    /**
     * The jdbc template to be used.
     */
    private final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Constructs the repository with a datasource and a queryresolver to be used with a jdbc template
     *
     * @param dataSource
     * @param sqlQueryResolver
     */
    @Autowired
    public JdbcUserInvitationRepository(final DataSource dataSource, final SqlQueryResolver sqlQueryResolver) {
        this.sqlQueryResolver = requireNonNull(sqlQueryResolver);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.ShortVariable")
    @Override
    public UserInvitation findById(final UUID id) {
        requireNonNull(id);
        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        try {
            return jdbcTemplate.queryForObject(sqlQueryResolver.resolveSqlQuery("user-invitation/get_user_invitation_by_id"), params,
                    new UserInvitationRowMapper());
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("UserInvitation '{}' not found.", id);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserInvitation delete(final UserInvitation userInvitation) {
        requireNonNull(userInvitation);

        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userInvitation.getId());
        jdbcTemplate.update(sqlQueryResolver.resolveSqlQuery("user-invitation/delete_user_invitation"), new MapSqlParameterSource(params));

        return userInvitation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserInvitation create(final UserInvitation userInvitation) {
        requireNonNull(userInvitation);
        requireNonNull(userInvitation.getUser());

        userInvitation.setId(UUID.randomUUID());

        final Map<String, Object> params = getSqlParameterMap(userInvitation);
        jdbcTemplate.update(sqlQueryResolver.resolveSqlQuery("user-invitation/insert_user_invitation"), new MapSqlParameterSource(params));

        return userInvitation;
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private Map<String, Object> getSqlParameterMap(final UserInvitation userInvitation) {
        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userInvitation.getId());
        params.put("user_id", userInvitation.getUser().getId());
        params.put("created", userInvitation.getCreated());
        params.put("expires", userInvitation.getExpires());
        return params;
    }
}
