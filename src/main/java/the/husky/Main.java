package the.husky;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import the.husky.dao.jdbc.JdbcUserDao;
import the.husky.dao.jdbc.JdbcVehicleDao;
import the.husky.service.cache.CacheService;
import the.husky.security.SecurityService;
import the.husky.service.WebService;
import the.husky.web.filter.WebFilter;
import the.husky.service.entity.UserService;
import the.husky.service.entity.VehicleService;
import the.husky.web.servlet.AdminServlet;
import the.husky.web.servlet.LoginServlet;
import the.husky.web.servlet.LogoutServlet;
import the.husky.web.servlet.StaticResourceServlet;
import the.husky.web.servlet.userservlet.*;
import the.husky.web.servlet.vehicleservlet.AddVehicleServlet;
import the.husky.web.servlet.vehicleservlet.AllVehicleServlet;
import the.husky.web.servlet.vehicleservlet.DeleteVehicleServlet;
import the.husky.web.servlet.vehicleservlet.EditVehicleServlet;

import java.util.EnumSet;
import java.util.Map;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
//        Properties properties = DataSourceConnector.loadProperties();
//
//        final String jdbcUrl = properties.getProperty("db.flyway.url");
//        final String jdbcUser = properties.getProperty("db.user");
//        final String jdbcPassword = properties.getProperty("db.password");
//        Flyway flyway = Flyway.configure().dataSource(jdbcUrl, jdbcUser, jdbcPassword)
//                .load();
//        flyway.baseline();
//        flyway.migrate();

        JdbcUserDao userDao = new JdbcUserDao();
        JdbcVehicleDao vehicleDao = new JdbcVehicleDao();

        UserService userService = new UserService(userDao);
        VehicleService vehicleService = new VehicleService(vehicleDao);
        CacheService cacheService = new CacheService(userService, vehicleService);
        SecurityService securityService = new SecurityService();
        WebService webService = new WebService(securityService, cacheService);

        ServletContextHandler contextHandler = getServletContextHandler(webService);

        contextHandler.addFilter
                (new FilterHolder
                        (new WebFilter()), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(1025);
        server.setHandler(contextHandler);
        server.start();
    }

    private static Map<Servlet, String> servletMap(WebService webService) {
        return Map.of(
                new AdminServlet(webService), "/admin",
                new LoginServlet(webService), "/login",
                new AddUserServlet(webService), "/user_add",
                new AllUsersServlet(webService), "/user_all",
                new EditUserServlet(webService), "/user_edit/*",
                new DeleteUserServlet(webService), "/user/delete",
                new AddVehicleServlet(webService), "/vehicle_add", // !
                new AllVehicleServlet(webService), "/vehicle_all",
                new EditVehicleServlet(webService), "/vehicle_edit",
                new DeleteVehicleServlet(webService), "/vehicle/delete");
    }

    private static Map<Servlet, String> servletMap() {
        return Map.of(
                new LogoutServlet(), "/logout",
                new ForgotPasswordServlet(), "/task",
                new StaticResourceServlet(), "/static/*");
    }

    private static ServletContextHandler getServletContextHandler(WebService webService) {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        for (Map.Entry<Servlet, String> entry : servletMap().entrySet()) {
            contextHandler.addServlet(new ServletHolder(entry.getKey()), entry.getValue());
        }

        for (Map.Entry<Servlet, String> entry : servletMap(webService).entrySet()) {
            contextHandler.addServlet(new ServletHolder(entry.getKey()), entry.getValue());
        }

        return contextHandler;
    }
}

