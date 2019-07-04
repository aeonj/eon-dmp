/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月7日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.db.model.mapper.BaseData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.ELE_TABLE_SUFFIX + "department")
public class EleDepartment extends BaseData {
	
	private static final long serialVersionUID = -4690054724830150725L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private EleDepartment parent;

	/**
	 * @return the parent
	 */
	public EleDepartment getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(EleDepartment parent) {
		this.parent = parent;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}