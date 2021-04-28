package eon.hg.fap.flow.model;

import eon.hg.fap.core.constant.Globals;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = Globals.FLOW_TABLE_SUFFIX + "NODE_ROLES", indexes = {
        @Index(name = "idx_flow_node_roles", columnList = "process_id_,node_name_")
})
public class NodeRoles extends Activity {
    @Column(name="ROLE_ID_")
    private String roleId;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
