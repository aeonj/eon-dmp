package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "relation_main")
public class RelationMain extends IdEntity {
    private String name;  //关联名称
    private String pri_ele;  //主控要素
    private String sec_ele;  //被控要素
}
