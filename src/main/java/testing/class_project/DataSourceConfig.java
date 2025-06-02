package testing.class_project;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Centralized configuration for multiple IP-based DataSources.
 * <p>
 * Main responsibilities:
 * 1. Creates a routing DataSource that selects connections based on client IP
 * 2. Manages connection pools for each database user
 * 3. Provides secure credentials through IP-based routing
 */

@Configuration
public class DataSourceConfig {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IpConfig ipConfig;

    /**
     * Creates routing DataSource that selects connection based on client IP.
     * Uses separate connection pool for each database user.
     */

    @Bean
    public DataSource dataSource() {
        var targetDataSources = buildAllDataSources();

        AbstractRoutingDataSource dataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return getCurrentIpDataSource();
            }
        };

        dataSource.setTargetDataSources(targetDataSources);
        return dataSource;
    }

    private Map<Object, Object> buildAllDataSources() {
        var dataSources = new HashMap<>();

        IpConfig.IP_CREDENTIAL_MAP.forEach((ip, ipData) -> {
            var username = ipData.name();
            var password = ipData.password();
            if (!dataSources.containsKey(username)) {
                dataSources.put(username, createDataSource(username, password));
            }
        });

        return dataSources;
    }

    private String getCurrentIpDataSource() {
        var ip = request.getRemoteAddr();
        return ipConfig.getCredentialsForIp(ip).name();
    }

    private DataSource createDataSource(String username, String password) {
        var dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/AlarmaVecinal");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
}