package eon.hg.fap.uflo.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

public class UfloPropertiesConfig {

    public PropertyPlaceholderConfigurer ufloProperties() {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setIgnoreResourceNotFound(true);
        final List<Resource> resourceLst = new ArrayList<Resource>();
        resourceLst.add(new ClassPathResource("config/uflo.properties"));
        ppc.setLocations(resourceLst.toArray(new Resource[]{}));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        ppc.setOrder(1);
        return ppc;
    }

}
