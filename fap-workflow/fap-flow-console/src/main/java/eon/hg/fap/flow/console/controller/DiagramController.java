package eon.hg.fap.flow.console.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.body.PageBody;
import eon.hg.fap.core.exception.Assert;
import eon.hg.fap.core.exception.ResultException;
import eon.hg.fap.core.mv.JModelAndView;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IMenuService;
import eon.hg.fap.db.service.IUserService;
import eon.hg.fap.flow.FlowProvider;
import eon.hg.fap.flow.console.bean.TaskDiagramInfoProviderBeanList;
import eon.hg.fap.flow.diagram.*;
import eon.hg.fap.flow.meta.CurrentNode;
import eon.hg.fap.flow.model.*;
import eon.hg.fap.flow.model.task.Task;
import eon.hg.fap.flow.model.task.TaskState;
import eon.hg.fap.flow.process.node.Node;
import eon.hg.fap.flow.process.node.StartNode;
import eon.hg.fap.flow.process.node.TaskNode;
import eon.hg.fap.flow.query.HistoryTaskQuery;
import eon.hg.fap.flow.query.ProcessInstanceQuery;
import eon.hg.fap.flow.query.TaskQuery;
import eon.hg.fap.flow.service.HistoryService;
import eon.hg.fap.flow.service.ProcessService;
import eon.hg.fap.flow.service.TaskService;
import eon.hg.fap.support.session.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flow")
public class DiagramController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired(required = false)
    private FlowProvider flowProvider;
    @Autowired
    private TaskDiagramInfoProviderBeanList taskDiagramInfoProviderBeanList;

    @Value("${flow.diagram.showTime}")
    private boolean showTime;
    @Value("${flow.diagram.passedNodeBgcolor}")
    private String passedNodeBgcolor;
    @Value("${flow.diagram.passedNodeFontColor}")
    private String passedNodeFontColor;
    @Value("${flow.diagram.passedNodeFontSize}")
    private int passedNodeFontSize;
    @Value("${flow.diagram.passedNodeBorderColor}")
    private String passedNodeBorderColor;
    @Value("${flow.diagram.passedConnectionColor}")
    private String passedConnectionColor;
    @Value("${flow.diagram.passedConnectionFontColor}")
    private String passedConnectionFontColor;
    @Value("${flow.diagram.passedConnectionFontSize}")
    private int passedConnectionFontSize;

    @Value("${flow.diagram.multiCurrentNodeBgcolor}")
    private String multiCurrentNodeBgcolor;
    @Value("${flow.diagram.multiCurrentNodeFontColor}")
    private String multiCurrentNodeFontColor;
    @Value("${flow.diagram.multiCurrentNodeFontSize}")
    private int multiCurrentNodeFontSize;
    @Value("${flow.diagram.multiCurrentNodeBorderColor}")
    private String multiCurrentNodeBorderColor;

    @Value("${flow.diagram.connectionColor}")
    private String connectionColor;
    @Value("${flow.diagram.connectionFontColor}")
    private String connectionFontColor;
    @Value("${flow.diagram.connectionFontSize}")
    private int connectionFontSize;
    @Value("${flow.diagram.nodeBgcolor}")
    private String nodeBgcolor;
    @Value("${flow.diagram.nodeFontColor}")
    private String nodeFontColor;
    @Value("${flow.diagram.nodeFontSize}")
    private int nodeFontSize;
    @Value("${flow.diagram.nodeBorderColor}")
    private String nodeBorderColor;

    @Value("${flow.diagram.currentNodeBgcolor}")
    private String currentNodeBgcolor;
    @Value("${flow.diagram.currentNodeFontColor}")
    private String currentNodeFontColor;
    @Value("${flow.diagram.currentNodeFontSize}")
    private int currentNodeFontSize;
    @Value("${flow.diagram.currentNodeBorderColor}")
    private String currentNodeBorderColor;


    @RequestMapping("/diagram")
    public ModelAndView diagram(HttpServletRequest req) {
        ModelAndView mv = new JModelAndView("flow-html/diagram.html",2);
        String businessId=req.getParameter("businessId");
        String taskId=req.getParameter("taskId");
        String processKey = req.getParameter("processKey");
        String processInstanceId = req.getParameter("processInstanceId");
        String processId = req.getParameter("processId");
        if (CommUtil.isNotEmpty(businessId)) {
            Long menu_id = UserContextHolder.getUserContext().getMenuId();
            Assert.notNull(menu_id,"获取的菜单不能为空");
            CurrentNode node = CurrentNode.menuInstance(menuService.getObjById(menu_id));
            ProcessDefinition pd = getProcessDefinition(node);
            ProcessInstanceQuery pq = processService.createProcessInstanceQuery();
            pq.processId(pd.getId());
            pq.businessId(businessId);
            List<ProcessInstance> piList = pq.list();
            if (piList!=null && piList.size()>0) {
                processInstanceId = Convert.toStr(piList.get(0).getId());
            } else {
                processId = Convert.toStr(pd.getId());
            }
        }
        StringBuffer sb=new StringBuffer();
        sb.append("{");
        if(StrUtil.isNotBlank(taskId)){
            sb.append("taskId:"+taskId);
        }
        if(StrUtil.isNotBlank(processKey)){
            if(sb.length()>1){
                sb.append(",");
            }
            sb.append("processKey:'"+processKey+"'");
        }
        if(StrUtil.isNotBlank(processInstanceId)){
            if(sb.length()>1){
                sb.append(",");
            }
            sb.append("processInstanceId:"+processInstanceId+"");
        }
        if(StrUtil.isNotBlank(processId)){
            if(sb.length()>1){
                sb.append(",");
            }
            sb.append("processId:"+processId+"");
        }
        sb.append("}");
        if(sb.length()<5){
            throw new RuntimeException("Show process diagram need one parameter:taskId or processKey or processInstanceId or processId");
        }
        mv.addObject("parameters",sb.toString());
        return mv;
    }

    @RequestMapping("/diagram/loadDiagramData")
    public ProcessDiagram loadDiagramData(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ProcessDiagram diagram = null;
        String processKey = req.getParameter("processKey");
        Long taskId = parseLong(req.getParameter("taskId"));
        Long processInstanceId = parseLong(req.getParameter("processInstanceId"));
        Long processId = parseLong(req.getParameter("processId"));
        if (taskId != null) {
            Task task = taskService.getTask(taskId);
            if (task == null) {
                HistoryTask historyTask = historyService.getHistoryTask(taskId);
                if (historyTask != null) {
                    processInstanceId = historyTask.getProcessInstanceId();
                    diagram = buildDiagramByProcessId(historyTask.getProcessId());
                    try {
                        diagram = (ProcessDiagram)diagram.clone();
                        resetProcessDiagramDefaultStyle(diagram);
                    } catch (CloneNotSupportedException e) {
                        throw new ServletException(e);
                    }
                    rebuildProcessDiagram(diagram, processInstanceId);
                } else {
                    throw new IllegalArgumentException("Task " + taskId + " is not exist!");
                }
            } else {
                processInstanceId = task.getProcessInstanceId();
                diagram = buildDiagramByTaskId(task);
            }
        } else if (processInstanceId != null) {
            diagram = buildDiagramByProcessInstanceId(processInstanceId);
        } else if (processId != null) {
            diagram = buildDiagramByProcessId(processId);
        } else if (StrUtil.isNotEmpty(processKey)) {
            diagram = buildDiagramByProcessKey(processKey);
        }
        if (diagram == null) {
            throw new IllegalArgumentException("There is not enough information to load process diagram!");
        }
        if (processInstanceId != null && processInstanceId != 0l) {
            ProcessInstance pi = processService.getProcessInstanceById(Long.valueOf(processInstanceId));
            HistoryProcessInstance hpi = null;
            if(pi==null){
                hpi=historyService.getHistoryProcessInstance(processInstanceId);
            }
            Long rootProcessInstanceId = null;
            if (pi != null) {
                rootProcessInstanceId = pi.getRootId();
            } else if (hpi != null) {
                rootProcessInstanceId = hpi.getProcessInstanceId();
            } else {
                throw new IllegalArgumentException("ProcessInstance ["+processInstanceId+"] not exist.");
            }
            ProcessDefinition pd = null;
            if(pi!=null){
                pd=processService.getProcessById(pi.getProcessId());
            }
            if(hpi!=null){
                pd=processService.getProcessById(hpi.getProcessId());
            }
            List<HistoryTask> historyTasks=queryHistoryTasks(rootProcessInstanceId);

            List<NodeDiagram> nodeDiagrams = diagram.getNodeDiagrams();
            for (NodeDiagram nodeDiagram : nodeDiagrams) {
                Node node = pd.getNode(nodeDiagram.getName());
                if (!(node instanceof TaskNode) && !(node instanceof StartNode)) {
                    continue;
                }
                TaskQuery query = taskService.createTaskQuery();
                query.rootProcessInstanceId(rootProcessInstanceId);
                query.nodeName(node.getName());
                query.addTaskState(TaskState.Created);
                query.addTaskState(TaskState.InProgress);
                query.addTaskState(TaskState.Ready);
                query.addTaskState(TaskState.Suspended);
                query.addTaskState(TaskState.Reserved);
                List<Task> tasks = query.list();
                StringBuffer sb = null;
                List<TaskDiagramInfoProvider> providers = taskDiagramInfoProviderBeanList.getProviders();
                for (TaskDiagramInfoProvider provider : providers) {
                    String info = null;
                    List<TaskInfo> taskInfoList = new ArrayList<TaskInfo>();
                    if (tasks.size() > 0) {
                        taskInfoList.addAll(buildTaskInfos(tasks));
                    }
                    List<HistoryTask> hisTasks = filterHistoryTasks(historyTasks,node.getName());
                    if (hisTasks.size() > 0) {
                        taskInfoList.addAll(buildHistoryTaskInfos(hisTasks));
                    }
                    info = provider.getInfo(node.getName(), taskInfoList);
                    if (StrUtil.isNotEmpty(info)) {
                        if (sb == null) {
                            sb = new StringBuffer();
                        }
                        sb.append(info);
                    }
                }
                if (sb != null) {
                    nodeDiagram.setInfo(sb.toString());
                }
            }
        }
        diagram.setShowTime(showTime);
        return diagram;
    }

    private List<HistoryTask> filterHistoryTasks(List<HistoryTask> historyTasks,String nodeName){
        List<HistoryTask> result=new ArrayList<HistoryTask>();
        for(HistoryTask hisTask:historyTasks){
            if(hisTask.getNodeName().equals(nodeName)){
                result.add(hisTask);
            }
        }
        return result;
    }

    private List<HistoryTask> queryHistoryTasks(long rootProcessInstanceId){
        HistoryTaskQuery historyTaskQuery = historyService.createHistoryTaskQuery();
        historyTaskQuery.rootProcessInstanceId(rootProcessInstanceId);
        List<HistoryTask> historyTasks = historyTaskQuery.list();
        return historyTasks;
    }

    private List<TaskInfo> buildTaskInfos(List<Task> tasks) {
        List<TaskInfo> infos = new ArrayList<TaskInfo>();
        for (Task task : tasks) {
            TaskInfo info = new TaskInfo();
            info.setAssignee(task.getAssignee());
            info.setBusinessId(task.getBusinessId());
            info.setCreateDate(task.getCreateDate());
            info.setDescription(task.getDescription());
            info.setDuedate(task.getDuedate());
            info.setOpinion(task.getOpinion());
            info.setOwner(task.getOwner());
            info.setProcessId(task.getProcessId());
            info.setProcessInstanceId(task.getProcessInstanceId());
            info.setState(task.getState());
            info.setTaskId(task.getId());
            info.setTaskName(task.getTaskName());
            info.setTaskId(task.getId());
            info.setType(task.getType());
            info.setUrl(task.getUrl());
            infos.add(info);
        }
        return infos;
    }

    private List<TaskInfo> buildHistoryTaskInfos(List<HistoryTask> tasks) {
        List<TaskInfo> infos = new ArrayList<TaskInfo>();
        for (HistoryTask task : tasks) {
            TaskInfo info = new TaskInfo();
            info.setAssignee(task.getAssignee());
            info.setBusinessId(task.getBusinessId());
            info.setCreateDate(task.getCreateDate());
            info.setDescription(task.getDescription());
            info.setDuedate(task.getDuedate());
            info.setEndDate(task.getEndDate());
            info.setOpinion(task.getOpinion());
            info.setOwner(task.getOwner());
            info.setProcessId(task.getProcessId());
            info.setProcessInstanceId(task.getProcessInstanceId());
            info.setState(task.getState());
            info.setTaskId(task.getId());
            info.setTaskName(task.getTaskName());
            info.setTaskId(task.getId());
            info.setType(task.getType());
            info.setUrl(task.getUrl());
            infos.add(info);
        }
        return infos;
    }

    private ProcessDiagram buildDiagramByTaskId(Task task) {
        ProcessInstance pi = processService.getProcessInstanceById(task.getProcessInstanceId());
        ProcessDefinition pd = processService.getProcessById(task.getProcessId());
        ProcessDiagram diagram = pd.getDiagram();
        try {
            diagram = (ProcessDiagram)diagram.clone();
            resetProcessDiagramDefaultStyle(diagram);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        rebuildProcessDiagram(diagram, pi.getRootId());
        for (NodeDiagram nodeDiagram : diagram.getNodeDiagrams()) {
            if (nodeDiagram.getName().equals(task.getNodeName())) {
                nodeDiagram.setBackgroundColor(multiCurrentNodeBgcolor);
                nodeDiagram.setBorderColor(multiCurrentNodeBorderColor);
                nodeDiagram.setFontColor(multiCurrentNodeFontColor);
                nodeDiagram.setFontSize(multiCurrentNodeFontSize);
                nodeDiagram.setFontBold(true);
                break;
            }
        }
        return diagram;
    }

    private ProcessDiagram buildDiagramByProcessInstanceId(Long processInstanceId) {
        ProcessInstance pi = processService.getProcessInstanceById(processInstanceId);
        long processId=0,rootId=0;
        if (pi != null) {
            processId=pi.getProcessId();
            rootId=pi.getRootId();
        }else{
            HistoryProcessInstance hpi=historyService.getHistoryProcessInstance(processInstanceId);
            if(hpi!=null){
                processId=hpi.getProcessId();
                rootId=hpi.getProcessInstanceId();
            }else{
                throw new IllegalArgumentException("ProcessInstance " + processInstanceId + " is not exist!");
            }
        }
        ProcessDefinition pd = processService.getProcessById(processId);
        ProcessDiagram diagram = pd.getDiagram();
        try {
            diagram = (ProcessDiagram)diagram.clone();
            resetProcessDiagramDefaultStyle(diagram);
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        rebuildProcessDiagram(diagram,rootId);
        return diagram;
    }

    private ProcessDiagram buildDiagramByProcessId(Long processId) {
        ProcessDefinition pd = processService.getProcessById(processId);
        ProcessDiagram diagram = pd.getDiagram();
        resetProcessDiagramDefaultStyle(diagram);
        return diagram;
    }

    private ProcessDiagram buildDiagramByProcessKey(String processKey) {
        ProcessDefinition pd = processService.getProcessByKey(processKey);
        ProcessDiagram diagram = pd.getDiagram();
        resetProcessDiagramDefaultStyle(diagram);
        return diagram;
    }

    private void rebuildProcessDiagram(ProcessDiagram diagram, long processInstanceId) {
        Map<String, Integer> countMap = new HashMap<String, Integer>();
        Map<String, HistoryActivity> map = new HashMap<String, HistoryActivity>();
        List<HistoryActivity> activities = historyService.getHistoryActivitysByProcesssInstanceId(processInstanceId);
        for (HistoryActivity hisActivity : activities) {
            String nodeName = hisActivity.getNodeName();
            if (countMap.containsKey(nodeName)) {
                int count = countMap.get(nodeName);
                count++;
                countMap.put(nodeName, count);
            } else {
                countMap.put(nodeName, 1);
            }
        }
        for (NodeDiagram d : diagram.getNodeDiagrams()) {
            String nodeName = d.getName();
            HistoryActivity activity = getHistoryActivity(activities, nodeName);
            if (activity == null) {
                continue;
            }
            map.put(nodeName, activity);
            if (countMap.containsKey(nodeName)) {
                int count = countMap.get(nodeName);
                d.setTime(count);
            }
            String icon = d.getIcon();
            String resultIcon = null;
            if (activity.getEndDate() == null) {
                resultIcon = icon.substring(0, icon.lastIndexOf("/")) + "/current" + icon.substring(icon.lastIndexOf("/"));
                d.setFontColor(currentNodeFontColor);
                d.setBackgroundColor(currentNodeBgcolor);
                d.setBorderColor(currentNodeBorderColor);
                d.setFontColor(currentNodeFontColor);
                d.setFontSize(currentNodeFontSize);
            } else {
                resultIcon = icon.substring(0, icon.lastIndexOf("/")) + "/passed" + icon.substring(icon.lastIndexOf("/"));
                d.setFontColor(passedNodeFontColor);
                d.setBorderColor(passedNodeBorderColor);
                d.setBackgroundColor(passedNodeBgcolor);
                d.setFontSize(passedNodeFontSize);
            }
            d.setIcon(resultIcon);
        }

        for (NodeDiagram d : diagram.getNodeDiagrams()) {
            String sourceName = d.getName();
            HistoryActivity source = map.get(sourceName);
            if (source == null || d.getSequenceFlowDiagrams() == null) {
                continue;
            }
            for (SequenceFlowDiagram flowDiagram : d.getSequenceFlowDiagrams()) {
                HistoryActivity target = map.get(flowDiagram.getTo());
                if (target == null) {
                    continue;
                }
                /*
                 * String leaveFlowName=source.getLeaveFlowName(); if(leaveFlowName==null ||
                 * leaveFlowName.equals(flowDiagram.getName())){ flowDiagram.setBorderColor("200,200,200");
                 * flowDiagram.setFontColor("150,150,150"); }
                 */
                flowDiagram.setBorderColor(passedConnectionColor);
                flowDiagram.setFontColor(passedConnectionFontColor);
                flowDiagram.setFontSize(passedConnectionFontSize);
            }
        }
    }

    private void resetProcessDiagramDefaultStyle(ProcessDiagram diagram) {
        for (NodeDiagram d : diagram.getNodeDiagrams()) {
            d.setBackgroundColor(nodeBgcolor);
            d.setBorderColor(nodeBorderColor);
            d.setFontColor(nodeFontColor);
            d.setFontSize(nodeFontSize);
            List<SequenceFlowDiagram> list = d.getSequenceFlowDiagrams();
            if (list == null) {
                continue;
            }
            for (SequenceFlowDiagram flow : list) {
                flow.setBorderColor(connectionColor);
                flow.setFontColor(connectionFontColor);
                flow.setFontSize(connectionFontSize);
            }
        }
    }

    private HistoryActivity getHistoryActivity(List<HistoryActivity> activities, String nodeName) {
        List<HistoryActivity> result = new ArrayList<HistoryActivity>();
        for (HistoryActivity activity : activities) {
            if (activity.getNodeName().equals(nodeName)) {
                result.add(activity);
            }
        }
        HistoryActivity target = null;
        for (HistoryActivity ac : result) {
            if (ac.getEndDate() == null) {
                target = ac;
                break;
            }
        }
        if (target == null && result.size() > 0) {
            return result.get(0);
        } else {
            return target;
        }
    }

    private static Long parseLong(Object obj) {
        Long val;
        if (obj == null) {
            val = null;
        } else if (obj instanceof Number) {
            val = ((Number) obj).longValue();
        } else {
            try {
                val = Long.valueOf(obj.toString());
            } catch (NumberFormatException e) {
                val = null;
            }
        }
        return val;
    }

    @RequestMapping("/operaHistory")
    public PageBody operaHistory(String businessId,int page, int limit) {
        Long menu_id = UserContextHolder.getUserContext().getMenuId();
        Assert.notNull(menu_id,"获取的菜单不能为空");
        return PageBody.success().addPageInfo(flowProvider.loadTaskLogList(menu_id,businessId,page,limit));
    }

    private ProcessDefinition getProcessDefinition(CurrentNode node) {
        ProcessDefinition pd = null;
        if (StrUtil.isNotBlank(node.getFlowId())) {
            pd = processService.getProcessById(Convert.toLong(node.getFlowId()));
        } else if (StrUtil.isNotBlank(node.getFlowName())) {
            pd = processService.getProcessByName(node.getFlowName());
        } else if (StrUtil.isNotBlank(node.getFlowKey())) {
            pd = processService.getProcessByKey(node.getFlowKey());
        }
        if (pd == null)
            throw new ResultException("未找到对应的流程");
        return pd;
    }

}
