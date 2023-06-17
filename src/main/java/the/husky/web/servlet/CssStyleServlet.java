package the.husky.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Cleanup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CssStyleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String path = "src/main/resources/template" + uri;

        File file = new File(path);

        if (file.exists()) {
            response.setContentType("text/css; charset=utf-8");
            // src/main/resources/template/vehicle/css/vehicle_list_style.css
            //

            @Cleanup FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            int bytesRead = fileInputStream.read(buffer);

            @Cleanup OutputStream outputStream = response.getOutputStream();
            outputStream.write(buffer, 0, bytesRead);

        }
        else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

//    private String getPath(HttpServletRequest request) {
//        String path = "src/main/resources/template" + request.getRequestURI();
//        String
//    }


}
