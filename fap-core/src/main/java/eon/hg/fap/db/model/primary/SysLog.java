/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年1月25日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "syslog")
public class SysLog extends IdEntity {
	private static final long serialVersionUID = 1494739655436824792L;
	private String title;
	private int type;// 0为普通日志，1为异常日志
	@Lob
	@Column(length = 2000)
	private String content;
	private String user_name;// 用户名称
	private String ip;
	private Long user_id;// 对应的用户
	private String ip_city;// ip所在城市
	private String op_url;  //操作地址
	private String ids;     //操作数据ID集合
	@Lock
	@Column(length = 42,nullable = false)
	private String rg_code; //用户域
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the user_id
	 */
	public Long getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the ip_city
	 */
	public String getIp_city() {
		return ip_city;
	}
	/**
	 * @param ip_city the ip_city to set
	 */
	public void setIp_city(String ip_city) {
		this.ip_city = ip_city;
	}
	/**
	 * @return the op_url
	 */
	public String getOp_url() {
		return op_url;
	}
	/**
	 * @param op_url the op_url to set
	 */
	public void setOp_url(String op_url) {
		this.op_url = op_url;
	}
	/**
	 * @return the ids
	 */
	public String getIds() {
		return ids;
	}
	/**
	 * @param ids the ids to set
	 */
	public void setIds(String ids) {
		this.ids = ids;
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


}
