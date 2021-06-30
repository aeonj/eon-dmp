/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年3月1日
 *
 * @author cxj
 */

package eon.hg.fap.db.service.impl;

import cn.hutool.crypto.SecureUtil;
import eon.hg.fap.common.tools.json.JsonExcludePreFilter;
import eon.hg.fap.core.cache.RedisPool;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.model.mapper.Ccid;
import eon.hg.fap.db.service.ICcidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = Globals.DEFAULT_TABLE_SUFFIX+"ccid")
public class CcidServiceImpl implements ICcidService {
    @Resource
    private GenericDao geDao;
    @Autowired(required = false)
    private RedisPool redisPool;

    @CachePut(key = "#ccid.hash_code")
    @Override
    public Ccid save(Ccid ccid) {
        return geDao.save(ccid);
    }

    @CachePut(key = "#ccid.hash_code")
    @Override
    public Ccid update(Ccid ccid) {
        return this.geDao.update(ccid);
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Ccid getObjById(Class<Ccid> clz, Long id) {
        if (id==null) return null;
        return this.geDao.get(clz,id);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void delete(Class clz, Long id) {
        Ccid bd = this.getObjById(clz,id);
        this.geDao.remove(bd);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void batchDelete(Class clz, List<Long> ids) {
        for (Long id : ids) {
            this.delete(clz,id);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Cacheable(key = "#hash_value")
    @Override
    public Ccid getObjByHash(Class<Ccid> clz, String hash_value) {
        return this.geDao.getBy(clz,"hash_code",hash_value);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Ccid getObjByProperty(Class<Ccid> clz, Object... fields) {
        return this.geDao.getBy(clz,fields);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<IdEntity> query(String query, Map params, int begin, int max) {
        return this.geDao.query(query,params,begin,max);
    }

    @Override
    public Ccid getCcid(Ccid record) {
        String jsonStr = JsonHandler.toJson(record,new JsonExcludePreFilter(null,"id","addTime","lastTime","lastUser","hash_code","is_deleted"));
        record.setHash_code(SecureUtil.md5(jsonStr));
        Ccid vf = this.getObjByHash((Class<Ccid>) record.getClass(),record.getHash_code());
        if (vf==null) {
            record.setId(null);
            return this.save(record);
        } else {
            return vf;
        }
    }

}
