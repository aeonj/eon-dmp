package eon.hg.fap.core.query;

public class QueryBean {

    private boolean bracketFront = false;
    private String relation;
    private String key;
    private String operation;
    private String value;
    private boolean bracketBack = false;

    private boolean onlyBracket;

    public boolean isBracketFront() {
        return bracketFront;
    }

    public void setBracketFront(boolean bracketFront) {
        this.bracketFront = bracketFront;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isBracketBack() {
        return bracketBack;
    }

    public void setBracketBack(boolean bracketBack) {
        this.bracketBack = bracketBack;
    }

    public boolean isOnlyBracket() {
        return onlyBracket;
    }

    public void setOnlyBracket(boolean onlyBracket) {
        this.onlyBracket = onlyBracket;
    }
}
