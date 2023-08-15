package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import the.husky.security.entity.Session;
import the.husky.service.WebService;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordServlet extends HttpServlet {
    private WebService webService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("task.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String answer = request.getParameter("answer");
        PageGenerator generator = PageGenerator.instance();

        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));
        int expectedAnswer = num1 + num2;

        if (answer.trim().equals(String.valueOf(expectedAnswer))) {
            String user = "guest-token";
            Session session = webService.getSecurityService().createGuestSession();
            setCookies(response, session.getToken(), user);
            response.sendRedirect("/vehicle_all");
        } else {
            String page = generator.getPage("wrong_answer.html");
            response.getWriter().write(page);
        }
    }

    private void setCookies(HttpServletResponse response, String token, String tokenRole) {
        Cookie cookie = new Cookie(tokenRole, token);
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}
