package eon.hg.fap.db.service.impl;

import cn.hutool.core.convert.Convert;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.core.exception.Assert;
import eon.hg.fap.core.security.SecurityUserHolder;
import eon.hg.fap.core.tools.JsonHandler;
import eon.hg.fap.db.dao.primary.PartGroupDao;
import eon.hg.fap.db.dao.primary.UserDao;
import eon.hg.fap.db.model.primary.PartGroup;
import eon.hg.fap.db.model.primary.User;
import eon.hg.fap.db.model.virtual.OnlineUser;
import eon.hg.fap.db.service.IDataRightService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataRightServiceImpl implements IDataRightService {
    @Resource
    private UserDao userDao;
    @Resource
    private PartGroupDao partDao;

    /**
     * 获取权限sql语句
     * @param alias
     * @return
     */
    @Override
    public String getDataRightSql(String alias) {
        OnlineUser oUser = SecurityUserHolder.getOnlineUser();
        User user = userDao.get(Convert.toLong(oUser.getUserid()));
        Assert.notNull(user,"当前登录用户获取异常，请重新登录");
        StringBuilder sqlBuilder = new StringBuilder();
        if (CommUtil.isNotEmpty(user.getBelong_source())) {
            //用户自定义权限
            List sourceList = JsonHandler.parseList(user.getBelong_source());
            for (int i = 0; i < sourceList.size(); i++) {
                Dto dto = (Dto) sourceList.get(i);
                String ele_code = dto.getString("eleCode");
                sqlBuilder.append(" and exists(select 1 from sys_userbelong ub where ub.ele_code='").append(ele_code).append("'")
                        .append(" and ub.ele_value=").append(alias).append(".").append(ele_code).append("_id")
                        .append(" and ub.user_id=").append(oUser.getUserid()).append(")");
            }
        } else if (CommUtil.isNotEmpty(user.getPg_id())) {
            //权限组
            PartGroup pg = partDao.get(user.getPg_id());
            List sourceList = JsonHandler.parseList(pg.getBelong_source());
            for (int i = 0; i < sourceList.size(); i++) {
                Dto dto = (Dto) sourceList.get(i);
                String ele_code = dto.getString("eleCode");
                sqlBuilder.append(" and exists(select 1 from sys_part_detail ub where ub.eleCode='").append(ele_code).append("'")
                        .append(" and ub.value=").append(alias).append(".").append(ele_code).append("_id")
                        .append(" and ub.pg_id=").append(user.getPg_id()).append(")");
            }

        }

        return sqlBuilder.toString();
    }

    /**
     * 获取权限hql语句
     *
     * @param alias
     * @return
     */
    @Override
    public String getDataRightHql(String alias) {
        OnlineUser oUser = SecurityUserHolder.getOnlineUser();
        User user = userDao.get(Convert.toLong(oUser.getUserid()));
        Assert.notNull(user,"当前登录用户获取异常，请重新登录");
        StringBuilder sqlBuilder = new StringBuilder();
        if (CommUtil.isNotEmpty(user.getBelong_source())) {
            //用户自定义权限
            List sourceList = JsonHandler.parseList(user.getBelong_source());
            for (int i = 0; i < sourceList.size(); i++) {
                Dto dto = (Dto) sourceList.get(i);
                String ele_code = dto.getString("eleCode");
                sqlBuilder.append(" and exists(select 1 from UserBelong ub where ub.ele_code='").append(ele_code).append("'")
                        .append(" and ub.ele_value=").append(alias).append(".").append(ele_code).append("_id")
                        .append(" and ub.user_id=").append(oUser.getUserid()).append(")");
            }
        } else if (CommUtil.isNotEmpty(user.getPg_id())) {
            //权限组
            PartGroup pg = partDao.get(user.getPg_id());
            List sourceList = JsonHandler.parseList(pg.getBelong_source());
            for (int i = 0; i < sourceList.size(); i++) {
                Dto dto = (Dto) sourceList.get(i);
                String ele_code = dto.getString("eleCode");
                sqlBuilder.append(" and exists(select 1 from PartDetail ub where ub.eleCode='").append(ele_code).append("'")
                        .append(" and ub.value=").append(alias).append(".").append(ele_code).append("_id")
                        .append(" and ub.pg.id=").append(user.getPg_id()).append(")");
            }

        }

        return sqlBuilder.toString();
    }
}
