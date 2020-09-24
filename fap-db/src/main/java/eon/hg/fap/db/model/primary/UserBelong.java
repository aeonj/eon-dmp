/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2017年2月4日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.*;

/**
 * 用户机构权限组类
 * @author aeon
 *
 */
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "userbelong")
public class UserBelong extends IdEntity {
	private static final long serialVersionUID = -8559866174935141545L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
	@Column(length=42)
	private String ele_code;
	private Long ele_id;
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	/**
	 * @return the ele_code
	 */
	public String getEle_code() {
		return ele_code;
	}
	/**
	 * @param ele_code the ele_code to set
	 */
	public void setEle_code(String ele_code) {
		this.ele_code = ele_code;
	}
	/**
	 * @return the ele_id
	 */
	public Long getEle_id() {
		return ele_id;
	}
	/**
	 * @param ele_id the ele_id to set
	 */
	public void setEle_id(Long ele_id) {
		this.ele_id = ele_id;
	}
}
