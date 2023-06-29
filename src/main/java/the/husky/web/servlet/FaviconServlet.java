package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;

import java.io.IOException;
import java.io.InputStream;

public class FaviconServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String faviconPath = "/static/favicon" + uri;

        @Cleanup InputStream inputStream = getServletContext().getResourceAsStream(faviconPath);

        if (inputStream != null) {
            response.setContentType("image/vnd.microsoft.icon");
            streamResourceContent(response, inputStream);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void streamResourceContent(HttpServletResponse response, InputStream inputStream)
            throws IOException {
        byte[] buffer = new byte[4096];
        int bytes;
        while ((bytes = inputStream.read(buffer)) != -1) {
            response.getOutputStream().write(buffer, 0, bytes);
        }
    }
}
