package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 资源类,系统权限资源对应URL信息，SpringSecurity根据权限资源信息进行URL拦截处理
 * @author apple
 *
 */
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "res")
public class Res extends IdEntity implements Comparable<Res>{

	private static final long serialVersionUID = 5822185101857472630L;
	private String resName;
	@Column(name = "rtype")
	private String type;// 这里只需要进行URL类型的过滤
	private String value;
	private int sequence;
	private String info;
	private String permission;
	private boolean entrance=false;   //入口

	public Res() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Res(Long id, String value) {
		super.setId(id);
		this.value = value;
	}

	public Res(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public boolean isEntrance() {
		return entrance;
	}

	public void setEntrance(boolean entrance) {
		this.entrance = entrance;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	@Override
	public int compareTo(Res obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}

}
