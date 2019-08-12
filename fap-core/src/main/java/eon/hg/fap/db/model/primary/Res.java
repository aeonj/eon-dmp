package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源类,系统权限资源对应URL信息，SpringSecurity根据权限资源信息进行URL拦截处理
 * @author apple
 *
 */
@Entity
@Data
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
	@ManyToMany(mappedBy = "ress", targetEntity = Role.class, fetch = FetchType.LAZY)
	private List<Role> roles = new ArrayList<Role>();
	private boolean entrance=false;   //入口

	@Override
	public int compareTo(Res obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}

}
