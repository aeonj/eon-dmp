package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.RelationDetailDao;
import eon.hg.fap.db.model.primary.RelationDetail;
import eon.hg.fap.db.service.IRelationDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RelationDetailServiceImpl implements IRelationDetailService {
    @Resource
    private RelationDetailDao relationDetailDao;

    /**
     * 根据一个ID得到RelationDetail
     *
     * @param id
     * @return
     */
    @Override
    public RelationDetail getObjById(Long id) {
        return this.relationDetailDao.get(id);
    }

    /**
     * @param fields
     * @return
     */
    @Override
    public RelationDetail getObjByProperty(Object... fields) {
        return this.relationDetailDao.getOne(fields);
    }

    /**
     * 保存一个RelationDetail，如果保存成功返回true，否则返回false
     *
     * @param instance
     * @return 是否保存成功
     */
    @Override
    public RelationDetail save(RelationDetail instance) {
        return this.relationDetailDao.save(instance);
    }

    /**
     * 更新一个RelationDetail
     *
     * @param instance 需要更新的RelationDetail
     */
    @Override
    public RelationDetail update(RelationDetail instance) {
        return this.relationDetailDao.update(instance);
    }

    /**
     * 删除一个RelationDetail
     *
     * @param instance
     * @return
     */
    @Override
    public void delete(RelationDetail instance) {
        this.relationDetailDao.remove(instance);
    }

    /**
     * 批量删除RelationDetail
     *
     * @param ids
     * @return
     */
    @Override
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            this.relationDetailDao.remove(id);
        }
    }

    /**
     * 通过一个查询对象得到RelationDetail
     *
     * @param qo
     * @return
     */
    @Override
    public IPageList list(IQueryObject qo) {
        return this.relationDetailDao.list(qo);
    }

    /**
     * @param qo
     * @return
     */
    @Override
    public List<RelationDetail> find(IQueryObject qo) {
        return this.relationDetailDao.find(qo);
    }

    /**
     * @param query
     * @param params
     * @param begin
     * @param max
     * @return
     */
    @Override
    public List<RelationDetail> query(String query, Map params, int begin, int max) {
        return this.relationDetailDao.query(query,params,begin,max);
    }
}
