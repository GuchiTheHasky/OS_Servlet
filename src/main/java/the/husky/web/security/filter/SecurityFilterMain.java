package the.husky.web.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;


public class SecurityFilterMain implements Filter {

    private final List<String> PERMITTED_URI = List.of("/login", "/user/add", "/task", "/favicon",
            "/image", "/user/all/*", "/vehicle/add", "/vehicle/all", "/user/edit", "/user/details",
            "/user/delete", "/vehicle/edit", "/vehicle/filter"); //todo if req.getUri.startWith.log.getValue filter.doFilter(req, res);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (isRequestPermitted(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request uri.");
        }
    }

    private boolean isRequestPermitted(HttpServletRequest request) {
        for (String uri : PERMITTED_URI) {
            if (request.getRequestURI().equals(uri)) {
                return true;
            }
        }
        return false;
    }
}


//    private boolean isUserAuthenticated() {
////        return securityService.getAuthenticatedUser() != null;
////    }


//        if (request.getRequestURI().equals("/login")) {
//            filterChain.doFilter(request, response);
//            return; // todo можна замінити на else
//        }
//        if (request.getRequestURI().equals("/css")) { // todo
//            filterChain.doFilter(request, response);
//            return;
//        }


//        if (securityService.isAuthentificate()) {
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            response.sendRedirect("/login");
//        }
//
//        System.out.println("Security filter");
