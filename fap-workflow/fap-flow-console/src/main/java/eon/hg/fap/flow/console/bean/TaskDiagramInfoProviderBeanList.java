package eon.hg.fap.flow.console.bean;

import eon.hg.fap.flow.diagram.TaskDiagramInfoProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class TaskDiagramInfoProviderBeanList implements ApplicationContextAware {
    private List<TaskDiagramInfoProvider> providers;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Collection<TaskDiagramInfoProvider> coll=applicationContext.getBeansOfType(TaskDiagramInfoProvider.class).values();
        providers=new ArrayList<TaskDiagramInfoProvider>();
        for(TaskDiagramInfoProvider p:coll){
            if(p.disable()){
                continue;
            }
            providers.add(p);
        }
    }

    public List<TaskDiagramInfoProvider> getProviders() {
        return providers;
    }
}
