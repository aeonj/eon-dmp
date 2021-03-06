package eon.hg.fap.db.service;

public interface IDataRightService {
    /**
     * 获取权限sql语句
     * @param alias
     * @return
     */
    String getDataRightSql(String alias);

    /**
     * 获取权限hql语句
     * @param alias
     * @return
     */
    String getDataRightHql(String alias);
}
