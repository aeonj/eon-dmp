package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.flow.meta.ActionType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "uflo_current_tasks", indexes = {
        @Index(name = "idx_uflo_current_tasks1", columnList = "process_id"),
        @Index(name = "idx_uflo_current_tasks2", unique = true, columnList = "process_instance_id"),
        @Index(name = "idx_uflo_current_tasks2", columnList = "business_id")
})
public class UfloCurrentTasks extends IdEntity {

    private Long process_id;
    private Long process_instance_id;
    private String business_id;
    private String current_node_name;
    private String next_node_name;
    @Column(length = 20)
    private String current_status_code;
    @Column(length = 20)
    private String next_status_code;
    private ActionType action_type;
    private Long current_task_id;
    private Long next_task_id;

    public Long getProcess_id() {
        return process_id;
    }

    public void setProcess_id(Long process_id) {
        this.process_id = process_id;
    }

    public Long getProcess_instance_id() {
        return process_instance_id;
    }

    public void setProcess_instance_id(Long process_instance_id) {
        this.process_instance_id = process_instance_id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getCurrent_node_name() {
        return current_node_name;
    }

    public void setCurrent_node_name(String current_node_name) {
        this.current_node_name = current_node_name;
    }

    public String getNext_node_name() {
        return next_node_name;
    }

    public void setNext_node_name(String next_node_name) {
        this.next_node_name = next_node_name;
    }

    public String getCurrent_status_code() {
        return current_status_code;
    }

    public void setCurrent_status_code(String current_status_code) {
        this.current_status_code = current_status_code;
    }

    public String getNext_status_code() {
        return next_status_code;
    }

    public void setNext_status_code(String next_status_code) {
        this.next_status_code = next_status_code;
    }

    public ActionType getAction_type() {
        return action_type;
    }

    public void setAction_type(ActionType action_type) {
        this.action_type = action_type;
    }

    public Long getCurrent_task_id() {
        return current_task_id;
    }

    public void setCurrent_task_id(Long current_task_id) {
        this.current_task_id = current_task_id;
    }

    public Long getNext_task_id() {
        return next_task_id;
    }

    public void setNext_task_id(Long next_task_id) {
        this.next_task_id = next_task_id;
    }
}
