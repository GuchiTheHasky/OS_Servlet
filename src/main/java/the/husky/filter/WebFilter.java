package the.husky.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import the.husky.entity.enums.Role;

import java.io.IOException;

@Component
@RequestMapping("/*")
public class WebFilter implements Filter {

    private final String[] userUri = {"/login", "/logout", "/task", "/answer", "/css/common.css", "/favicon/*",
            "/img/image.png", "/cart", "/vehicle_all", "/vehicle_filter_by_manufacturer", "/vehicle_filter_by_engine_type",
            "/vehicle_add", "/vehicle_edit", "/vehicle/delete", "/payment", "/cart"};

    private final String[] adminUri = {"/login", "/logout", "/task", "/answer", "/css/common.css", "/favicon/*", "/admin",
            "/img/image.png", "/cart", "/vehicle_all", "/vehicle_filter_by_manufacturer", "/vehicle_filter_by_engine_type",
            "/vehicle_add", "/vehicle_edit", "/vehicle/delete", "/cart", "/user_all", "/user_add", "/user_edit", "/user/delete"};

    private final String[] systemUri = {"/login", "/logout", "/task", "/answer", "/css/common.css", "/favicon/*",
            "/user_add", "/img/image.png"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();

        if (isSystemAccessLevel(uri)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("role") == null) {
            response.sendRedirect("/login");
        } else {
            if (isAdminAccessLevel(uri) && (session.getAttribute("role").equals(Role.ADMIN))) {
                filterChain.doFilter(request, response);
            } else if (isUserAccessLevel(uri) && session.getAttribute("role").equals(Role.USER)) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect("/login");
            }
        }
    }

    private boolean isUserAccessLevel(String uri) {
        for (String s : userUri) {
            if (uri.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdminAccessLevel(String uri) {
        for (String s : adminUri) {
            if (uri.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSystemAccessLevel(String uri) {
        for (String s : systemUri) {
            if (uri.equals(s)) {
                return true;
            }
        }
        return false;
    }
}
