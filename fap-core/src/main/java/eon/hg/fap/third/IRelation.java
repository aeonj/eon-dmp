package eon.hg.fap.third;

import eon.hg.fap.db.model.primary.RelationMain;

import java.util.List;

public interface IRelation {
    List<RelationMain> getRelationList();


    String getRelationSql(String priSource, String secSource, String priValue);
}
