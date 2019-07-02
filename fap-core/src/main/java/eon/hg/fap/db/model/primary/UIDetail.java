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
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AEON
 *
 */
@Data
@ToString(exclude={"confs"})
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "uidetail")
public class UIDetail extends IdEntity {

	private static final long serialVersionUID = 6441024541285580696L;
	
	private String field_name;
	private String field_title;
	private String field_logic;
	private Byte field_index;
	@Column(length=38)
	private String field_type;
	private Byte hidden;
	private Short width;
	private Byte cols;
	private Byte detail_type;
	private Long parent_id;
	@JSONField(serialize=false)
	@ManyToOne(fetch = FetchType.LAZY)
	private UIManager ui;
	@OneToMany(mappedBy = "ui_detail", fetch = FetchType.LAZY, cascade={CascadeType.ALL}, orphanRemoval=true)
	private List<UIConfDetail> confs = new ArrayList();
	@Transient
	private String ui_detail_id;

}
