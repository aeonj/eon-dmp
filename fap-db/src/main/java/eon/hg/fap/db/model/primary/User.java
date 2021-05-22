/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年1月25日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import com.alibaba.fastjson.annotation.JSONField;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.constant.AeonConstants;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.tools.JsonHandler;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.*;
import java.util.function.Supplier;

/**
 * 用户类,所有用户都在这张表进行管理
 * @author AEON
 *
 */
@Data
@ToString
@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = Globals.SYS_TABLE_SUFFIX + "user", indexes = {
		@Index(name = "idx_user", columnList = "userName")})
public class User extends IdEntity {
	private static final long serialVersionUID = -6076365663342941338L;
	@Lock
	private String userName;// 用户名
	private String nickName;// 昵称
	private String trueName;// 真实姓名

	@Lock
	@JSONField(serialize = false)
	private String password;// 密码
	private String userRole;// 用户角色，登录时根据不同用户角色导向不同的管理页面,MANAGE是后台管理类,可用于纯业务系统,PUBLIC是指公众用户，一般指注册用户，对于需要入驻的用户再按照类别扩展
	private Date birthday;// 出生日期
	private String telephone;// 电话号码
	private String QQ;// 用户QQ
	private String weixin;//用户微信
	private String WW;// 用户阿里旺旺
	//add by aeon 2015.5.28
	@Lock
	@Column(length =42,nullable=false)
	private String rg_code = AeonConstants.SUPER_RG_CODE;
	@Column(length =4)
	private int years=0;// 用户年龄
	private String address;// 用户地址
	private int sex;// 性别 1为男、0为女、-1为保密
	private String email;// 邮箱地址
	private String mobile;// 手机号码
	private String card; // 身份证号
	private Long photo_id;// 用户照片
	private Long area_id;// 用户家乡地区信息
	@ManyToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
	@JoinTable(name = Globals.SYS_TABLE_SUFFIX + "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new TreeSet();
	@Transient
	private List<Menu> menus;
	@Transient
	private List<MenuGroup> mgs;
	@OneToOne
	private UserConfig config;

	private String belong_source;    //json格式存放权限组成员source
	private Long pg_id;     //是否自定义权限
	private Long orgtype_id;   //所属机构类型
    @Column(length = 42)
	private String orgtype_ele_id;       //所属机构ID
	
	private int manageType=0;   //后台管理用户类别  0:普通用户 1:区域管理员
	@Transient
	private boolean is_f3_login = false;  //用于F3同步登录
	@Transient
	private List<Dto> belongList;

	/**
	 * 获取不重复的用户菜单
	 * @return
	 */
	public List<Menu> getMenus() {
		if (this.menus==null) {
			this.menus = new ArrayList<Menu>();
			for (Role role : roles) {
				for (Menu menu : role.getMenus()) {
					if (AeonConstants.SUPER_USER.equals(this.getUserName()) || menu.getManageTypes().contains(String.valueOf(this.getManageType()))) {
						if (!menus.contains(menu)) {
							menus.add(menu);
							if (menu.getMg()!=null) {
								if (this.mgs==null) {
									this.mgs = new ArrayList<MenuGroup>();
								}
								if (!this.mgs.contains(menu.getMg())) {
									this.mgs.add(menu.getMg());
								}
								menu.getMg().getAuthmenus().add(menu);
							}
						}
					}
				}
			}
		}
		return this.menus;
	}
	
	public List<MenuGroup> getMgs() {
		if (this.mgs==null) {
			if (this.mgs==null) {
				this.mgs = new ArrayList<MenuGroup>();
			}
			this.getMenus();
			Collections.sort(this.mgs, new Comparator<MenuGroup>() {  
	            public int compare(MenuGroup arg0, MenuGroup arg1) {  
	                int hits0 = arg0.getSequence();  
	                int hits1 = arg1.getSequence();  
	                if (hits1 < hits0) {  
	                    return 1;  
	                } else if (hits1 == hits0) {  
	                    return 0;  
	                } else {  
	                    return -1;  
	                }  
	            }  
	        });

		}
		return this.mgs;
	}

	/**
	 * @return the belongList
	 */
	public List<Dto> getBelongList(Supplier<List<UserBelong>> belongs) {
		if (belongList==null) {
			belongList = new ArrayList<>();
			if (CommUtil.isNotEmpty(this.belong_source)) {
				List sourceList = JsonHandler.parseList(this.belong_source);
				for (int i = 0; i < sourceList.size(); i++) {
					Dto dto = (Dto) sourceList.get(i);
					StringBuilder eleValues = new StringBuilder();
					for (UserBelong pd : belongs.get()) {
						if (dto.getString("eleCode").equals(pd.getEle_code())) {
							eleValues.append(",").append(pd.getEle_value());
						}
					}
					if (CommUtil.isNotEmpty(eleValues.toString())) {
						dto.put("eleValue", eleValues.toString().substring(1));
					}
					belongList.add(dto);
				}
			}
		}
		return belongList;
	}



}
