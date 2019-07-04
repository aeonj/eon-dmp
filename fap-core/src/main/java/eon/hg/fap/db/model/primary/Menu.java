/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年1月25日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色资源类，存储用户角色资源菜单信息
 * @author AEON
 *
 */
@Entity
@Data
@Table(name = Globals.SYS_TABLE_SUFFIX + "menu")
public class Menu extends IdEntity implements Comparable<Menu> {
	private static final long serialVersionUID = -2502634473094918124L;
	private String menuName;// 菜单名称
	@Column(unique = true)
	private String menuCode;// 菜单编码，ss根据该编码来识别菜单
	@Lock
	private String type;// MANAGE为后台业务系统权限，，PUBLIC为公众权限
	private String info;// 菜单说明
	private boolean display = true;// 是否显示菜单
	private int sequence;// 排序
	private Long parent_id;
	private String request;
	@Transient
	private List<Dto> child = new ArrayList<Dto>();
	@ManyToOne
	private MenuGroup mg;  //对应菜单组
	@ManyToMany(mappedBy = "menus", targetEntity = Role.class, fetch = FetchType.LAZY)
	private List<Role> roles = new ArrayList<Role>();
	@OneToMany(mappedBy = "menu")
	private List<Operate> operates = new ArrayList<Operate>();// 对应的操作信息
    @Column(length=42)
    private String authkey;
	@Column(length=10)
	private String manageTypes="0,1";   //用于菜单显示，与user实体类的manageType关联，0:普通用户 1:区域管理员
	@Column(length = 60)
	private String f_module_id;    //F3的功能ID

	@Override
	public int compareTo(Menu obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}

}