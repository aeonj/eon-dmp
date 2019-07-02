package eon.hg.fap.db.service;

import eon.hg.fap.db.model.primary.SysConfig;

public interface ISysConfigService {
	/**
	 * 保存一个SysConfig，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	boolean save(SysConfig instance);
	
	/**
	 * 删除一个SysConfig
	 * 
	 * @param id
	 * @return
	 */
	boolean delete(Long id);
			
	/**
	 * 更新一个SysConfig
	 * 
	 * @param id
	 *            需要更新的SysConfig的id
	 * @param dir
	 *            需要更新的SysConfig
	 */
	boolean update(SysConfig instance);
	
	/**
	 * 
	 * @return
	 */
	SysConfig getSysConfig();

}
