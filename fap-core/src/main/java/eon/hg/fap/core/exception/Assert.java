package eon.hg.fap.core.exception;

import eon.hg.fap.common.CommUtil;

import java.util.List;

public abstract class Assert {
    public static void notEmpty(long[] ids, String exceptionName){
        if(ids == null || ids.length < 1)
            throw new ResultException(exceptionName);
    }

    public static void notEmpty(List list, String exceptionName){
        if(list == null || list.isEmpty())
            throw new ResultException(exceptionName);
    }

    public static void notNull(Object data, String exceptionName){
        if(data == null)
            throw new ResultException(exceptionName);
    }

    public static void notEmpty(String string, String exceptionMessage){
        if (CommUtil.isEmpty(string)){
            throw new ResultException(exceptionMessage);
        }
    }

    public static void isInstandOf(Class<?> type, Object obj, String exceptionMessage) {
        if (false == type.isInstance(obj)){
            throw new ResultException(exceptionMessage);
        }
    }
}
