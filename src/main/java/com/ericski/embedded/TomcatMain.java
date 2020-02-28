package com.ericski.embedded;

import com.ericski.embedded.servlets.EmbeddedResourceHandlerServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomcatMain
{
    public static void main(String[] args) throws Exception, LifecycleException
    {     
        Tomcat tomcat = new Tomcat();
        
        int port = Integer.getInteger("PORT", 8811);
        tomcat.setPort(port);

        // Root context
        File base = new File(System.getProperty("java.io.tmpdir"));        
        Context context = tomcat.addWebapp("", base.getAbsolutePath());

        // Jersey servlet
        Tomcat.addServlet(context, "jersey-container-servlet", ResourceLoader.resourceConfig());
        context.addServletMappingDecoded("/rest/*", "jersey-container-servlet");

        // servlet for serving embedded static resources
        Tomcat.addServlet(context, "statics", new EmbeddedResourceHandlerServlet());
        context.addServletMappingDecoded("/*", "statics");
        
        // start & wait
        tomcat.start();
        tomcat.getServer().await();
    }
}
