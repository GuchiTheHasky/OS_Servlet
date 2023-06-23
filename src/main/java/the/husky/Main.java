package the.husky;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import the.husky.dao.jdbc.JdbcUserDao;
import the.husky.dao.jdbc.JdbcVehicleDao;
import the.husky.security.SecurityService;
import the.husky.service.UserService;
import the.husky.service.VehicleService;
import the.husky.web.servlet.*;
import the.husky.web.servlet.userservlet.*;
import the.husky.web.servlet.vehicleservlet.*;

import java.util.Objects;

public class Main {
    public static void main(String[] args) throws Exception {
        JdbcUserDao userDao = new JdbcUserDao();
        JdbcVehicleDao vehicleDao = new JdbcVehicleDao();

        UserService userService = new UserService(userDao);
        VehicleService vehicleService = new VehicleService(vehicleDao);
        SecurityService securityService = new SecurityService(userService);

        LoginServlet loginServlet = new LoginServlet();
        StaticResourceServlet resourceServlet = new StaticResourceServlet();
        FaviconServlet faviconServlet = new FaviconServlet();

        ValidationTaskServlet validationTaskServlet = new ValidationTaskServlet();
        AddUserServlet addUserServlet = new AddUserServlet(userService, securityService);
        GetAllUsersServlet getAllUsersServlet = new GetAllUsersServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet(userService, securityService);

        AddVehicleServlet addVehicleServlet = new AddVehicleServlet(vehicleService);
        GetAllVehicleServlet getAllVehicleServlet = new GetAllVehicleServlet(vehicleService);
        DeleteVehicleServlet deleteVehicleServlet = new DeleteVehicleServlet(vehicleService);
        EditVehicleServlet editVehicleServlet = new EditVehicleServlet(vehicleService);
        VehicleFilterServlet vehicleFilterServlet = new VehicleFilterServlet(vehicleService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        String resourceBase = Objects.requireNonNull(Main.class.getResource("/template")).toExternalForm();
        contextHandler.setResourceBase(resourceBase);

        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(validationTaskServlet), "/task");
        contextHandler.addServlet(new ServletHolder(addUserServlet), "/user_add");
        contextHandler.addServlet(new ServletHolder(getAllUsersServlet), "/user_all/*");

        contextHandler.addServlet(new ServletHolder(addVehicleServlet), "/vehicle/add");
        contextHandler.addServlet(new ServletHolder(getAllVehicleServlet), "/vehicle_all");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user_edit");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user/details");
        contextHandler.addServlet(new ServletHolder(deleteUserServlet), "/user_delete");
        contextHandler.addServlet(new ServletHolder(deleteVehicleServlet), "/vehicle/delete");
        contextHandler.addServlet(new ServletHolder(editVehicleServlet), "/vehicle/edit");
        contextHandler.addServlet(new ServletHolder(vehicleFilterServlet), "/vehicle/filter");

        // TODO: 21.06.2023 має бути просто мапінг на /static
        contextHandler.addServlet(new ServletHolder(faviconServlet), "/favicon.ico");

        contextHandler.addServlet(new ServletHolder(resourceServlet), "/static/*");

//        contextHandler.addFilter
//                (new FilterHolder
//                        (new SecurityFilterMain()), "/*", EnumSet.of(DispatcherType.REQUEST));


        Server server = new Server(1025);
        server.setHandler(contextHandler);
        server.start();
    }
}

// TODO: 21.06.2023 все, включно з фавіконом, окрім темплейтів засунути в папку static
// todo: common css це?
// todo: закреслені aligne в html позабирати
// todo: зробити акаунт на AWS ec2 і залити тули OS, але перед цим запакувати це в jar
// todo: структура мейн спочатку всі dao, services, servlets filter ?
// todo: залишити лише один фільтр і той повинен бути в папці web
// todo: в сервлетах краще зробити кетч Error
