/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年1月25日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.LogType;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author AEON
 *
 */
@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "syslog")
public class SysLog extends IdEntity {
	private static final long serialVersionUID = 1494739655436824792L;
	private String title;
	private int type;// 0为普通日志，1为异常日志
	private LogType op_type;
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

}
