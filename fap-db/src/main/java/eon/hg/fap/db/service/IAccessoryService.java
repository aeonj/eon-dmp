package eon.hg.fap.db.service;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.model.primary.Accessory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface IAccessoryService {
	/**
	 * 保存一个Accessory，如果保存成功返回true，否则返回false
	 * 
	 * @param instance
	 * @return 是否保存成功
	 */
	Accessory save(Accessory instance);
	
	/**
	 * 根据一个ID得到Accessory
	 * 
	 * @param id
	 * @return
	 */
	Accessory getObjById(Long id);
	
	/**
	 * 删除一个Accessory
	 * 
	 * @param id
	 * @return
	 */
	void delete(Long id);
	
	/**
	 * 批量删除Accessory
	 * 
	 * @param ids
	 * @return
	 */
	void batchDelete(List<Long> ids);
	
	/**
	 * 通过一个查询对象得到Accessory
	 * 
	 * @param properties
	 * @return
	 */
	IPageList list(IQueryObject properties);
	
	/**
	 * 更新一个Accessory
	 * 
	 * @param instance
	 *            需要更新的Accessory
	 */
	Accessory update(Accessory instance);
	
	/**
	 * 
	 * @param query
	 * @param params
	 * @param begin
	 * @param max
	 * @return
	 */
	List<Accessory> query(String query, Map params, int begin, int max);
	
	/**
	 * 
	 * @param fields
	 * @return
	 */
	Accessory getObjByProperty(Object... fields);

	/**
	 * 物理删除文件和逻辑删除数据库文件
	 * @return
	 */
	boolean deleteFile(Accessory accessory, HttpServletRequest request);
	
	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param path
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	boolean deleteDirectory(String path);		
	
}
