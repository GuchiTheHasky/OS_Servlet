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
import the.husky.web.filter.WebFilter;
import the.husky.service.UserService;
import the.husky.service.VehicleService;
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
        SecurityService securityService = new SecurityService(userService);

        AdminServlet adminServlet = new AdminServlet();
        LoginServlet loginServlet = new LoginServlet(securityService);
        LogoutServlet logoutServlet = new LogoutServlet();
        StaticResourceServlet resourceServlet = new StaticResourceServlet();

        ForgotPasswordServlet forgotPasswordServlet = new ForgotPasswordServlet();
        AddUserServlet addUserServlet = new AddUserServlet(securityService);
        AllUsersServlet allUsersServlet = new AllUsersServlet(userService);
        EditUserServlet editUserServlet = new EditUserServlet(userService);
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet(securityService);

        AddVehicleServlet addVehicleServlet = new AddVehicleServlet(vehicleService);
        AllVehicleServlet allVehicleServlet = new AllVehicleServlet(vehicleService);
        DeleteVehicleServlet deleteVehicleServlet = new DeleteVehicleServlet(vehicleService);
        EditVehicleServlet editVehicleServlet = new EditVehicleServlet(vehicleService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(adminServlet), "/admin");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(logoutServlet), "/logout");
        contextHandler.addServlet(new ServletHolder(forgotPasswordServlet), "/task");
        contextHandler.addServlet(new ServletHolder(addUserServlet), "/user_add");
        contextHandler.addServlet(new ServletHolder(allUsersServlet), "/user_all");

        contextHandler.addServlet(new ServletHolder(addVehicleServlet), "/vehicle_add");
        contextHandler.addServlet(new ServletHolder(allVehicleServlet), "/vehicle_all");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user_edit/*");
        contextHandler.addServlet(new ServletHolder(editUserServlet), "/user/details");
        contextHandler.addServlet(new ServletHolder(deleteUserServlet), "/user/delete");
        contextHandler.addServlet(new ServletHolder(deleteVehicleServlet), "/vehicle/delete");
        contextHandler.addServlet(new ServletHolder(editVehicleServlet), "/vehicle_edit");

        contextHandler.addServlet(new ServletHolder(resourceServlet), "/static/*");

        contextHandler.addFilter
                (new FilterHolder
                        (new WebFilter()), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(1025);
        server.setHandler(contextHandler);
        server.start();
    }
}

// todo: з 10-ї хвилини пояснення по токену
// заюзати javafaker для генерації випадкових даних
// забрати прямий доступ до кешу
// токен генерується в секьюріті сервісі
// зробити кеш юзерів можливо мапу (id, password+sol)
// юзер токен генерується секюріті сервісом, а куку можна в сервлеті робити
// куку не можна робити в секюріті сервісі
// !!! перевірити конкатенацію шляхів
//

// Сесію генерує SecurityService, метод повинен приймати Credentials (сутність яка в собі логін і пароль тримає)
