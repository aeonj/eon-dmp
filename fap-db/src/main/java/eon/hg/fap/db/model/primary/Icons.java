package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "icons")
public class Icons {
    @Column(length=42)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;// 域模型id，这里为自增类型

    @Column(unique = true, nullable = false)
    private String icons;
}
