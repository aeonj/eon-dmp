package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 权限组要素明细权限值类
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "part_detail", indexes = {
		@Index(name = "idx_part_detail", columnList = "pg_id,elecode,value")
})
public class PartDetail extends IdEntity {
	private static final long serialVersionUID = 2837627972535989092L;

	@ManyToOne(fetch = FetchType.LAZY)
	private PartGroup pg;   //权限要素
	@Column(length=42)
	private String eleCode;   //要素名
	@Column(length=80)
	private String value;

}
