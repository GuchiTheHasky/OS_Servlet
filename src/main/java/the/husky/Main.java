package the.husky;

import com.husky.container.context.ApplicationContext;
import com.husky.container.context.ClassPathApplicationContext;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.flywaydb.core.Flyway;
import the.husky.dao.loader.PropertiesLoader;
import the.husky.web.filter.PaymentFilter;
import the.husky.web.filter.WebFilter;
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
import java.util.Properties;

@Slf4j
public class Main {
    public static void main(String[] args) throws Exception {
//        Properties properties = PropertiesLoader.loadProperties();
//
//        final String jdbcUrl = properties.getProperty("db.flyway.url");
//        final String jdbcUser = properties.getProperty("db.user");
//        final String jdbcPassword = properties.getProperty("db.password");
//        final String jdbcLocations = properties.getProperty("db.flyway.locations");
//        Flyway flyway = Flyway.configure().dataSource(jdbcUrl, jdbcUser, jdbcPassword)
//                .locations(jdbcLocations)
//                .load();
//        flyway.migrate();

        ApplicationContext context = new ClassPathApplicationContext(
                "/context/dao_context.xml",
                "/context/service_context.xml",
                "/context/servlet_context.xml",
                "/context/filter_context.xml"
        );

        ServletContextHandler contextHandler = getServletContextHandler(context);
        addFilter(contextHandler, context);

        Server server = new Server(1024);
        server.setHandler(contextHandler);
        server.start();
    }

    private static Map<Servlet, String> userServletMap(ApplicationContext context) {
        AddUserServlet addUserServlet = context.getBean(AddUserServlet.class);
        AllUsersServlet allUsersServlet = context.getBean(AllUsersServlet.class);
        EditUserServlet editUserServlet = context.getBean(EditUserServlet.class);
        DeleteUserServlet deleteUserServlet = context.getBean(DeleteUserServlet.class);
        ForgotPasswordServlet forgotPasswordServlet = context.getBean(ForgotPasswordServlet.class);
        CartServlet cartServlet = context.getBean(CartServlet.class);
        PaymentServlet paymentServlet = context.getBean(PaymentServlet.class);
        return Map.of(
                addUserServlet, "/user_add",
                allUsersServlet, "/user_all",
                editUserServlet, "/user_edit/*",
                deleteUserServlet, "/user/delete",
                forgotPasswordServlet, "/task",
                cartServlet, "/cart",
                paymentServlet, "/card");
    }

    private static Map<Servlet, String> vehicleServletMap(ApplicationContext context) {
        AddVehicleServlet addVehicleServlet = context.getBean(AddVehicleServlet.class);
        AllVehicleServlet allVehicleServlet = context.getBean(AllVehicleServlet.class);
        EditVehicleServlet editVehicleServlet = context.getBean(EditVehicleServlet.class);
        DeleteVehicleServlet deleteVehicleServlet = context.getBean(DeleteVehicleServlet.class);
        return Map.of(
                addVehicleServlet, "/vehicle_add",
                allVehicleServlet, "/vehicle_all",
                editVehicleServlet, "/vehicle_edit",
                deleteVehicleServlet, "/vehicle/delete");
    }

    private static Map<Servlet, String> systemServletMap(ApplicationContext context) {
        AdminServlet adminServlet = context.getBean(AdminServlet.class);
        LoginServlet loginServlet = context.getBean(LoginServlet.class);
        LogoutServlet logoutServlet = context.getBean(LogoutServlet.class);
        StaticResourceServlet staticResourceServlet = context.getBean(StaticResourceServlet.class);

        return Map.of(
                adminServlet, "/admin",
                loginServlet, "/login",
                logoutServlet, "/logout",
                staticResourceServlet, "/static/*");
    }

    private static ServletContextHandler getServletContextHandler(ApplicationContext context) {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        for (Map.Entry<Servlet, String> entry : userServletMap(context).entrySet()) {
            contextHandler.addServlet(new ServletHolder(entry.getKey()), entry.getValue());
        }

        for (Map.Entry<Servlet, String> entry : vehicleServletMap(context).entrySet()) {
            contextHandler.addServlet(new ServletHolder(entry.getKey()), entry.getValue());
        }

        for (Map.Entry<Servlet, String> entry : systemServletMap(context).entrySet()) {
            contextHandler.addServlet(new ServletHolder(entry.getKey()), entry.getValue());
        }
        return contextHandler;
    }

    private static void addFilter(ServletContextHandler contextHandler, ApplicationContext context) {
        WebFilter webFilter = context.getBean(WebFilter.class);
        contextHandler.addFilter
                (new FilterHolder
                        (webFilter), "/*", EnumSet.of(DispatcherType.REQUEST));
        PaymentFilter paymentFilter = context.getBean(PaymentFilter.class);
        contextHandler.addFilter
                (new FilterHolder
                        (paymentFilter), "/card", EnumSet.of(DispatcherType.REQUEST));
    }
}
