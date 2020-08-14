package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "uflo_node_users", indexes = {
        @Index(name = "idx_uflo_node_users", columnList = "process_instance_id,node_name")
})
public class UfloNodeUsers extends IdEntity {
    private Long process_instance_id;
    private String node_name;
    private Long user_id;

    public Long getProcess_instance_id() {
        return process_instance_id;
    }

    public void setProcess_instance_id(Long process_instance_id) {
        this.process_instance_id = process_instance_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
