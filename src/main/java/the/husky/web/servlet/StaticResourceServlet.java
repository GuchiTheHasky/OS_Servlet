package the.husky.web.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

@Slf4j
@NoArgsConstructor
public class StaticResourceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String resourcePath = request.getRequestURI();
        String relativePath = resourcePath.substring(1);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(relativePath)) {

            streamResourceContent(response, inputStream);

        } catch (NullPointerException | IOException e) {
            log.error("Failed to stream resource content: {}", relativePath, e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void streamResourceContent(HttpServletResponse response, InputStream inputStream)
            throws IOException {
        try (ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
             WritableByteChannel outputChannel = Channels.newChannel(response.getOutputStream())) {

            int bufferSize = 4096;
            int growFactor = 2;
            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            int bytesRead;
            while ((bytesRead = inputChannel.read(buffer)) != -1) {
                buffer.flip();
                outputChannel.write(buffer);
                buffer.clear();

                if (buffer.remaining() < bytesRead) {
                    bufferSize *= growFactor;
                    buffer = ByteBuffer.allocate(bufferSize);
                }
            }
        }
    }
}
