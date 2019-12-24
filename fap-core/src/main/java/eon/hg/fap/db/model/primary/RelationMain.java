package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "relation_main",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pri_ele","sec_ele"})
})
public class RelationMain extends IdEntity {
    private String name;  //关联名称
    @Column(length = 42,nullable = false)
    private String pri_ele;  //主控要素
    @Column(length = 42,nullable = false)
    private String sec_ele;  //被控要素
}
