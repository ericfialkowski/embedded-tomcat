package com.ericski.embedded.servlets;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EmbeddedResourceHandlerServlet extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException
    {
        String pathInfo = "/www" + request.getPathInfo();
        
        InputStream resourceAsStream = getResourceFromPathInfo(pathInfo);
        if (resourceAsStream == null)
        {
            response.sendError(404, "Not found: " + pathInfo);
        }
        else
        {
            try (ServletOutputStream outputStream = response.getOutputStream())
            {
                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = resourceAsStream.read(buffer)) != -1)
                {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            resourceAsStream.close();
        }
    }
    
    /**
     * 
     * Handles index pages
     * 
     * @param pathInfo
     * @return 
     */
    protected InputStream getResourceFromPathInfo(String pathInfo)
    {
        InputStream stream = null;
        if ( pathInfo.endsWith("/"))
        {
            stream = EmbeddedResourceHandlerServlet.class.getResourceAsStream(pathInfo + "index.html");
            if ( stream == null)
            {
                stream = EmbeddedResourceHandlerServlet.class.getResourceAsStream(pathInfo + "index.htm");
            }
        }
        else
        {
            stream = EmbeddedResourceHandlerServlet.class.getResourceAsStream(pathInfo);
        }
        return stream;
    }
}
