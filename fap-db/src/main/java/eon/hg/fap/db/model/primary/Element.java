package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 系统要素类，管理基础数据
 * @author AEON
 *
 */
@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "element")
public class Element extends IdEntity {

	private static final long serialVersionUID = 5768032256040941162L;
	
	private int set_year;
	@Column(length = 42, nullable=false)
	private String ele_source;  //要素对应表名
	private String class_name;   //要素对应类名
	@Column(length = 42, unique = true, nullable=false)
	private String ele_code;   //要素编码
	@Column(length = 42)
	private String ele_name;   //要素中文显示名
	private boolean enabled = true;   //是否启用
	private int dispmode=0;    //显示模式
	@Column(length = 4)
	private int max_level=99;    //最大级次
	private String code_rule;  //编码规则
	private Long ui_id;     //对应界面视图
	private boolean from_rg = false;   //是否是指定域下的基础数据，物理表形式的基础数据显示受此约束


}
