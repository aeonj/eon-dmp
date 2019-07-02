package eon.hg.fap.db.service.impl;

import eon.hg.fap.common.CommUtil;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.db.dao.primary.AccessoryDao;
import eon.hg.fap.db.model.primary.Accessory;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.service.IAccessoryService;
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
public class AccessoryServiceImpl implements IAccessoryService {
	@Resource
	private AccessoryDao accessoryDao;
	
	public boolean save(Accessory accessory) {
		/**
		 * init other field here
		 */
		try {
			this.accessoryDao.save(accessory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Accessory getObjById(Long id) {
		Accessory accessory = this.accessoryDao.get(id);
		if (accessory != null) {
			return accessory;
		}
		return null;
	}
	
	public boolean delete(Long id) {
		try {
			this.accessoryDao.remove(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean batchDelete(List<Long> accessoryIds) {
		// TODO Auto-generated method stub
		for (Serializable id : accessoryIds) {
			delete((Long) id);
		}
		return true;
	}
	
	public IPageList list(IQueryObject properties) {
		return accessoryDao.list(properties);
	}
	
	public boolean update(Accessory accessory) {
		try {
			this.accessoryDao.update( accessory);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
		User current_user = SecurityUserHolder.getCurrentUser();
		accessory.setIs_deleted(true);
		accessory.setLastTime(new Date());
		accessory.setLastUser(current_user.getTrueName());
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
