/**
 * 
 */
package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

/**
 * 用户操作管理类，用来管理菜单对应的工具栏操作信息，也可扩展到界面中的任何界面元素
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "region_operate")
public class RegionOperate extends IdEntity {

	private static final long serialVersionUID = 6939308736035497644L;
	
	@Column(length=20)
	private String priorname;    //上一级操作显示名称
	@Column(length=20)
	private String dispname;    //操作显示名称
	@Column(length=42)
	private String authtype;    //授权类型  对应枚举类型PARTAUTHTYPE
	@OneToOne
	private Operate operate;      //对应的操作
	@ManyToOne
	private Region region;      //对应的管理员用户
	
	/**
	 * @return the authtype
	 */
	public String getAuthtype() {
		return authtype;
	}
	/**
	 * @param authtype the authtype to set
	 */
	public void setAuthtype(String authtype) {
		this.authtype = authtype;
	}
	/**
	 * @return the operate
	 */
	public Operate getOperate() {
		return operate;
	}
	/**
	 * @param operate the operate to set
	 */
	public void setOperate(Operate operate) {
		this.operate = operate;
	}
	/**
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(Region region) {
		this.region = region;
	}
	/**
	 * @return the dispname
	 */
	public String getDispname() {
		return dispname;
	}
	/**
	 * @param dispname the dispname to set
	 */
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}
	/**
	 * @return the priorname
	 */
	public String getPriorname() {
		return priorname;
	}
	/**
	 * @param priorname the priorname to set
	 */
	public void setPriorname(String priorname) {
		this.priorname = priorname;
	}
}
