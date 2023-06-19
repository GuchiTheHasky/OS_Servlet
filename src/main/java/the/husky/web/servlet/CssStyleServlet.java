package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;

import java.io.*;

public class CssStyleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String cssPath = "/template" + uri;

        @Cleanup InputStream inputStream = getServletContext().getResourceAsStream(cssPath);

        if (inputStream != null) {
            response.setContentType("text/css; charset=utf-8");
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
