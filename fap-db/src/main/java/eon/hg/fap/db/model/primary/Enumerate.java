/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年12月15日
 * @author cxj
 */

package eon.hg.fap.db.model.primary;

import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author aeon
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "enumerate")
public class Enumerate extends IdEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3589038204391975921L;
	
	@Column(length=42)
	private String field;   //对照字段
	private String fieldname;   //对照字段名称
	@Column(length=42)
	private String code;   //代码
	private String codedesc;     //代码描述
	private int sortno;        //排序号
	private String remark;      //备注
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getFieldname() {
		return fieldname;
	}
	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodedesc() {
		return codedesc;
	}
	public void setCodedesc(String codedesc) {
		this.codedesc = codedesc;
	}
	public int getSortno() {
		return sortno;
	}
	public void setSortno(int sortno) {
		this.sortno = sortno;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
