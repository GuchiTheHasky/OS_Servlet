//package the.husky.security.filter.user;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import the.husky.entity.user.User;
//import the.husky.service.UserService;
//
//import java.io.IOException;
//import java.util.List;
//
//@AllArgsConstructor
//public class SecurityFilterAddUser implements Filter {
//    private UserService userService;
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        if (request.getMethod().equalsIgnoreCase("POST")) {
//            String name = request.getParameter("user_name");
//            String password = request.getParameter("password");
//
//            if (name.isEmpty() || password.isEmpty() || isUserExist(name)) {
//                response.sendError(HttpServletResponse.SC_BAD_REQUEST,
//                        "Required fields is empty or user is already exist.");
//            } else {
//                filterChain.doFilter(request, response);
//            }
//        } else if (request.getMethod().equalsIgnoreCase("GET")) {
//            filterChain.doFilter(request, response);
//        }
//    }
//
//    @SneakyThrows
//    private boolean isUserExist(String name) {
//        User currentUser = userService.getByName(name);
//        if (currentUser != null) {
//            List<User> users = userService.getAll();
//            for (User user : users) {
//                if (user.getName().equals(currentUser.getName())) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//}
