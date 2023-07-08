package the.husky;

import jakarta.servlet.DispatcherType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import the.husky.dao.jdbc.JdbcUserDao;
import the.husky.dao.jdbc.JdbcVehicleDao;
import the.husky.security.SecurityService;
import the.husky.security.filter.SecurityFilterMain;
import the.husky.service.UserService;
import the.husky.service.VehicleService;
import the.husky.web.servlet.LoginServlet;
import the.husky.web.servlet.StaticResourceServlet;
import the.husky.web.servlet.userservlet.*;
import the.husky.web.servlet.vehicleservlet.*;

import java.util.EnumSet;
import java.util.Objects;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
//        Properties properties = DataSourceConnector.loadProperties();

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
        SecurityService securityService = new SecurityService(userService);

        LoginServlet loginServlet = new LoginServlet(securityService);
        StaticResourceServlet resourceServlet = new StaticResourceServlet();

        ValidationTaskServlet validationTaskServlet = new ValidationTaskServlet();
        AddUserServlet addUserServlet = new AddUserServlet(securityService);
        GetAllUsersServlet getAllUsersServlet = new GetAllUsersServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet(securityService);

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
        contextHandler.addServlet(new ServletHolder(getAllUsersServlet), "/user_all");

        contextHandler.addServlet(new ServletHolder(addVehicleServlet), "/vehicle_add");
        contextHandler.addServlet(new ServletHolder(getAllVehicleServlet), "/vehicle_all");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user_edit/*");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user/details");
        contextHandler.addServlet(new ServletHolder(deleteUserServlet), "/user/delete");
        contextHandler.addServlet(new ServletHolder(deleteVehicleServlet), "/vehicle/delete");
        contextHandler.addServlet(new ServletHolder(editVehicleServlet), "/vehicle_edit");
        contextHandler.addServlet(new ServletHolder(vehicleFilterServlet), "/vehicle/filter");

        contextHandler.addServlet(new ServletHolder(resourceServlet), "/static/*");

        contextHandler.addFilter
                (new FilterHolder
                        (new SecurityFilterMain()), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(1025);
        server.setHandler(contextHandler);
        server.start();
    }
}

// todo: забрати папку статік з папки теиплейт
// todo: кожна перемінна сервіса повинна мати повну назву
// todo: VehicleFilterServlet не потрібний на 5-й хвилині є пояснення
// todo: SecurityFilterMain забрати суфікс Main
// todo: не треба сервлету для фавікона + можна додати тіку + заюзати IOUtils + треба перевірити, можливо справиться без тіки
// todo: з 10-ї хвилини пояснення по токену
// todo:
