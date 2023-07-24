package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.*;
import the.husky.web.util.PageGenerator;

import java.io.IOException;

public class ForgotPasswordServlet extends HttpServlet {

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
            setCookies(response);
            response.sendRedirect("/vehicle_all");
        } else {
            String page = generator.getPage("wrong_answer.html");
            response.getWriter().write(page);
        }
    }

    private void setCookies(HttpServletResponse response) {
        Cookie cookie = new Cookie("user-token", "root");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }
}
