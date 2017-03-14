package nl.jetse.app;

import nl.qnh.usermanagement.UserManagementConfig;
import nl.qnh.web.filter.LoggingInterceptor;
import org.hawaiiframework.sql.ResourceSqlQueryResolver;
import org.hawaiiframework.sql.SqlQueryResolver;
import org.hawaiiframework.sql.SqlQueryResolverComposite;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application for spring boot.
 * This is the startup application.
 */
@SpringBootApplication(scanBasePackageClasses = {Jetse.class, UserManagementConfig.class, LoggingInterceptor.class})
@EnableCaching
public class Jetse {

    /**
     * Query resolver for Hawaii
     *
     * @return a ResourceSQLQueryResolver that loads the queries from the resources path
     */
    @Bean
    public SqlQueryResolver sqlQueryResolver() {
        // TODO: 14-2-2017 only load file system resource loader when running in dev profile
        final SqlQueryResolverComposite sqlQueryResolver = new SqlQueryResolverComposite();
        final List<SqlQueryResolver> resolvers = new ArrayList<>();
        final FileSystemResourceLoader fileSystemResourceLoader = new FileSystemResourceLoader();
        final ResourceSqlQueryResolver fileSystemQueryResolver = new ResourceSqlQueryResolver(fileSystemResourceLoader);
        fileSystemQueryResolver.setPrefix("src/main/resources/sql/jetse/");
        fileSystemQueryResolver.setSuffix(".sql");
        resolvers.add(fileSystemQueryResolver);

        final ResourceSqlQueryResolver resourceSqlQueryResolver = new ResourceSqlQueryResolver();
        resourceSqlQueryResolver.setPrefix("sql/jetse/");
        resourceSqlQueryResolver.setSuffix(".sql");
        resolvers.add(resourceSqlQueryResolver);

        sqlQueryResolver.setSqlQueryResolvers(resolvers);
        return sqlQueryResolver;
    }

    /**
     * The restTemplate to use in the application.
     *
     * @param builder
     * @return
     */
    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * starts the spring boot application
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Jetse.class, args);
    }
}
