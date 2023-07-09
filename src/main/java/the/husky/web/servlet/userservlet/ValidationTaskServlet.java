package the.husky.web.servlet.userservlet;

import jakarta.servlet.http.HttpSession;
import the.husky.entity.user.User;
import the.husky.web.util.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public class ValidationTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("task.html");
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String answer = request.getParameter("answer");
        PageGenerator generator = PageGenerator.instance();

        int num1 = Integer.parseInt(request.getParameter("num1"));
        int num2 = Integer.parseInt(request.getParameter("num2"));
        int expectedAnswer = num1 + num2;

        if (answer.trim().equals(String.valueOf(expectedAnswer))) {
            HttpSession session = request.getSession(true);
            Optional<User> user = Optional.of(new User());
            session.setAttribute("user", user.get());
            response.sendRedirect("/user_all");
        } else {
            String page = generator.getPage("wrong_answer.html");
            response.getWriter().write(page);
        }
    }
}
