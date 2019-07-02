package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 权限组要素明细权限值类
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "part_detail")
public class PartDetail extends IdEntity {
	private static final long serialVersionUID = 2837627972535989092L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PartGroup pg;   //权限要素
	private String eleCode;   //要素名
	private String value;

}
