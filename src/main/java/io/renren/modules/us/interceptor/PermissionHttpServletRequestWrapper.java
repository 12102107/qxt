package io.renren.modules.us.interceptor;

import org.springframework.mock.web.DelegatingServletInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * request wrapper: 可重复读取request.getInputStream
 *
 * @author Li
 */
public class PermissionHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static final int BUFFER_START_POSITION = 0;

    private static final int CHAR_BUFFER_LENGTH = 1024;

    /**
     * input stream 的buffer
     */
    private final String body;

    /**
     * @param request {@link javax.servlet.http.HttpServletRequest} object.
     */
    PermissionHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                char[] charBuffer = new char[CHAR_BUFFER_LENGTH];
                int bytesRead;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, BUFFER_START_POSITION, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        body = stringBuilder.toString();
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        return new DelegatingServletInputStream(byteArrayInputStream);
    }

}
