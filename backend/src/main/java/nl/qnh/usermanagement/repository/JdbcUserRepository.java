package nl.qnh.usermanagement.repository;

import nl.qnh.usermanagement.model.EmailAddress;
import nl.qnh.usermanagement.model.User;
import org.hawaiiframework.sql.SqlQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

/**
 * {@inheritDoc}
 */
@SuppressWarnings("PMD.TooManyMethods")
@Repository
public class JdbcUserRepository implements UserRepository {

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
    public JdbcUserRepository(final DataSource dataSource, final SqlQueryResolver sqlQueryResolver) {
        this.sqlQueryResolver = requireNonNull(sqlQueryResolver);
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(final Long userId) {
        requireNonNull(userId);
        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        try {
            final User user = jdbcTemplate
                    .queryForObject(sqlQueryResolver.resolveSqlQuery("user-management/get_user_by_id"), params, new UserRowMapper());
            addRoles(user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("User '{}' not found.", userId);
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(final User user) {
        requireNonNull(user);
        final Map<String, Object> params = getSqlParameterMap(user);

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sqlQueryResolver.resolveSqlQuery("user-management/insert_user")
                , new MapSqlParameterSource(params)
                , keyHolder, new String[] {"id"});
        user.setId(getGeneratedId(keyHolder.getKey()));

        saveRoles(user);
        return user;

    }

    private Map<String, Object> getSqlParameterMap(final User user) {
        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        params.put("first_name", user.getFirstName());
        params.put("last_name", user.getLastName());
        params.put("subject_id", user.getSubjectId());

        addEmailAddress(params, user.getEmailAddress());
        return params;
    }

    private void addEmailAddress(final Map<String, Object> params, final EmailAddress emailAddress) {
        params.put("email_confirmed", emailAddress.isConfirmed());
        params.put("email_address", emailAddress.getAddressAsString());
    }

    /**
     * returns string value for a number solve LawOfDemeter warning
     *
     * @param number the non null value for a number
     * @return the string value
     */
    private Long getGeneratedId(final Number number) {
        requireNonNull(number);
        return number.longValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User deleteUser(final User user) {
        requireNonNull(user);

        deleteRoles(user);

        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());
        jdbcTemplate.update(sqlQueryResolver.resolveSqlQuery("user-management/delete_user_by_id"), params);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateUser(final User user) {
        requireNonNull(user);

        final Map<String, Object> params = getSqlParameterMap(user);
        jdbcTemplate.update(sqlQueryResolver.resolveSqlQuery("user-management/update_user"), new MapSqlParameterSource(params));

        saveRoles(user);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("PMD.LawOfDemeter") // fluent api (streams)
    @Override
    public List<User> findAll() {
        final List<User> users = jdbcTemplate.query(sqlQueryResolver.resolveSqlQuery("user-management/get_users"), new UserRowMapper());
        users.parallelStream().forEach(this::addRoles);

        return users;
    }

    private void addRoles(final User user) {
        requireNonNull(user);

        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("id", user.getId());

        @SuppressWarnings("PMD.ShortVariable")
        final List<String> roles = jdbcTemplate
                .query(sqlQueryResolver.resolveSqlQuery("user-management/get_user_roles"), params, (rs, i) -> rs.getString("role"));

        user.setRoles(roles);
    }

    private void saveRoles(final User user) {
        requireNonNull(user);

        deleteRoles(user);

        final List<SqlParameterSource> parameters = new ArrayList<>();
        for (final String role : user.getRoles()) {
            addRole(parameters, user, role);
        }
        jdbcTemplate.batchUpdate(sqlQueryResolver.resolveSqlQuery("user-management/insert_user_role"),
                parameters.toArray(new SqlParameterSource[] {}));
    }

    private void addRole(final List<SqlParameterSource> parameters, final User user, final String role) {
        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("user_id", user.getId());
        params.put("role", role);

        parameters.add(new MapSqlParameterSource(params));
    }

    private void deleteRoles(final User user) {
        requireNonNull(user);

        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("user_id", user.getId());
        jdbcTemplate.update(sqlQueryResolver.resolveSqlQuery("user-management/delete_user_roles"), new MapSqlParameterSource(params));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public User findUserBySubjectId(final UUID subjectId) {
        requireNonNull(subjectId);
        @SuppressWarnings("PMD.UseConcurrentHashMap")
        final Map<String, Object> params = new HashMap<>();
        params.put("subject_id", subjectId);
        try {
            final User user = jdbcTemplate
                    .queryForObject(sqlQueryResolver.resolveSqlQuery("user-management/get_user_by_subject_id"), params, new UserRowMapper());
            addRoles(user);
            return user;
        } catch (EmptyResultDataAccessException e) {
            LOGGER.debug("User with subjectId '{}' not found.", subjectId);
            return null;
        }
    }
}
