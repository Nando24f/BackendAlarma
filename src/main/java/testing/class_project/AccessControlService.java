package testing.class_project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controls query execution permissions based on client IP
 */
@Service
public class AccessControlService {

    private final HttpServletRequest request;
    private final IpConfig ipConfig;

    @Autowired
    public AccessControlService(HttpServletRequest request, IpConfig ipConfig) {
        this.request = request;
        this.ipConfig = ipConfig;
    }

    /**
     * Checks if current client can execute query1
     *
     * @return true if client IP is mapped to credentials authorized for query1
     */
    public boolean canExecuteQuery1() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_1);
    }

    public boolean canExecuteQuery2() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_2);
    }

    public boolean canExecuteQuery3() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_3);
    }

    public boolean canExecuteQuery4() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_4);
    }

    public boolean canExecuteQuery5() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_5);
    }

    public boolean canExecuteQuery6() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_6);
    }

    public boolean canExecuteQuery7() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_7);
    }

    public boolean canExecuteQuery8() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_8);
    }

    public boolean canExecuteQuery9() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_9);
    }

    public boolean canExecuteQuery10() {
        return canExecuteQuery(IpConfig.JUAN, IpConfig.QUERY_10);
    }

    public boolean canExecuteQuery(String allowedUser, String requiredQuery) {
        var userData = ipConfig.getCredentialsForIp(request.getRemoteAddr());
        var userName = userData.name();
        var userQueries = userData.queries();
        System.out.println("Remote IP: " + request.getRemoteAddr());
        return userName.equals(allowedUser) && userQueries.contains(requiredQuery);
    }
}
