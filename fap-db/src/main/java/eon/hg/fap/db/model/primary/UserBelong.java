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

	private Long user_id;
	@Column(length=42)
	private String ele_code;
	private String ele_value;

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getEle_code() {
		return ele_code;
	}

	public void setEle_code(String ele_code) {
		this.ele_code = ele_code;
	}

	public String getEle_value() {
		return ele_value;
	}

	public void setEle_value(String ele_value) {
		this.ele_value = ele_value;
	}
}
