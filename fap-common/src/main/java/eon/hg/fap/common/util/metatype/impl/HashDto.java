package eon.hg.fap.common.util.metatype.impl;

import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.cast.TypeCastHelper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

/**
 * 继承于HashMap，所有的key不敏感
 * Created by aeon on 2018/4/17.
 */
public class HashDto extends HashMap<String,Object> implements Dto {

    public HashDto() {
        super();
    }

    public HashDto(Map<? extends String, ? extends Object> m) {
        Iterator<String> its = (Iterator<String>) m.keySet().iterator();
        while (its.hasNext()) {
            String key =its.next();
            put(key.toLowerCase(),m.get(key));
        }
    }

    @Override
    public Integer getInteger(String pKey) {
        Object obj = TypeCastHelper.convert(get(pKey), "Integer", null);
        if (obj != null)
            return (Integer) obj;
        else
            return null;
    }

    @Override
    public BigInteger getBigInteger(String pKey) {
        BigInteger outValue = null;
        Object obj = get(pKey);
        if (obj instanceof BigInteger) {
            outValue = (BigInteger) obj;
        } else {
            outValue = new BigInteger(getString(pKey));
        }
        return outValue;
    }

    @Override
    public Long getLong(String pKey) {
        Object obj = TypeCastHelper.convert(get(pKey), "Long", null);
        if (obj != null)
            return (Long) obj;
        else
            return null;
    }

    @Override
    public String getString(String pKey) {
        Object obj = TypeCastHelper.convert(get(pKey), "String", null);
        if (obj != null)
            return (String) obj;
        else
            return "";
    }

    @Override
    public BigDecimal getBigDecimal(String pKey) {
        Object obj = TypeCastHelper.convert(get(pKey), "BigDecimal", null);
        if (obj != null)
            return (BigDecimal) obj;
        else
            return null;
    }

    @Override
    public Date getDate(String pKey) {
        Object obj = TypeCastHelper.convert(get(pKey), "Date", "yyyy-MM-dd");
        if (obj != null)
            return (Date) obj;
        else
            return null;
    }

    @Override
    public Timestamp getTimestamp(String pKey) {
        Object obj = TypeCastHelper.convert(get(pKey), "Timestamp", "yyyy-MM-dd HH:mm:ss");
        if (obj != null)
            return (Timestamp) obj;
        else
            return null;
    }

    @Override
    public Boolean getBoolean(String pKey) {
        Object obj = TypeCastHelper.convert(get(pKey), "Boolean", null);
        if (obj != null)
            return (Boolean) obj;
        else
            return null;
    }

    @Override
    public List<? extends Object> getList(String pKey) {
        return (List<? extends Object>) get(pKey);
    }

}
