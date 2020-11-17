package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.support.model.SConfig;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

/**
 * @author apple
 *
 */
@Data
@ToString(exclude={"websiteLogo","memberIcon","admin_login_logo","admin_manage_logo"})
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "sysconfig")
public class SysConfig extends IdEntity implements SConfig {
	private static final long serialVersionUID = -4157357427429866674L;
	private String title;// 系统标题
	private String websiteName;// 网站名称
	private String uploadFilePath;// 用户上传文件路径
	private String sysLanguage;// 系统语言
	private String company_name;  //公司名称
	private String imageSuffix;// 图片的后缀名
	private String imageWebServer;// 图片服务器地址
	private int imageFilesize;// 允许图片上传的最大值
	private String imageSaveType;// 图片保存类型
	private int smallWidth;// 最小尺寸像素宽
	private int smallHeight;
	private int middleWidth;// 中尺寸像素宽
	private int middleHeight;
	private int bigWidth;// 大尺寸像素高
	private int bigHeight;
	private String securityCodeType;// 验证码类型
	private boolean securityCodeRegister=true;// 前台注册验证
	private boolean securityCodeLogin=true;// 前台登陆验证
	private boolean smsEnbale=false;// 短信平台开启
	private String smsURL;// 短信平台发送地址
	private String smsUserName;// 短信平台用户名
	private String smsPassword;// 短信平台用户密码
	private String smsTest;// 短信测试发送账号
	private boolean emailEnable=false;// 邮件是否开启
	private String emailHost;// stmp服务器
	private int emailPort;// stmp端口
	private String emailUser;// 发件人
	private String emailUserName;// 邮箱用户名
	private String emailPws;// 邮箱密码
	private String emailTest;// 邮件发送测试
	@OneToOne(cascade = CascadeType.ALL)
	private Accessory websiteLogo;// 网站logo
	private boolean websiteState=true;// 网站状态(开/关)
	private boolean second_domain_open=false;// 是否开通二级域名
	private String closeReason;// 网站关闭原因
	@OneToOne(cascade = CascadeType.ALL)
	private Accessory memberIcon;// 默认用户图标
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Accessory admin_login_logo;// 后台业务系统登录页的左上角Logo
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Accessory admin_manage_logo;// 后台业务管理中心左上角的Logo

	private boolean login_year=false;   //是否显示登录年度
	private String address;// 限制网站访问地址，填写网址
	private String welcomeType; //首页类型，0:默认空白 1:统计检查系统 2:非金融系统
	@Column(length = 2)
	private int menu_tab = 1;  //功能样式  0:单页面 1:多页面
	private Date updDate; //项目最新更新时间
    @ColumnDefault("0")
	private int request_count=0;  //Controller请求数目
    @ColumnDefault("0")
	private boolean db_reset=false;  //是否重置平台数据
    @ColumnDefault("0")
	private boolean display_menu_group=false;  //是否显示菜单组
    @ColumnDefault("1")
    private boolean default_sys_type=true;   //是否默认业务系统类型，涉及到首页跳转页面，设置为true，首页默认跳转到manage/index.htm
    @ColumnDefault("1")
    private boolean must_login_flag=true;   //是否默认业务系统类型，涉及到首页跳转页面，设置为true，首页默认跳转到manage/index.htm

}
