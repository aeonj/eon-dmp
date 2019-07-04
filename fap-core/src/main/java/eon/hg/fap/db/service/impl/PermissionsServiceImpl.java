package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.db.dao.primary.PermissionsDao;
import eon.hg.fap.db.model.primary.Permissions;
import eon.hg.fap.db.service.IPermissionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionsServiceImpl implements IPermissionsService {

    @Resource
    PermissionsDao permissionsDAO;

    @Override
    public Permissions save(Permissions instance) {
        return this.permissionsDAO.save(instance);
    }

    @Override
    public Permissions getObjById(Long id) {
        return this.permissionsDAO.get(id);
    }

    @Override
    public void delete(Long id) {
        this.permissionsDAO.remove(id);
    }

    @Override
    public Permissions update(Permissions instance) {
        return this.permissionsDAO.update(instance);
    }

    @Override
    public List<Permissions> query(String query, Map params, int begin, int max) {
        return this.permissionsDAO.query(query,params,begin,max);
    }

    @Override
    public Permissions getObjByProperty(String construct, String propertyName, Object value) {
        return this.permissionsDAO.getBy(construct,propertyName,value);
    }

    @Override
    public IPageList list(QueryObject qo) {
        return this.permissionsDAO.list(qo);
    }
}
