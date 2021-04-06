package eon.hg.fap.flow.console.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.flow.console.bean.AssignProviderBeanList;
import eon.hg.fap.flow.console.bean.AssigneeInfo;
import eon.hg.fap.flow.console.bean.HandlerBeanList;
import eon.hg.fap.flow.console.provider.ProcessFile;
import eon.hg.fap.flow.console.provider.ProcessProvider;
import eon.hg.fap.flow.console.provider.ProcessProviderUtils;
import eon.hg.fap.flow.deploy.parse.impl.ProcessParser;
import eon.hg.fap.flow.model.ProcessDefinition;
import eon.hg.fap.flow.process.assign.Assignee;
import eon.hg.fap.flow.process.assign.AssigneeProvider;
import eon.hg.fap.flow.process.assign.Entity;
import eon.hg.fap.flow.process.assign.PageQuery;
import eon.hg.fap.flow.service.ProcessService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/flow")
public class DesignerController{
    @Autowired
    private HandlerBeanList handlerBeanList;
    @Autowired
    private AssignProviderBeanList assignProviderList;
    @Autowired
    private ProcessService processService;

    @RequestMapping("/designer")
    public ModelAndView designer() {
        ModelAndView mv = new JModelAndView("flow-html/designer.html",2);
        mv.addObject("categories","");
        return mv;
    }

    @RequestMapping("/handler/list")
    public ResultBody handlerList(String handler) {
        if(StrUtil.isEmpty(handler)){
            ResultBody.failed("传入的请求参数不能为空");
        }
        return ResultBody.success().addObject(handlerBeanList.getHandlerMap().get(handler));
    }

    @RequestMapping("/assign/provider/list")
    public ResultBody assignProviderList(String parentId, String beanId, Integer page, Integer limit) {
        if(page==null)page=1;
        if (limit==null) limit=1000;
        if(parentId!=null && parentId.equals("null")){
            parentId=null;
        }
        PageQuery<Entity> pageQuery= new PageQuery<Entity>(Integer.valueOf(page),Integer.valueOf(limit));
        if(StrUtil.isNotEmpty(beanId)){
            AssigneeInfo info = buildAssigneeInfo(parentId, pageQuery,beanId,true);
            return ResultBody.success(info);
        }else{
            List<AssigneeInfo> result=new ArrayList<AssigneeInfo>();
            for(String providerId:assignProviderList.getAssigneeProviderMaps().keySet()){
                AssigneeInfo info = buildAssigneeInfo(parentId, pageQuery,providerId,false);
                result.add(info);
            }
            return ResultBody.success(result);
        }
    }

    private AssigneeInfo buildAssigneeInfo(String parentId,PageQuery<Entity> pageQuery, String beanId,boolean buildEntity) {
        AssigneeProvider provider=assignProviderList.getAssigneeProviderMaps().get(beanId);
        provider.queryEntities(pageQuery, parentId);
        AssigneeInfo info=new AssigneeInfo();
        info.setName(provider.getName());
        info.setTree(provider.isTree());
        info.setProviderId(beanId);
        if(buildEntity){
            List<Assignee> assignees=new ArrayList<Assignee>();
            Collection<Entity> entitys=pageQuery.getResult();
            if(entitys!=null){
                for(Entity entity:entitys){
                    Assignee assignee=new Assignee();
                    assignee.setId(entity.getId());
                    assignee.setName(entity.getName());
                    assignee.setProviderId(beanId);
                    assignee.setChosen(entity.isChosen());
                    assignees.add(assignee);
                }
            }
            info.setAssignees(assignees);
            info.setCount(pageQuery.getRecordCount());
        }
        return info;
    }

    @RequestMapping("/designer/deploy")
    public void deploy(String content) {
        content=URLUtil.decode(content);
        InputStream inputStream=IoUtil.toStream(content, "utf-8");
        processService.deployProcess(inputStream);
        IoUtil.close(inputStream);
    }

    @RequestMapping("/designer/saveFile")
    public void saveFile(String fileName, String content) {
        content=URLUtil.decode(content);
        ProcessProvider provider= ProcessProviderUtils.getProcessProvider(fileName);
        provider.saveProcess(fileName, content);
    }

    @RequestMapping("/designer/openFile")
    public ProcessDefinition openFile(String name) throws ServletException, IOException {
        name=URLUtil.decode(name);
        ProcessProvider targetProvider=ProcessProviderUtils.getProcessProvider(name);
        if(targetProvider==null){
            throw new RuntimeException("不支持的文件类型: "+name);
        }
        InputStream inputStream=targetProvider.loadProcess(name);
        try{
            byte[] bytes= IoUtil.readBytes(inputStream);
            ProcessDefinition process= ProcessParser.parseProcess(bytes, 0, true);
            return process;
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }finally{
            IOUtils.closeQuietly(inputStream);
        }
    }

    @RequestMapping("/designer/deleteFile")
    public void deleteFile(String fileName) {
        fileName=URLUtil.decode(fileName);
        ProcessProvider provider=ProcessProviderUtils.getProcessProvider(fileName);
        provider.deleteProcess(fileName);
    }

    @RequestMapping("/designer/loadProcessProviders")
    public List<ProcessProvider> loadProcessProviders() {
        List<ProcessProvider> providers=ProcessProviderUtils.getProviders();
        return providers;
    }

    @RequestMapping("/designer/loadProcessProviderFiles")
    public List<ProcessFile> loadProcessProviderFiles(@RequestParam("name") String providerName) {
        if(StrUtil.isBlank(providerName)){
            throw new RuntimeException("Process provider name can not be null.");
        }
        ProcessProvider targetProcessProvider=ProcessProviderUtils.getProcessProviderByName(providerName);
        List<ProcessFile> files=targetProcessProvider.loadAllProcesses();
        return files;
    }

}
