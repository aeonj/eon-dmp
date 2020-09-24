/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月24日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import com.alibaba.fastjson.annotation.JSONField;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author AEON
 *
 */
@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "uiconf_main")
public class UIConfMain extends IdEntity {

	private static final long serialVersionUID = -6562578266634576663L;
	
	private String uiconf_field;
	private String uiconf_title;
	private String uiconf_value;
	private String uiconf_datatype;
	@JSONField(serialize=false)
	@ManyToOne(fetch = FetchType.LAZY)
	private UIManager ui_main;

}
