/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月24日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author AEON
 *
 */
@Data
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "uiconf")
public class UIConf extends IdEntity {

	private static final long serialVersionUID = -2293386937117617538L;
	
	private String uiconf_type;
	private String uiconf_field;
	private String uiconf_title;
	private String uiconf_datatype;
	private String ref_selmodel;
	private int order_no;
	@Transient
	private int is_contain;
	@Transient
	private String uiconf_value;
	@Transient
	private String editmode;

}
