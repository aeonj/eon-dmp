/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月1日
 *
 * @author cxj
 */

package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.BaseDataDao;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.service.IBaseDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 不使用泛型，因操作对象是动态调整的
 *
 * @author AEON
 */
@Service
@Transactional
public class BaseDataServiceImpl implements IBaseDataService {
    @Resource
    private BaseDataDao basedataDao;

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#save(com.aeon.foundation.table.mapper.BaseData)
     */
    @Override
    public BaseData save(BaseData basedata) {
        return this.basedataDao.save(basedata);
    }

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#getObjById(java.lang.Long)
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public BaseData getObjById(Class clz, Long id) {
        this.basedataDao.setEntityClass(clz);
        BaseData basedata = this.basedataDao.get(id);
        if (basedata != null) {
            return basedata;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#delete(java.lang.Long)
     */
    @Override
    public void delete(Class clz, Long id) {
        this.basedataDao.setEntityClass(clz);
        this.basedataDao.remove(id);
    }

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#batchDelete(java.util.List)
     */
    @Override
    public void batchDelete(Class clz, List<Long> ids) {
        this.basedataDao.setEntityClass(clz);
        for (Long id : ids) {
            this.basedataDao.remove(id);
        }
    }

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#list(com.aeon.core.query.support.IQueryObject)
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public IPageList list(Class clz, IQueryObject properties) {
        this.basedataDao.setEntityClass(clz);
        return basedataDao.list(properties);
    }

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#update(com.aeon.foundation.table.mapper.BaseData)
     */
    @Override
    public BaseData update(BaseData basedata) {
        basedata.setTreepath(basedata.getParentpath());
        return this.basedataDao.update(basedata);
    }

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#query(java.lang.String, java.util.Map, int, int)
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BaseData> query(String query, Map params, int begin, int max) {
        return this.basedataDao.query(query, params, begin, max);
    }

    /*
     * (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#find(java.lang.Class, com.aeon.core.query.support.IQueryObject)
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BaseData> find(Class clz, IQueryObject properties) {
        if (properties == null) {
            return null;
        }
        String query = properties.getQuery();
        String construct = properties.getConstruct();
        Map params = properties.getParameters();
        this.basedataDao.setEntityClass(clz);
        return this.basedataDao.find(construct, query, params, -1, -1);
    }

    /* (non-Javadoc)
     * @see com.aeon.foundation.service.IBaseDataService#getObjByProperty(java.lang.String, java.lang.String, java.lang.Object)
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public BaseData getObjByProperty(Class clz, String construct, String propertyName,
                                     Object value) {
        this.basedataDao.setEntityClass(clz);
        return this.basedataDao.getBy(construct,
                propertyName, value);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BaseData> findBySql(String sql) {
        return this.basedataDao.findBySql(sql);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<BaseData> findBySql(String sql, Object[] obj) {
        return this.basedataDao.findBySql(sql, obj);
    }

}
