/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年1月25日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.support.model.UConfig;
import lombok.Data;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户参数类,记录用户相关的一些其他属性
 * @author AEON
 *
 */
@Data
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = Globals.SYS_TABLE_SUFFIX + "userconfig")
public class UserConfig extends IdEntity implements UConfig {
	private static final long serialVersionUID = -6619413529532587073L;
	@Column(unique = true, nullable = false)
	private long user_id;
	@Column(columnDefinition = "varchar(20)")
	private String theme;     //后台业务平台EXT默认样式
	@Column(columnDefinition = "varchar(5)")
	private String layout;    //后台业务平台EXT缺省主界面布局模式。1:传统经典布局;2:个性桌面布局。
	@Column(columnDefinition = "varchar(30)")
	private String elecode_type;   //基础数据显示编码类别   standard：标准编码
    @Column(length = 60)
    private String f_user_id;   //F3的用户ID
    @Column(length = 60)
    private String f_role_id;   //F3的用户ID

}
