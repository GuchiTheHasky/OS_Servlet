package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FaviconServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imagePath = "src\\main\\resources\\template\\favicon.ico";
        resp.setContentType("image/vnd.microsoft.icon");
        try (InputStream inputStream = Files.newInputStream(Paths.get(imagePath))) {
            byte[] buffer = new byte[4096];
            int bytes;
            while ((bytes = inputStream.read(buffer)) != -1) {
                resp.getOutputStream().write(buffer, 0, bytes);
            }
        }
    }
}
