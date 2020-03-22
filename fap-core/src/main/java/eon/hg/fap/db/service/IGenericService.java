package eon.hg.fap.db.service;

import eon.hg.fap.common.util.metatype.Dto;

import java.util.List;
import java.util.Map;

public interface IGenericService {
    List<Dto> findDtoBySql(String sql);

    List<Dto> findDtoBySql(String sql, Map params);

    List<Dto> findDtoBySql(String sql, Map params, int begin, int max);

    List<Dto> findDtoBySql(String sql, Object[] params);

    List<Dto> findDtoBySql(String sql, Object[] params, int begin, int max);

    int executeBySql(String sql);

    int executeBySql(String sql, Object[] params);
}
