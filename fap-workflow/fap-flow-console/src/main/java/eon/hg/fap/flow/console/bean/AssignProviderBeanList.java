package eon.hg.fap.flow.console.bean;

import eon.hg.fap.flow.process.assign.AssigneeProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class AssignProviderBeanList implements ApplicationContextAware {
    private Map<String,AssigneeProvider> assigneeProviderMaps = new HashMap<String,AssigneeProvider>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,AssigneeProvider> map=applicationContext.getBeansOfType(AssigneeProvider.class);
        for(String beanId:map.keySet()){
            AssigneeProvider provider=map.get(beanId);
            if(!provider.disable()){
                assigneeProviderMaps.put(beanId, provider);
            }
        }
    }

    public Map<String, AssigneeProvider> getAssigneeProviderMaps() {
        return assigneeProviderMaps;
    }
}
