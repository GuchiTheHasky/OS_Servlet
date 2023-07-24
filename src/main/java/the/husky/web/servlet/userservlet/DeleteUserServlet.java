package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import the.husky.exception.ParseRequestException;
import the.husky.service.WebService;

import java.io.IOException;

@AllArgsConstructor
public class DeleteUserServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");
        int id = parseIdParameter(idStr);

        webService.getCacheService().deleteUser(id);
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

