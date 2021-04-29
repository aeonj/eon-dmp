package eon.hg.fap.db.service.impl;

import eon.hg.fap.core.query.support.IPageList;
import eon.hg.fap.core.query.support.IQueryObject;
import eon.hg.fap.db.dao.primary.UserBelongDao;
import eon.hg.fap.db.model.primary.UserBelong;
import eon.hg.fap.db.service.IUserBelongService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserBelongServiceImpl implements IUserBelongService {
    @Resource
    private UserBelongDao userBelongDao;

    /**
     * 根据一个ID得到UserBelong
     *
     * @param id
     * @return
     */
    @Override
    public UserBelong getObjById(Long id) {
        return this.userBelongDao.get(id);
    }

    /**
     * @param fields
     * @return
     */
    @Override
    public UserBelong getObjByProperty(Object... fields) {
        return this.userBelongDao.getOne(fields);
    }

    /**
     * 根据用户删除权限信息
     *
     * @param user_id
     * @return
     */
    @Override
    public int deleteByUser(Long user_id) {
        return this.userBelongDao.executeQuery("delete from UserBelong obj where obj.user_id=?1",new Object[]{user_id});
    }

    /**
     * 保存一个UserBelong，如果保存成功返回true，否则返回false
     *
     * @param instance
     * @return 是否保存成功
     */
    @Override
    public UserBelong save(UserBelong instance) {
        return this.userBelongDao.save(instance);
    }

    /**
     * 更新一个UserBelong
     *
     * @param instance 需要更新的UserBelong
     */
    @Override
    public UserBelong update(UserBelong instance) {
        return this.userBelongDao.update(instance);
    }

    /**
     * 删除一个UserBelong
     *
     * @param id
     * @return
     */
    @Override
    public void delete(Long id) {
        this.userBelongDao.remove(id);
    }

    /**
     * 批量删除UserBelong
     *
     * @param ids
     * @return
     */
    @Override
    public void batchDelete(List<Long> ids) {
        for (Long id : ids) {
            this.userBelongDao.remove(id);
        }
    }

    /**
     * 通过一个查询对象得到UserBelong
     *
     * @param qo
     * @return
     */
    @Override
    public IPageList list(IQueryObject qo) {
        return this.userBelongDao.list(qo);
    }

    /**
     * @param qo
     * @return
     */
    @Override
    public List<UserBelong> find(IQueryObject qo) {
        return this.userBelongDao.find(qo);
    }

    /**
     * @param query
     * @param params
     * @param begin
     * @param max
     * @return
     */
    @Override
    public List<UserBelong> query(String query, Map params, int begin, int max) {
        return this.userBelongDao.query(query,params,begin,max);
    }
}
