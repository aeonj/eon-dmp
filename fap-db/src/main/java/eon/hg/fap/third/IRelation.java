package eon.hg.fap.third;

import eon.hg.fap.db.model.primary.RelationMain;

import java.util.List;

/**
 * 提供要素关联列表的第三方扩展
 * @author aeon
 */
public interface IRelation {
    List<RelationMain> getRelationList();


    String getRelationSql(String priSource, String secSource, String priValue);
}
