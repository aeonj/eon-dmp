package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统提醒管理类，用来向系统管理员发送相关提醒信息，如手机充值时平台余额不足需要向管理员发送余额不足信息，
 * 管理员登陆后台即可看到该提醒，并进行优先处理
 * @author AEON
 * 
 */
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "sys_tip")
public class SystemTip extends IdEntity {
	private static final long serialVersionUID = 7745159338275939524L;
	private String st_title;// 提醒标题
	private int st_level;// 提醒等级，等级越高在管理后台显示越靠前，优先提醒管理员
	private int st_status;// 提醒状态，0为为未处理，1为已经处理
	@Column(length = 2000)
	private String st_content;// 提醒内容

	public SystemTip() {
		super();
	}

	public SystemTip(Long id, Date addTime) {
		super(id, addTime);
	}

	public int getSt_status() {
		return st_status;
	}

	public void setSt_status(int st_status) {
		this.st_status = st_status;
	}

	public String getSt_title() {
		return st_title;
	}

	public void setSt_title(String st_title) {
		this.st_title = st_title;
	}

	public int getSt_level() {
		return st_level;
	}

	public void setSt_level(int st_level) {
		this.st_level = st_level;
	}

	public String getSt_content() {
		return st_content;
	}

	public void setSt_content(String st_content) {
		this.st_content = st_content;
	}

}
