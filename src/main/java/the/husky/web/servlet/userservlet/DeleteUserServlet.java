package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import the.husky.exception.ParseRequestException;
import the.husky.security.SecurityService;

import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    private SecurityService securityService;

    public DeleteUserServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        int id = parseIdParameter(idStr);

        securityService.deleteUser(id);
        response.sendRedirect("/user_all");
    }

    private int parseIdParameter(String idParam) {
        try {
            return Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            throw new ParseRequestException("Error, wrong ID.");
        }
    }
}

