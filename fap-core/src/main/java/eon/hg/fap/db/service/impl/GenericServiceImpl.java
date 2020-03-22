package eon.hg.fap.db.service.impl;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.db.dao.primary.GenericDao;
import eon.hg.fap.db.service.IGenericService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GenericServiceImpl implements IGenericService {
    @Resource
    private GenericDao genericDao;

    @Override
    public List<Dto> findDtoBySql(String sql) {
        return genericDao.findDtoBySql(sql);
    }

    @Override
    public List<Dto> findDtoBySql(String sql,Map params) {
        return genericDao.findDtoBySql(sql,params);
    }

    @Override
    public List<Dto> findDtoBySql(String sql, Map params, int begin, int max) {
        return genericDao.findDtoBySql(sql,params,begin,max);
    }

    @Override
    public List<Dto> findDtoBySql(String sql,Object[] params) {
        return genericDao.findDtoBySql(sql,params);
    }

    @Override
    public List<Dto> findDtoBySql(String sql, Object[] params, int begin, int max) {
        return genericDao.findDtoBySql(sql,params,begin,max);
    }

    @Override
    public int executeBySql(String sql) {
        return genericDao.executeBySql(sql);
    }

    @Override
    public int executeBySql(String sql, Object[] params) {
        return genericDao.executeBySql(sql,params);
    }

}
