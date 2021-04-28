package eon.hg.fap.flow.meta;

public class SqlRelation {
    //逗号开始的select列
    private String columns = "";
    //表连接
    private String joins = "";
    //sql条件
    private String conditions = "";

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public String getJoins() {
        return joins;
    }

    public void setJoins(String joins) {
        this.joins = joins;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

}
