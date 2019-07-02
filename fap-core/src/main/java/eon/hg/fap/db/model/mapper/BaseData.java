/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月1日
 * @author cxj
 */

package eon.hg.fap.db.model.mapper;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author AEON
 *
 */
@MappedSuperclass
public abstract class BaseData extends IdEntity implements Serializable {
	
	@Column(precision=4)
	private int set_year;
	@Column(length = 42)
	private String code;
	@Column
	private String name;
	@Transient
	private List<Dto> child = new ArrayList<>();
	@Column
	private String treepath;
	@Transient
	private String parentpath;
	@Column(name = "level_num")
	private Byte level=1;
	private boolean leaf=true;
	private boolean enabled=true;
	private Long create_user;
	private Date create_date;
	private Long latest_op_user;
	private Date latest_op_date;
	private String last_ver;
	
	/**
	 * @return the set_year
	 */
	public int getSet_year() {
		return set_year;
	}
	/**
	 * @param set_year the set_year to set
	 */
	public void setSet_year(int set_year) {
		this.set_year = set_year;
	}
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
	/**
	 * @return the level
	 */
	public Byte getLevel() {
		return level;
	}
	/**
	 * @param level the level to set
	 */
	public void setLevel(Byte level) {
		this.level = level;
	}
	/**
	 * @return the leaf
	 */
	public boolean isLeaf() {
		return leaf;
	}
	/**
	 * @param leaf the leaf to set
	 */
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	/**
	 * @return the create_user
	 */
	public Long getCreate_user() {
		return create_user;
	}
	/**
	 * @param create_user the create_user to set
	 */
	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}
	/**
	 * @return the create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}
	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	/**
	 * @return the latest_op_user
	 */
	public Long getLatest_op_user() {
		return latest_op_user;
	}
	/**
	 * @param latest_op_user the latest_op_user to set
	 */
	public void setLatest_op_user(Long latest_op_user) {
		this.latest_op_user = latest_op_user;
	}
	/**
	 * @return the latest_op_date
	 */
	public Date getLatest_op_date() {
		return latest_op_date;
	}
	/**
	 * @param latest_op_date the latest_op_date to set
	 */
	public void setLatest_op_date(Date latest_op_date) {
		this.latest_op_date = latest_op_date;
	}
	/**
	 * @return the last_ver
	 */
	public String getLast_ver() {
		return last_ver;
	}
	/**
	 * @param last_ver the last_ver to set
	 */
	public void setLast_ver(String last_ver) {
		this.last_ver = last_ver;
	}
	
	public abstract BaseData getParent();
	/**
	 * @return the parentpath
	 */
	public String getParentpath() {
		if (CommUtil.isEmpty(parentpath)) {
			parentpath = nestedTreePath(this);
		}
		return parentpath;
	}
	private String nestedTreePath(BaseData bd) {
		if (bd.getParent()==null) {
			return "/"+bd.getId();
		} else {
			return nestedTreePath(bd.getParent())+"/"+bd.getId();
		}
	}
	
	public void setTreepath(String treepath){
		 this.treepath=treepath;
	}
	public String getTreepath() {
		return treepath;
	}
	public String getStandard_code() {
		return code;
	}

}
