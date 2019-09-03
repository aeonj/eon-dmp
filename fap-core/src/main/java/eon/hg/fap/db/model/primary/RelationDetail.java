package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "relation_detail")
public class RelationDetail extends IdEntity {
    @ManyToOne
    private RelationMain main;
    private String pri_ele_value;
    private String sec_ele_value;
}
