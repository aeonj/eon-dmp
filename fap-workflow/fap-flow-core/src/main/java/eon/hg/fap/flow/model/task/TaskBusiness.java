package eon.hg.fap.flow.model.task;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.flow.meta.ActionType;

import javax.persistence.*;

@Entity
@Table(name = Globals.FLOW_TABLE_SUFFIX + "task_business", indexes = {
        @Index(name = "idx_task_business1", columnList = "process_id_"),
        @Index(name = "idx_task_business2", unique = true, columnList = "process_instance_id_"),
        @Index(name = "idx_task_business2", columnList = "business_id_")
})
public class TaskBusiness {
    @Id
    @Column(name="ID_")
    private long id;

    @Column(name="PROCESS_ID_")
    private Long processId;
    @Column(name="PROCESS_INSTANCE_ID_")
    private Long processInstanceId;
    @Column(name="BUSINESS_ID_")
    private String businessId;
    @Column(name="CURRENT_NODE_NAME_")
    private String currentNodeName;
    @Column(name="NEXT_NODE_NAME_")
    private String nextNodeName;
    @Column(name="CURRENT_STATUS_CODE_",length = 20)
    private String currentStatusCode;
    @Column(name="NEXT_STATUS_CODE_",length = 20)
    private String nextStatusCode;
    @Column(name="ACTION_TYPE_")
    private ActionType actionType;
    @Column(name="CURRENT_TASK_ID_")
    private Long currentTaskId;
    @Column(name="NEXT_TASK_ID_")
    private Long nextTaskId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCurrentNodeName() {
        return currentNodeName;
    }

    public void setCurrentNodeName(String currentNodeName) {
        this.currentNodeName = currentNodeName;
    }

    public String getNextNodeName() {
        return nextNodeName;
    }

    public void setNextNodeName(String nextNodeName) {
        this.nextNodeName = nextNodeName;
    }

    public String getCurrentStatusCode() {
        return currentStatusCode;
    }

    public void setCurrentStatusCode(String currentStatusCode) {
        this.currentStatusCode = currentStatusCode;
    }

    public String getNextStatusCode() {
        return nextStatusCode;
    }

    public void setNextStatusCode(String nextStatusCode) {
        this.nextStatusCode = nextStatusCode;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public Long getCurrentTaskId() {
        return currentTaskId;
    }

    public void setCurrentTaskId(Long currentTaskId) {
        this.currentTaskId = currentTaskId;
    }

    public Long getNextTaskId() {
        return nextTaskId;
    }

    public void setNextTaskId(Long nextTaskId) {
        this.nextTaskId = nextTaskId;
    }
}
