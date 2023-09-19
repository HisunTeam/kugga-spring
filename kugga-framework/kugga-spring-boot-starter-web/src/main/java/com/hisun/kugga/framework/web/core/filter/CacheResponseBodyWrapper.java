package com.hisun.kugga.framework.web.core.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author toi
 */
@Slf4j
public class CacheResponseBodyWrapper extends HttpServletResponseWrapper {

    private ByteArrayOutputStream byteArrayOutputStream;

    private ServletOutputStream servletOutputStream;

    public CacheResponseBodyWrapper(HttpServletResponse response) {
        super(response);
        byteArrayOutputStream = new ByteArrayOutputStream();
        servletOutputStream = new ServletOutputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {

            }

            @Override
            public void write(int b) throws IOException {
                response.getOutputStream().write(b);
                // read and write
                byteArrayOutputStream.write(b);
            }
        };
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    public byte[] toByteArray() {
        return byteArrayOutputStream.toByteArray();
    }
}
