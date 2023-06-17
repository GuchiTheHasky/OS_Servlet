package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

public class FaviconServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String imagePath = "template/" + uri;
        try (InputStream inputStream = getServletContext().getResourceAsStream(imagePath)) {
            if (inputStream != null) {
                byte[] buffer = new byte[4096];
                int bytes;
                while ((bytes = inputStream.read(buffer)) != -1) {
                    response.getOutputStream().write(buffer, 0, bytes);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }
}
