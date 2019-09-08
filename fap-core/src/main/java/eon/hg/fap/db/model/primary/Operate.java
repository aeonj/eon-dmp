/**
 * 
 */
package eon.hg.fap.db.model.primary;

import com.alibaba.fastjson.annotation.JSONField;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 用户操作管理类，用来管理菜单对应的工具栏操作信息，也可扩展到界面中的任何界面元素
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "operate")
public class Operate extends IdEntity {

	private static final long serialVersionUID = 6939308736035497644L;
	
	@Column(length=20)
	private String name;    //操作名称
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	private Menu menu;      //对应的菜单
	private String selector;    //jquery选择器
	@Column(length=42)
	private String cmptype;     //界面选择器类型   对应枚举类型CMPTYPE
	@Column(length=42)
	private String authkey;
	private int sequence;// 排序
	private String remark;  //操作备注
	@Transient
	private Long info_id;   //用于授权
	@Transient
	private String priorname;   //用于授权
	@Transient
	private String dispname;   //用于授权
	@Transient
	private String authtype;   //用于授权

}
