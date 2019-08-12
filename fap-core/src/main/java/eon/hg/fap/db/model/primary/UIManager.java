/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月24日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import com.alibaba.fastjson.annotation.JSONField;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 界面视图主控制类
 * @author AEON
 *
 */
@Data
@ToString(exclude={"details","confs"})
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "uimanager")
public class UIManager extends IdEntity {
	
	private static final long serialVersionUID = 406189563925969696L;
	@Column(length=42)
	private String ui_code;  //视图编码
	private String ui_name;  //视图名称
	@Column(length=10)
	private String xtype;    //控件类型
	private String comp_id;
	private String servletpath;
	@Column(length=20)
	private String rg_code= AeonConstants.SUPER_RG_CODE;
	@Column(precision=2)
	private Byte total_column; 
	private Long parent_id;
	@JSONField(serialize = false)
	@OneToMany(mappedBy = "ui", fetch = FetchType.LAZY, cascade={CascadeType.ALL}, orphanRemoval=true)
	private List<UIDetail> details = new ArrayList<>();
	@OneToMany(mappedBy = "ui_main", fetch = FetchType.LAZY, cascade={CascadeType.ALL}, orphanRemoval=true)
	private List<UIConfMain> confs = new ArrayList<>();
	@Transient
	private String ui_id;


}
