package eon.hg.fap.flow.model;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.db.model.primary.Accessory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = Globals.FLOW_TABLE_SUFFIX + "NODE_USERS", indexes = {
        @Index(name = "idx_flow_node_users", columnList = "process_instance_id_,node_name_")
})
public class NodeUsers extends Activity {
    @Column(name="PROCESS_INSTANCE_ID_")
    private Long processInstanceId;
    @Column(name="USER_ID_")
    private String userId;

    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(Long processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
