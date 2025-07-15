package testing.class_project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controls query execution permissions based on client IP
 */
@Service
public class AccessControlService {

    private final HttpServletRequest request;
    private final IpConfig ipConfig;

    private final List<String> allowedIps = new ArrayList<>();

    public AccessControlService(HttpServletRequest request, IpConfig ipConfig) {
        this.request = request;
        this.ipConfig = ipConfig;
        allowedIps.add(IpConfig.JUAN_IP); // <- IP autorizada
    }

    public boolean isAllowed(String ip) {
        System.out.println("IP recibida para verificación: " + ip);
        return allowedIps.contains(ip);
    }

   

    /**
     * Checks if current client can execute query1
     *
     * @return true if client IP is mapped to credentials authorized for query1
     */
    public boolean canExecuteQuery1() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_1);
    }

    public boolean canExecuteQuery2() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_2);
    }

    public boolean canExecuteQuery3() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_3);
    }

    public boolean canExecuteQuery4() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_4);
    }

    public boolean canExecuteQuery5() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_5);
    }

    public boolean canExecuteQuery6() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_6);
    }

    public boolean canExecuteQuery7() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_7);
    }

    public boolean canExecuteQuery8() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_8);
    }

    public boolean canExecuteQuery9() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_9);
    }

    public boolean canExecuteQuery10() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_10);
    }

    public boolean canExecuteQuery11() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_11);
    }

    public boolean canExecuteQuery12() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_12);
    }

    public boolean canExecuteQuery13() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_13);
    }

    public boolean canExecuteQuery14() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_14);
    }
    public boolean canExecuteQuery15() {
        return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_15);
    }
    public boolean canExecuteQuery16() {
        //return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_16);
        return true;
    }
    public boolean canExecuteQuery17() {
        //return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_17);
        return true;
    }   
    public boolean canExecuteQuery18() {
        //return canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_18);
        return true;
    }   
    public boolean canExecuteQuery19() {
        //canExecuteQuery(IpConfig.JUAN_IP, IpConfig.QUERY_19);
        return true;
    }

    public boolean canExecuteQuery(String allowedUser, String requiredQuery) {
        var userData = ipConfig.getCredentialsForIp(request.getRemoteAddr());
        var userName = userData.name();
        var userQueries = userData.queries();
        System.out.println("Remote IP: " + request.getRemoteAddr());
        return userName.equals(allowedUser) && userQueries.contains(requiredQuery);
    }
}
