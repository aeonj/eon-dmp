package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 系统启用地域控制a类，标识rg_code值对应
 * @author aeon
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "region")
public class Region extends IdEntity implements Serializable {

	private static final long serialVersionUID = 1390808505392633604L;
	@Column(unique = true, nullable = false)
	private String code;    //对应rg_code
	private String name;    //启用的域名称
	private String ename;    //启用的域英文简称
	@OneToOne(fetch = FetchType.LAZY)
	private Area area;
	@ManyToMany(targetEntity = Menu.class, fetch = FetchType.LAZY)
	@JoinTable(name = Globals.SYS_TABLE_SUFFIX + "region_menu", joinColumns = @JoinColumn(name = "rg_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private Set<Menu> menus = new TreeSet<Menu>();
	@OneToMany(mappedBy = "region")
	private List<RegionOperate> operates = new ArrayList<RegionOperate>();// 对应的操作信息
	@ManyToMany(targetEntity = Region.class, fetch = FetchType.LAZY)
	@JoinTable(name = Globals.SYS_TABLE_SUFFIX + "region_relation", joinColumns = @JoinColumn(name = "primary_id"), inverseJoinColumns = @JoinColumn(name = "secondary_id"))
	private Set<Region> relations = new TreeSet<Region>();
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
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
	/**
	 * @return the area
	 */
	public Area getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(Area area) {
		this.area = area;
	}
	/**
	 * @return the operates
	 */
	public List<RegionOperate> getOperates() {
		return operates;
	}
	/**
	 * @param operates the operates to set
	 */
	public void setOperates(List<RegionOperate> operates) {
		this.operates = operates;
	}
	public String getEname() {
		return ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	/**
	 * @return the relations
	 */
	public Set<Region> getRelations() {
		return relations;
	}
	/**
	 * @param relations the relations to set
	 */
	public void setRelations(Set<Region> relations) {
		this.relations = relations;
	}
}
