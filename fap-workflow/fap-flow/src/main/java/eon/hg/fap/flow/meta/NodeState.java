package eon.hg.fap.flow.meta;

import java.util.HashSet;
import java.util.Set;

public enum NodeState {

    UN_CHECK("001","未完成"),
    CHECK("002","已完成"),
    BACK("003","已退回"),
    FROM_BACK("004","被退回"),
    DISCARD("103","已作废"),
    HISTORY("008","历史记录"),
    ALL("999","全部");

    private String code;
    private String msg;

    NodeState(String code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static NodeState getValue(String code) {
        for (NodeState ns : values()) {
            if (ns.getCode().equals(code)) {
                return ns;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
