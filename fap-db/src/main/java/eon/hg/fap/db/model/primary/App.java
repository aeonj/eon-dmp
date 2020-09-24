package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 应用系统类
 * @author AEON
 *
 */
@Entity
@Data
@Table(name = Globals.SYS_TABLE_SUFFIX + "app")
public class App extends IdEntity implements Comparable<App> {

	private String code;// 应用编码
	private String name;// 应用名称
	private String menuname;// 应用菜单名称
	private String dispname;// 应用菜单名称
	private int sequence;// 序号

	@Override
	public int compareTo(App obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}

}
