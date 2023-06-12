package the.husky;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import the.husky.dao.jdbc.JdbcUserDao;
import the.husky.dao.jdbc.JdbcVehicleDao;
import the.husky.entity.user.User;
import the.husky.service.UserService;
import the.husky.service.VehicleService;
import the.husky.web.auth.UserAuthenticate;
import the.husky.web.servlet.*;
import the.husky.web.servlet.userservlet.*;
import the.husky.web.servlet.vehicleservlet.*;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        JdbcUserDao userDao = new JdbcUserDao();
        UserService userService = new UserService(userDao);

        List<User> users = userService.getAll();
        UserAuthenticate userAuthenticate = new UserAuthenticate(users, userService);

        LoginServlet loginServlet = new LoginServlet(userService, userAuthenticate);
        LogoServlet logoServlet = new LogoServlet();
        FaviconServlet faviconServlet = new FaviconServlet();

        ValidationTaskServlet validationTaskServlet = new ValidationTaskServlet();
        AddUserServlet addUserServlet = new AddUserServlet(userService, userAuthenticate);
        GetAllUsersServlet getAllUsersServlet = new GetAllUsersServlet(userService);
        EditUserDetailsServlet editUserServlet = new EditUserDetailsServlet(userService);
        DeleteUserServlet deleteUserServlet = new DeleteUserServlet(userService, userAuthenticate);

        JdbcVehicleDao vehicleDao = new JdbcVehicleDao();
        VehicleService vehicleService = new VehicleService(vehicleDao);

        AddVehicleServlet addVehicleServlet = new AddVehicleServlet(vehicleService);
        GetAllVehicleServlet getAllVehicleServlet = new GetAllVehicleServlet(vehicleService);
        VehicleManufacturerServlet vehicleManufacturerServlet = new VehicleManufacturerServlet(vehicleService);
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
        contextHandler.addServlet(new ServletHolder(vehicleManufacturerServlet), "/vehicle/manufacturer");
        contextHandler.addServlet(new ServletHolder(deleteVehicleServlet), "/vehicle/delete");
        contextHandler.addServlet(new ServletHolder(editVehicleServlet), "/vehicle/edit");
        contextHandler.addServlet(new ServletHolder(vehicleFilterServlet), "/vehicle/filter");

        Server server = new Server(1025);
        server.setHandler(contextHandler);
        server.start();
    }
}


// TODO: підключити FlyWay або LiquiBase
// TODO: захист від <script>
// TODO: jetty-security
// TODO: забрати залежності між слоями,
//  зробити dependency injection, забрати singleton і статику, підключити шаблонізатори
// todo: зробити рандомну генерацію значень
