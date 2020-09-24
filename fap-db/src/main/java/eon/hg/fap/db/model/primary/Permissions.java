package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "permissions")
public class Permissions extends IdEntity {

    @Column(length = 120)
    private String title;

    @Column(length = 60)
    private String value;
}