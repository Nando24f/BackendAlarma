package testing.class_project;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * Manages the mapping between client IP addresses and database credentials.
 * <p>
 * Provides a centralized configuration for IP-based database access control.
 */
@Component
public class IpConfig {

    public record IpData(String name, List<String> queries, String password) {
    }

    public static final String JUAN_IP = "200.13.4.251";
    public static final String JUAN = "juan";

    public static final String QUERY_1 = "query1";
    public static final String QUERY_2 = "query2";
    public static final String QUERY_3 = "query3";
    public static final String QUERY_4 = "query4";
    public static final String QUERY_5 = "query5";
    public static final String QUERY_6 = "query6";
    public static final String QUERY_7 = "query7";
    public static final String QUERY_8 = "query8";
    public static final String QUERY_9 = "query9";
    public static final String QUERY_10 = "query10";
    public static final String QUERY_CALLES = "query-calles"; // Cambiado de QUERY_11 a QUERY_CALLES

    public static final Map<String, IpData> IP_CREDENTIAL_MAP = Map.of(
            JUAN_IP, new IpData(JUAN,
                    List.of(QUERY_1, QUERY_2, QUERY_3, QUERY_4, QUERY_5,
                            QUERY_6, QUERY_7, QUERY_8, QUERY_9, QUERY_10, QUERY_CALLES), // Añadido QUERY_CALLES
                    "mypass543")
    );

    public static final IpData DEFAULT_CREDENTIALS
            = new IpData("no_access", List.of(), "");

    public IpData getCredentialsForIp(String ip) {
        return IP_CREDENTIAL_MAP.getOrDefault(ip, DEFAULT_CREDENTIALS);
    }
}