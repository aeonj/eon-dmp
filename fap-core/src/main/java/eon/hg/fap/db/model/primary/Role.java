/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年1月26日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author AEON
 *
 */
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "role")
public class Role extends IdEntity implements Comparable<Role>{
	private static final long serialVersionUID = -2875185344092083475L;
	private String roleCode;   //角色编码
	private String roleName;   //角色名称
	private String type;// MANAGE为后台业务系统权限，PUBLIC为公众权限
	private String info;// 角色说明
	private boolean builtin=false;// 是否内置角色
	@ManyToMany(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinTable(name = Globals.SYS_TABLE_SUFFIX + "role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private Set<Menu> menus = new TreeSet<Menu>();
	@Lock
	@Column(columnDefinition = "varchar(42) default '000000'",nullable = false)
	private String rg_code; //用户域
	@Transient
	private String authCode;      //权限角色值
	@OneToMany(mappedBy = "role")
	private List<RoleOperate> operates = new ArrayList<RoleOperate>();// 对应的操作信息
	@ManyToMany(mappedBy = "roles", targetEntity = User.class, fetch = FetchType.LAZY)
	private List<User> users = new ArrayList<User>();
	
	
	public Role() {
		super();
	}
	
	public Role(Long id) {
		super(id,null);
	}
	
	public Role(Long id,String roleCode, String roleName, String type) {
		super(id,null);
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.type = type;
	}
	
	public Role(Long id,String roleCode, String roleName, String type, String info, boolean builtin) {
		super(id,null);
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.type = type;
		this.info = info;
		this.builtin = builtin;
	}
	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}
	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}
	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * @return the menus
	 */
	public Set<Menu> getMenus() {
		return menus;
	}
	/**
	 * @param menus the menus to set
	 */
	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the builtin
	 */
	public boolean isBuiltin() {
		return builtin;
	}
	/**
	 * @param builtin the builtin to set
	 */
	public void setBuiltin(boolean builtin) {
		this.builtin = builtin;
	}
	
	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		if (this.authCode==null) {
			if (this.getRoleCode().indexOf("ROLE_") != 0) {
				this.authCode = Globals.DEFAULT_AUTH_SUFFIX + this.getRoleCode().toUpperCase()+this.getRg_code();
			} else {
				this.authCode = this.getRoleCode().toUpperCase()+this.getRg_code();
			}
		}
		return this.authCode;
	}

	/**
	 * @return the rg_code
	 */
	public String getRg_code() {
		return rg_code;
	}

	/**
	 * @param rg_code the rg_code to set
	 */
	public void setRg_code(String rg_code) {
		this.rg_code = rg_code;
	}

	/**
	 * @return the operates
	 */
	public List<RoleOperate> getOperates() {
		return operates;
	}

	/**
	 * @param operates the operates to set
	 */
	public void setOperates(List<RoleOperate> operates) {
		this.operates = operates;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public int compareTo(Role obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}

}
