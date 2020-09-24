package eon.hg.fap.db.model.primary;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统区域类，默认导入全国区域省、市、县（区）三级数据
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "area")
public class Area extends IdEntity {

	private static final long serialVersionUID = -3950293361610980697L;
	
	private String areaName;// 区域名称
	@ManyToOne
	private Area parent;// 上级区域
	private int sequence;// 序号
	@Column(name = "level_num")
	private int level;// 层级
	private boolean common=false;// 常用地区
	@Transient
	private List<Dto> sons = new ArrayList();   //用于树型展示

	public Area(Long id, Date addTime) {
		super(id, addTime);
		// TODO Auto-generated constructor stub
	}

	public Area() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isCommon() {
		return common;
	}

	public void setCommon(boolean common) {
		this.common = common;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the sons
	 */
	public List<Dto> getSons() {
		return sons;
	}

	/**
	 * @param sons the sons to set
	 */
	public void setSons(List<Dto> sons) {
		this.sons = sons;
	}

}
