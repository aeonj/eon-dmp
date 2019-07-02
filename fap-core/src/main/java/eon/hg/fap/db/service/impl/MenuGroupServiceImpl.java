package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.dao.primary.MenuGroupDao;
import eon.hg.fap.db.model.primary.MenuGroup;
import eon.hg.fap.db.service.IMenuGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class MenuGroupServiceImpl implements IMenuGroupService {

    @Resource
    private MenuGroupDao menuGroupDAO;

    /**
     * 保存一个MenuGroup，如果保存成功返回true，否则返回false
     *
     * @param instance
     * @return 是否保存成功
     */
    @Override
    public boolean save(MenuGroup instance) {
        return false;
    }

    /**
     * 根据一个ID得到MenuGroup
     *
     * @param id
     * @return
     */
    @Override
    public MenuGroup getObjById(Long id) {
        return this.menuGroupDAO.get(id);
    }

    /**
     * 删除一个MenuGroup
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(Long id) {
        return false;
    }

    /**
     * 批量删除MenuGroup
     *
     * @param ids
     * @return
     */
    @Override
    public boolean batchDelete(List<Long> ids) {
        return false;
    }

    /**
     * 通过一个查询对象得到MenuGroup
     *
     * @param properties
     * @return
     */
    @Override
    public IPageList list(IQueryObject properties) {
        return null;
    }

    /**
     * 通过查询对象获取未分页的MengGroup列表
     *
     * @param qo
     * @return
     */
    @Override
    public List<MenuGroup> find(IQueryObject qo) {
        return this.menuGroupDAO.find(qo);
    }

    /**
     * 更新一个MenuGroup
     *
     * @param instance
     */
    @Override
    public boolean update(MenuGroup instance) {
        return false;
    }

    /**
     * @param query
     * @param params
     * @param begin
     * @param max
     * @return
     */
    @Override
    public List<MenuGroup> query(String query, Map params, int begin, int max) {
        return menuGroupDAO.query(query,params,begin,max);
    }

    /**
     * @param construct
     * @param propertyName
     * @param value
     * @return
     */
    @Override
    public MenuGroup getObjByProperty(String construct, String propertyName, Object value) {
        return null;
    }

    @Transactional
    @Override
    public boolean saveDirtyData(String insertdata, String updatedata, String removedata) {
        List<MenuGroup> inserts = JsonHandler.parseList(insertdata,MenuGroup.class);
        for (MenuGroup mg:inserts) {
            mg.setId(null);
            mg.setType("MANAGE");
            this.menuGroupDAO.save(mg);
        }
        List<MenuGroup> updates = JsonHandler.parseList(updatedata,MenuGroup.class);
        for (MenuGroup mg: updates) {
            this.menuGroupDAO.update(mg);
        }
        List<MenuGroup> removes = JsonHandler.parseList(removedata,MenuGroup.class);
        this.menuGroupDAO.deleteAll(removes);
        return true;
    }
}
