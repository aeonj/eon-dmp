/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2017年1月22日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

/**
 * 机构类型，设置对应的用户机构
 * @author aeon
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "orgtype")
public class OrgType extends IdEntity implements Comparable<OrgType> {
	
	private static final long serialVersionUID = -1236283675841399915L;
	
	@Column(unique = true,length=42)
	private String orgCode;    //机构类型编码
	private String orgName;    //机构类型名称
	@Column(length=42)
	private String eleCode;    //对应的要素
	@Transient
	private List<Dto> child = new ArrayList<Dto>();
	
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}
	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	/**
	 * @return the eleCode
	 */
	public String getEleCode() {
		return eleCode;
	}
	/**
	 * @param eleCode the eleCode to set
	 */
	public void setEleCode(String eleCode) {
		this.eleCode = eleCode;
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
	@Override
	public int compareTo(OrgType obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}
	
}
