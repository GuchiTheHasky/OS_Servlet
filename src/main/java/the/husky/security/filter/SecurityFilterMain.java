package the.husky.security.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
public class SecurityFilterMain implements Filter {

    private final List<String> PERMITTED_URI = List.of("/login", "/css/*", "/user/add", "/task", "/favicon.ico",
            "/image.png", "/vehicle/add", "/user/edit", "/user/details",
            "/user/delete", "/vehicle/edit","/user/all", "/vehicle/all", "/vehicle/filter");
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request, response);

//        if (isRequestPermitted(request)) {
//            filterChain.doFilter(request, response);
//        } else {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid request uri.");
//        }
//        if (request.getRequestURI().equalsIgnoreCase("/favicon.ico")) {
//            response.setContentType("image/vnd.microsoft.icon");
//            filterChain.doFilter(request, response);
//        }
    }

    private boolean isRequestPermitted(HttpServletRequest request) {
        for (String uri : PERMITTED_URI) {
            if (request.getRequestURI().startsWith(uri) || uri.contains("css")) {
                return true;
            }
        }
        return false;
    }
}