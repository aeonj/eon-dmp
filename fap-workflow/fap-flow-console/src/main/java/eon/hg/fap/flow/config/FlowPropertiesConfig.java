package eon.hg.fap.flow.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.ArrayList;
import java.util.List;

public class FlowPropertiesConfig {

    public PropertyPlaceholderConfigurer eFlowProperties() {
        final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setIgnoreResourceNotFound(true);
        final List<Resource> resourceLst = new ArrayList<Resource>();
        resourceLst.add(new ClassPathResource("config/flow.properties"));
        ppc.setLocations(resourceLst.toArray(new Resource[]{}));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        ppc.setOrder(1);
        return ppc;
    }

}
