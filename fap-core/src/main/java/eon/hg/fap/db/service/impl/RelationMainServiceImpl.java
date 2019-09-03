package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.RelationMainDao;
import eon.hg.fap.db.model.primary.RelationMain;
import eon.hg.fap.db.service.IRelationMainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RelationMainServiceImpl implements IRelationMainService {
    @Resource
    private RelationMainDao relationMainDao;

    /**
     * 根据一个ID得到RelationMain
     *
     * @param id
     * @return
     */
    @Override
    public RelationMain getObjById(Long id) {
        return this.relationMainDao.get(id);
    }

    /**
     * @param fields
     * @return
     */
    @Override
    public RelationMain getObjByProperty(Object... fields) {
        return this.relationMainDao.getOne(fields);
    }

    /**
     * 保存一个RelationMain，如果保存成功返回true，否则返回false
     *
     * @param instance
     * @return 是否保存成功
     */
    @Override
    public RelationMain save(RelationMain instance) {
        return this.relationMainDao.save(instance);
    }

    /**
     * 更新一个RelationMain
     *
     * @param instance 需要更新的RelationMain
     */
    @Override
    public RelationMain update(RelationMain instance) {
        return this.relationMainDao.update(instance);
    }

    /**
     * 删除一个RelationMain
     *
     * @param id
     * @return
     */
    @Override
    public void delete(Long id) {
        this.relationMainDao.remove(id);
    }

    /**
     * 批量删除RelationMain
     *
     * @param ids
     * @return
     */
    @Override
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            this.relationMainDao.remove(id);
        }
    }

    /**
     * 通过一个查询对象得到RelationMain
     *
     * @param qo
     * @return
     */
    @Override
    public IPageList list(IQueryObject qo) {
        return this.relationMainDao.list(qo);
    }

    /**
     * @param qo
     * @return
     */
    @Override
    public List<RelationMain> find(IQueryObject qo) {
        return this.relationMainDao.find(qo);
    }

    /**
     * @param query
     * @param params
     * @param begin
     * @param max
     * @return
     */
    @Override
    public List<RelationMain> query(String query, Map params, int begin, int max) {
        return this.relationMainDao.query(query,params,begin,max);
    }
}
