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

    public static final String JUAN_IP = " 179.57.14.210";
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
    public static final String QUERY_11 = "query11";
    public static final String QUERY_12 = "query12";
    public static final String QUERY_13 = "query13";
    public static final String QUERY_14 = "query14";
    public static final String QUERY_15 = "query15";
    public static final String QUERY_16 = "query16";
    public static final String QUERY_17 = "query17";
    public static final String QUERY_18 = "query18";
    public static final String QUERY_19 = "query19";

    public static final Map<String, IpData> IP_CREDENTIAL_MAP = Map.of(
            JUAN_IP, new IpData(JUAN,
                    List.of(QUERY_1, QUERY_2, QUERY_3, QUERY_4, QUERY_5,
                            QUERY_6, QUERY_7, QUERY_8, QUERY_9, QUERY_10, QUERY_2, QUERY_11, 
                            QUERY_12, QUERY_13, QUERY_14, QUERY_15,QUERY_16, QUERY_17, 
                            QUERY_18,QUERY_19),
                    "mypass543")
    );

    public static final IpData DEFAULT_CREDENTIALS
            = new IpData("no_access", List.of(), "");

    public IpData getCredentialsForIp(String ip) {
        return IP_CREDENTIAL_MAP.getOrDefault(ip, DEFAULT_CREDENTIALS);
    }
}
