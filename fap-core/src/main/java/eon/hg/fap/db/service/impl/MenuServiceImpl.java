package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.MenuDao;
import eon.hg.fap.db.model.primary.Menu;
import eon.hg.fap.db.service.IMenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuServiceImpl implements IMenuService {
	@Resource
	private MenuDao menuDao;

	public Menu save(Menu menu) {
		return this.menuDao.save(menu);
	}
	
	public Menu getObjById(Long id) {
		return this.menuDao.get(id);
	}
	
	public void delete(Long id) {
		this.menuDao.remove(id);
	}
	
	public void batchDelete(List<Long> menuIds) {
		for (Long id : menuIds) {
			delete(id);
		}
	}
	
	public IPageList list(IQueryObject properties) {
		return menuDao.list(properties);
	}

	public Page<Menu> list(Pageable pageable) {
		return this.menuDao.findAll((Specification<Menu>) null,pageable);
	}

    @Override
    public List<Menu> find(IQueryObject qo) {
        return this.menuDao.find(qo);
    }

    public Menu update(Menu menu) {
			return this.menuDao.update( menu);
	}
	public List<Menu> query(String query, Map params, int begin, int max){
		return this.menuDao.query(query, params, begin, max);
		
	}

	@Override
	public Menu getObjByProperty(String construct, String propertyName,
			Object value) {
		return this.menuDao.getBy(construct, propertyName, value);
	}

	@Override
	public void deleteAllChilds(List<Long> idlist) {
		List<Menu> menuList = this.menuDao.findAllById(idlist);
		LinkedList<Menu> delList = new LinkedList<>();

		for (Menu menu : menuList) {
			delList.push(menu);
		}
		while (!delList.isEmpty()) {
			Menu delMenu = delList.pop();
			Specification<Menu> sf = (Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
				Predicate finalConditions = criteriaBuilder.conjunction();
				finalConditions = criteriaBuilder.and(finalConditions, criteriaBuilder.equal(root.get("parent_id"), delMenu.getId()));
				return finalConditions;
			};
			List<Menu> menus = this.menuDao.findAll(sf);
			if (menus.size() == 0) {
				this.menuDao.delete(delMenu);
			} else {
				for (Menu menu1 : menus) {
					delList.push(menu1);
				}
			}
		}
	}

}
