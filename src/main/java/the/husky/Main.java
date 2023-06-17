package the.husky;

import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import the.husky.dao.jdbc.JdbcUserDao;
import the.husky.dao.jdbc.JdbcVehicleDao;
import the.husky.service.UserService;
import the.husky.service.VehicleService;
import the.husky.web.security.filter.SecurityFilterFavicon;
import the.husky.web.security.filter.user.SecurityFilterAddUser;
import the.husky.web.security.filter.SecurityFilterLogin;
import the.husky.web.security.filter.SecurityFilterMain;
import the.husky.web.security.SecurityService;
import the.husky.web.security.filter.user.SecurityFilterEditUser;
import the.husky.web.security.filter.user.SecurityFilterUsers;
import the.husky.web.security.filter.vehicle.SecurityFilterAddVehicle;
import the.husky.web.security.filter.vehicle.SecurityFilterDeleteVehicle;
import the.husky.web.security.filter.vehicle.SecurityFilterVehicles;
import the.husky.web.servlet.*;
import the.husky.web.servlet.userservlet.*;
import the.husky.web.servlet.vehicleservlet.*;

import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception {

        JdbcUserDao userDao = new JdbcUserDao();
        UserService userService = new UserService(userDao);

        SecurityService securityService = new SecurityService(userService);

        LoginServlet loginServlet = new LoginServlet(securityService);
        LogoServlet logoServlet = new LogoServlet();
        FaviconServlet faviconServlet = new FaviconServlet();
        CssStyleServlet cssStyleServlet = new CssStyleServlet();

        ValidationTaskServlet validationTaskServlet = new ValidationTaskServlet();
        AddUserServlet addUserServlet = new AddUserServlet(userService, securityService);
        GetAllUsersServlet getAllUsersServlet = new GetAllUsersServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet(userService, securityService);

        JdbcVehicleDao vehicleDao = new JdbcVehicleDao();
        VehicleService vehicleService = new VehicleService(vehicleDao);

        AddVehicleServlet addVehicleServlet = new AddVehicleServlet(vehicleService);
        GetAllVehicleServlet getAllVehicleServlet = new GetAllVehicleServlet(vehicleService);
        DeleteVehicleServlet deleteVehicleServlet = new DeleteVehicleServlet(vehicleService);
        EditVehicleServlet editVehicleServlet = new EditVehicleServlet(vehicleService);
        VehicleFilterServlet vehicleFilterServlet = new VehicleFilterServlet(vehicleService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(validationTaskServlet), "/task");
        contextHandler.addServlet(new ServletHolder(addUserServlet), "/user/add");
        contextHandler.addServlet(new ServletHolder(getAllUsersServlet), "/user/all/*");
        contextHandler.addServlet(new ServletHolder(logoServlet), "/image");
        contextHandler.addServlet(new ServletHolder(faviconServlet), "/favicon.ico");
        contextHandler.addServlet(new ServletHolder(addVehicleServlet), "/vehicle/add");
        contextHandler.addServlet(new ServletHolder(getAllVehicleServlet), "/vehicle/all");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user/edit");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user/details");
        contextHandler.addServlet(new ServletHolder(deleteUserServlet), "/user/delete");
        contextHandler.addServlet(new ServletHolder(deleteVehicleServlet), "/vehicle/delete");
        contextHandler.addServlet(new ServletHolder(editVehicleServlet), "/vehicle/edit");
        contextHandler.addServlet(new ServletHolder(vehicleFilterServlet), "/vehicle/filter");
        contextHandler.addServlet(new ServletHolder(cssStyleServlet), "/css/*");
        contextHandler.addServlet(new ServletHolder(cssStyleServlet), "/user/css/*");
        contextHandler.addServlet(new ServletHolder(cssStyleServlet), "/vehicle/css/*");

        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterMain()), "/*", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterFavicon()), "/favicon.ico", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterLogin(securityService)), "/login", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterAddUser(userService)), "/user/add",
                        EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterAddVehicle()), "/vehicle/add", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterDeleteVehicle(vehicleService)), "/vehicle/delete",
                        EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterDeleteVehicle(vehicleService)), "/vehicle/edit",
                        EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder(
                        new SecurityFilterUsers(securityService)), "/user/all",
                        EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder(
                        new SecurityFilterEditUser(userService)), "/user/edit",
                        EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter
                (new FilterHolder(
                        new SecurityFilterVehicles(securityService)), "/vehicle/all",
                        EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(1025);
        server.setHandler(contextHandler);
        server.start();
    }
}

// todo: можна зробити ContentTypeFilter і сетити тайп
// TODO: перейти на war
// todo: перейти на pom.xml