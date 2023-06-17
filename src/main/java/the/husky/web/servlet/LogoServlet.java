package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LogoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String imagePath = "template/img" + uri;
        resp.setContentType("image/png");
        try (InputStream inputStream = getServletContext().getResourceAsStream(imagePath)) {
            if (inputStream != null) {
                byte[] buffer = new byte[4096];
                int bytes;
                while ((bytes = inputStream.read(buffer)) != -1) {
                    resp.getOutputStream().write(buffer, 0, bytes);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
