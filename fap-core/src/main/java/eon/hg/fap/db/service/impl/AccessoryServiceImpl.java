package eon.hg.fap.db.service.impl;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.dao.primary.AccessoryDao;
import eon.hg.fap.db.model.primary.Accessory;
import eon.hg.fap.db.service.IAccessoryService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@CacheConfig(cacheNames = Globals.DEFAULT_TABLE_SUFFIX+"accessory")
public class AccessoryServiceImpl implements IAccessoryService {
	@Resource
	private AccessoryDao accessoryDao;

	@CachePut(key = "#accessory.id")
	public Accessory save(Accessory accessory) {
		return this.accessoryDao.save(accessory);
	}

	@Cacheable(key = "#id")
	public Accessory getObjById(Long id) {
		return this.accessoryDao.get(id);
	}

	@CacheEvict(key = "#id")
	public void delete(Long id) {
		this.accessoryDao.remove(id);
	}

	public void batchDelete(List<Long> accessoryIds) {
		for (Serializable id : accessoryIds) {
			delete((Long) id);
		}
	}
	
	public IPageList list(IQueryObject properties) {
		return accessoryDao.list(properties);
	}

	@CachePut(key = "#accessory.id")
	public Accessory update(Accessory accessory) {
		return this.accessoryDao.update( accessory);
	}
	public List<Accessory> query(String query, Map params, int begin, int max){
		return this.accessoryDao.query(query, params, begin, max);
		
	}
	
	@Override
	public Accessory getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.accessoryDao.getBy(construct, propertyName, value);
	}

	@Override
	public boolean deleteFile(Accessory accessory,HttpServletRequest request) {
		accessory.setIs_deleted(true);
		accessory.setLastTime(new Date());
		accessory.setLastUser(SecurityUserHolder.getOnlineUser().getUsername());
		this.accessoryDao.update( accessory);
		String saveFilePathName = request.getSession().getServletContext()
				.getRealPath("/");
		CommUtil.deleteFile(saveFilePathName+accessory.getPath()+"/"+accessory.getName());
		return true;
	}
	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param path
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String path) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!path.endsWith(File.separator)) {
			path = path + File.separator;
		}
		File dirFile = new File(path);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile1(files[i].getAbsolutePath());
				if (!flag)
					break;
			} // 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag)
			return false;
		return flag;
		
	}
	
	public boolean deleteFile1(String path) {
		boolean flag = false;
		File file = new File(path);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}
}
