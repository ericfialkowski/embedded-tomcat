package com.ericski.embedded;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

public class ResourceLoader extends ResourceConfig
{
    public ResourceLoader()
    {
        packages("com.ericski.embedded.resources");
    }    
    
    
    public static ServletContainer resourceConfig()
    {
        return new ServletContainer(new ResourceConfig(new ResourceLoader().getClasses()));
    }
}
