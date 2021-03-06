package eon.hg.fap.core.constant;

/**
 * 系统常量类，这里的常量是系统默认值，可以在系统中进行用户定制
 * @author AEON
 * 
 */
public class Globals {
	public final static String DEFAULT_SYSTEM_TITLE = " 禾冠行业云平台系统";// 系统默认名称
	public final static String DEFAULT_MANAGE_FRAME = "ext";// 系统后台业务管理默认框架
	public final static boolean SSO_SIGN = true;// 是否允许单点登录
	public final static int DEFAULT_DNS_VERSION = 201907101;// 软件发布小版本号
	public final static String DEFAULT_DNS_OUT_VERSION = "V2.0";// 软件大版本号
	public final static String DEFAULT_WBESITE_NAME = "Dinosaur";// 软件名称
	public final static String DEFAULT_CLOSE_REASON = "系统维护中...";// 系统关闭原因默认值
	public final static String DERAULT_USER_TEMPLATE = "user_templates";// 用户各种报表模板
	public final static String UPLOAD_FILE_PATH = "upload";// 默认文件上传路径
	public final static String DEFAULT_AUTH_SUFFIX = "ROLE_";  //默认权限值前缀名
	public final static String DEFAULT_SYSTEM_LANGUAGE = "zh_cn";// 默认系统语言为简体中文
	public final static String DEFAULT_SYSTEM_PAGE_ROOT = "templates/";// 默认系统页面根路径
	public final static String SYSTEM_MANAGE_PAGE_PATH = "templates/zh_cn/system/";// 默认系统后台页面路径为
	public final static String SYSTEM_FORNT_PAGE_PATH = "templates/zh_cn/front/";// 默认系统页面前台路径
	public final static String SYSTEM_DATA_BACKUP_PATH = "data";// 系统数据备份默认路径
	public final static Boolean SYSTEM_UPDATE = true;// 系统是否需要升级,预留字段
	public final static boolean SAVE_LOG = true;// 是否记录日志
	public final static String SECURITY_CODE_TYPE = "normal";// 默认验证码类型
	public final static boolean EAMIL_ENABLE = true;// 邮箱默认开启
	public final static String DEFAULT_IMAGESAVETYPE = "sidImg";// 默认图片存储路径格式
	public final static int DEFAULT_IMAGE_SIZE = 1024;// 默认上传图片最大尺寸,单位为KB
	public final static String DEFAULT_IMAGE_SUFFIX = "gif|jpg|jpeg|bmp|png|tbi";// 默认上传图片扩展名
	public final static int DEFAULT_IMAGE_SMALL_WIDTH = 160;// 默认小图片宽度
	public final static int DEFAULT_IMAGE_SMALL_HEIGH = 160;// 默认小图片高度
	public final static int DEFAULT_IMAGE_MIDDLE_WIDTH = 300;// 默认中图片宽度
	public final static int DEFAULT_IMAGE_MIDDLE_HEIGH = 300;// 默认中图片高度
	public final static int DEFAULT_IMAGE_BIG_WIDTH = 1024;// 默认大图片宽度
	public final static int DEFAULT_IMAGE_BIG_HEIGH = 1024;// 默认大图片高度
	public final static String DEFAULT_TABLE_SUFFIX = "hg_";// 默认表前缀名
	public final static String SYS_TABLE_SUFFIX = "sys_";// 系统表前缀名
	public final static String FLOW_TABLE_SUFFIX = "wf_";// 系统表前缀名
	public final static String ELE_TABLE_SUFFIX = "ele_";// 基础表前缀名
	public final static String THIRD_ACCOUNT_LOGIN = "eon_login_";// 第三方账号登录的前缀
	public final static String DEFAULT_SMS_URL = "http://service.winic.org/sys_port/gateway/";// 暂时使用第三方，以后公司会接入自己的接口
	public final static String DEFAULT_THEME = "triton";// 暂时使用第三方，以后公司会接入自己的接口
	public final static String DEFAULT_BIND_DOMAIN_CODE = "75A520E9FBE608E91D6CCDEC0E05740C97B9DCE99C5934CD589CFE4E769668CBA";// 绑定的域名加密字符串
	public final static String DEFAULT_3DES_KEY = "F68E28ADC899FD210FE69B90C6BD1EF9ADDE11D2A9BA6AA9";  //3DES密钥

	public static Boolean MUST_LOGIN_FLAG=false;   //是否必须登陆
	public static Boolean DEFAULT_SYS_TYPE=true;   //默认只使用业务系统类别

}
