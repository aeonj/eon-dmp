package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.PartGroupDao;
import eon.hg.fap.db.model.primary.PartGroup;
import eon.hg.fap.db.service.IPartGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PartGroupServiceImpl implements IPartGroupService {
    @Resource
    private PartGroupDao partGroupDao;

    /**
     * 根据一个ID得到PartGroup
     *
     * @param id
     * @return
     */
    @Override
    public PartGroup getObjById(Long id) {
        return this.partGroupDao.get(id);
    }

    /**
     * @param fields
     * @return
     */
    @Override
    public PartGroup getObjByProperty(Object... fields) {
        return this.partGroupDao.getOne(fields);
    }

    /**
     * 保存一个PartGroup，如果保存成功返回true，否则返回false
     *
     * @param instance
     * @return 是否保存成功
     */
    @Override
    public PartGroup save(PartGroup instance) {
        return this.partGroupDao.save(instance);
    }

    /**
     * 更新一个PartGroup
     *
     * @param instance 需要更新的PartGroup
     */
    @Override
    public PartGroup update(PartGroup instance) {
        return this.partGroupDao.update(instance);
    }

    /**
     * 删除一个PartGroup
     *
     * @param id
     * @return
     */
    @Override
    public void delete(Long id) {
        this.partGroupDao.remove(id);
    }

    /**
     * 批量删除PartGroup
     *
     * @param ids
     * @return
     */
    @Override
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            this.partGroupDao.remove(id);
        }
    }

    /**
     * 通过一个查询对象得到PartGroup
     *
     * @param qo
     * @return
     */
    @Override
    public IPageList list(IQueryObject qo) {
        return this.partGroupDao.list(qo);
    }

    /**
     * @param qo
     * @return
     */
    @Override
    public List<PartGroup> find(IQueryObject qo) {
        return this.partGroupDao.find(qo);
    }

    /**
     * @param query
     * @param params
     * @param begin
     * @param max
     * @return
     */
    @Override
    public List<PartGroup> query(String query, Map params, int begin, int max) {
        return this.partGroupDao.query(query,params,begin,max);
    }
}
