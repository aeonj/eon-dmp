package eon.hg.fap.db.model.primary;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.*;

/**
 * 权限组类
 * @author AEON
 *
 */
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Data
@Entity
@Table(name = Globals.SYS_TABLE_SUFFIX + "part_group")
public class PartGroup extends IdEntity {
	private static final long serialVersionUID = -3648007497998924209L;
	
	private String belong_name;   //权限组名称
	private String belong_source;// 对应要素列表,json格式
	@OneToMany(cascade= CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "pg")
	private Set<PartDetail> details = new HashSet<PartDetail>();
	@Lock
	@Column(length=42)
	private String rg_code;
	@Transient
	private List<Dto> detailList;
	
	/**
	 * @return the detailList
	 */
	public List<Dto> getDetailList() {
		if (detailList==null) {
			detailList = new ArrayList<Dto>();
			Dto dtoTemp = new HashDto();
			for (PartDetail pd : this.details) {
				dtoTemp.put(pd.getEleCode(), dtoTemp.getString(pd.getEleCode())+","+pd.getValue());
			}
			Iterator it = dtoTemp.keySet().iterator();
			while (it.hasNext()) {
				Object key = it.next();
				String value = CommUtil.null2String(dtoTemp.get(key));
				if (value.length()>0) {
					value = value.substring(1);
					Dto dto = new HashDto();
					dto.put("eleCode", CommUtil.null2String(key));
					dto.put("eleValue", value);
					detailList.add(dto);
				}
			}
		}
		return detailList;
	}

}
