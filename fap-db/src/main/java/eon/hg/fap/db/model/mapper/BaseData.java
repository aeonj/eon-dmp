/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月1日
 * @author cxj
 */

package eon.hg.fap.db.model.mapper;

import com.alibaba.fastjson.annotation.JSONField;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.beans.SpringUtils;
import eon.hg.fap.core.cache.RedisPool;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.db.service.IBaseDataService;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AEON
 *
 */
@MappedSuperclass
@Data
public abstract class BaseData extends IdEntity implements Serializable {
	
	@Column(length = 42)
	private String code;
	@Column
	private String name;
	@Transient
	private String text;
	@Transient
	private List<Dto> child = new ArrayList<>();
	@Column
	private String treepath;
	@Transient
    @JSONField(serialize = false)
	private String parentpath;
	@Transient
    @JSONField(serialize = false)
	private BaseData tree_parent;
	@Transient
	private List<BaseData> children;
	@Column(name = "level_num")
	private Byte level=1;
	private boolean leaf=true;
	private boolean enabled=true;
	private String last_ver;
	private Long parent_id;
	@Transient
	private Long old_parent_id;

	@JSONField(serialize = false)
	public BaseData getParent() {
		IBaseDataService baseDataService = SpringUtils.getBean("baseDataServiceImpl",IBaseDataService.class);
		if (CommUtil.isNotEmpty(this.getParent_id())) {
			return baseDataService.getObjById(this.getClass(),this.getParent_id());
		} else {
			return null;
		}
	}

	/**
	 * @return the parentpath
	 */
	public String getParentpath() {
		if (CommUtil.isEmpty(parentpath)) {
			parentpath = nestedTreePath(this);
		}
		return parentpath;
	}
	private String nestedTreePath(BaseData bd) {
		BaseData parent =bd.getParent();
		if (parent==null) {
			return "/"+bd.getId();
		} else {
			return nestedTreePath(parent)+"/"+bd.getId();
		}
	}
	
	public String getStandard_code() {
		return code;
	}

	public boolean isRedis() {
		return SpringUtils.getBean("redisPool")!=null;
	}

}
