package io.renren.modules.us.interceptor;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.DelegatingServletInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Li
 */
public class PermissionHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private static Logger logger = LoggerFactory.getLogger(PermissionHttpServletRequestWrapper.class);

    private String body;

    PermissionHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        return new DelegatingServletInputStream(byteArrayInputStream);
    }

}