/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年2月20日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AEON
 *
 */
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "menugroup")
public class MenuGroup extends IdEntity implements Comparable<MenuGroup>{

	private static final long serialVersionUID = 4229208678499672086L;
	private String name;// 菜单分组名称
	private int sequence;// 菜单分组序号
	private String type;// 菜单分组类型
	@OneToMany(mappedBy = "mg")
	private List<Menu> menus = new ArrayList<Menu>();// 对应的角色信息
	@Column(length=60)
	private String icons;
	@Transient
	private List<Menu> authmenus = new ArrayList<Menu>();
	@Transient
	private List<Dto> child = new ArrayList<Dto>();
	@Transient
	private String menujson;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}
	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the menus
	 */
	public List<Menu> getMenus() {
		return menus;
	}
	/**
	 * @param menus the menus to set
	 */
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	/**
	 * @return the icons
	 */
	public String getIcons() {
		return icons;
	}
	/**
	 * @param icons the icons to set
	 */
	public void setIcons(String icons) {
		this.icons = icons;
	}
	/**
	 * @return the authmenus
	 */
	public List<Menu> getAuthmenus() {
		return authmenus;
	}
	/**
	 * @param authmenus the authmenus to set
	 */
	public void setAuthmenus(List<Menu> authmenus) {
		this.authmenus = authmenus;
	}
	/**
	 * @return the child
	 */
	public List<Dto> getChild() {
		return child;
	}
	/**
	 * @param child the child to set
	 */
	public void setChild(List<Dto> child) {
		this.child = child;
	}

	public String getMenujson() {
		return menujson;
	}

	public void setMenujson(String menujson) {
		this.menujson = menujson;
	}

	@Override
	public int compareTo(MenuGroup obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}

}
