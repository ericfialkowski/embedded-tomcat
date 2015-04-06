package com.ericski.embedded;

import com.ericski.embedded.servlets.EmbeddedResourceHandlerServlet;
import java.io.File;
import java.net.MalformedURLException;
import javax.servlet.ServletException;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class Main
{
    public static void main(String[] args) throws Exception, LifecycleException
    {
        new Main().start();
    }

    public void start() throws ServletException, LifecycleException,
                               MalformedURLException
    {

        Tomcat tomcat = new Tomcat();
        
        int port = Integer.getInteger("PORT", 8181);                        
        tomcat.setPort(port);

        // Root context
        File base = new File(System.getProperty("java.io.tmpdir"));        
        Context context = tomcat.addWebapp("", base.getAbsolutePath());

        // Jersey servlet
        Tomcat.addServlet(context, "jersey-container-servlet", ResourceLoader.resourceConfig());
        context.addServletMapping("/rest/*", "jersey-container-servlet");

        // servlet for serving embedded static resources
        Tomcat.addServlet(context, "statics", new EmbeddedResourceHandlerServlet());
        context.addServletMapping("/*", "statics");
        
        // start & wait
        tomcat.start();
        tomcat.getServer().await();
    }
}
