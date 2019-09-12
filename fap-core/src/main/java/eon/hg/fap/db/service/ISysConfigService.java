package eon.hg.fap.db.service;

import eon.hg.fap.core.cache.CacheOperator;
import eon.hg.fap.db.model.primary.SysConfig;

public interface ISysConfigService extends CacheOperator {
	/**
	 * 保存一个SysConfig，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	SysConfig save(SysConfig instance);
	
	/**
	 * 删除一个SysConfig
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
			
	/**
	 * 更新一个SysConfig
	 */
	SysConfig update(SysConfig instance);
	
	/**
	 * 
	 * @return
	 */
	SysConfig getSysConfig();

}
