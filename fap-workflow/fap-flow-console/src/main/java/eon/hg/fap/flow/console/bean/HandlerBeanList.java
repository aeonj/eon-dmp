package eon.hg.fap.flow.console.bean;

import eon.hg.fap.flow.process.handler.*;
import eon.hg.fap.flow.process.listener.TaskListener;
import eon.hg.fap.flow.process.node.FormTemplateProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class HandlerBeanList implements ApplicationContextAware {
    private Map<String,Set<String>> handlerMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        handlerMap = new HashMap<String,Set<String>>();
        handlerMap.put(NodeEventHandler.class.getSimpleName(), applicationContext.getBeansOfType(NodeEventHandler.class).keySet());
        handlerMap.put(ProcessEventHandler.class.getSimpleName(), applicationContext.getBeansOfType(ProcessEventHandler.class).keySet());
        handlerMap.put(DecisionHandler.class.getSimpleName(), applicationContext.getBeansOfType(DecisionHandler.class).keySet());
        handlerMap.put(AssignmentHandler.class.getSimpleName(), applicationContext.getBeansOfType(AssignmentHandler.class).keySet());
        handlerMap.put(ConditionHandler.class.getSimpleName(), applicationContext.getBeansOfType(ConditionHandler.class).keySet());
        handlerMap.put(ActionHandler.class.getSimpleName(), applicationContext.getBeansOfType(ActionHandler.class).keySet());
        handlerMap.put(ForeachHandler.class.getSimpleName(), applicationContext.getBeansOfType(ForeachHandler.class).keySet());
        handlerMap.put(ReminderHandler.class.getSimpleName(), applicationContext.getBeansOfType(ReminderHandler.class).keySet());
        handlerMap.put(CountersignHandler.class.getSimpleName(), applicationContext.getBeansOfType(CountersignHandler.class).keySet());
        handlerMap.put(TaskListener.class.getSimpleName(), applicationContext.getBeansOfType(TaskListener.class).keySet());
        Set<String> set=new HashSet<String>();
        for(FormTemplateProvider provider:applicationContext.getBeansOfType(FormTemplateProvider.class).values()){
            set.add(provider.getFormTemplate());
        }
        handlerMap.put(FormTemplateProvider.class.getSimpleName(), set);
    }

    public Map<String, Set<String>> getHandlerMap() {
        return handlerMap;
    }

    public void setHandlerMap(Map<String, Set<String>> handlerMap) {
        this.handlerMap = handlerMap;
    }
}
