package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 应用系统类
 * @author AEON
 *
 */
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "app")
public class App extends IdEntity implements Comparable<App> {

	private static final long serialVersionUID = -2606534317323429290L;
	private String name;// 应用名称
	private String menuname;// 应用菜单名称
	private String dispname;// 应用菜单名称
	private int sequence;// 序号

	public App(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public App() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the menuname
	 */
	public String getMenuname() {
		return menuname;
	}

	/**
	 * @param menuname the menuname to set
	 */
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	/**
	 * @return the dispname
	 */
	public String getDispname() {
		return dispname;
	}

	/**
	 * @param dispname the dispname to set
	 */
	public void setDispname(String dispname) {
		this.dispname = dispname;
	}

	/**
	 * @return the sequence
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Override
	public int compareTo(App obj) {
		if (super.getId().equals(obj.getId())) {
			return 0;
		}
		return 1;
	}

}
