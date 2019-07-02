package eon.hg.fap.common.util.metatype;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by aeon on 2018/4/17.
 */
public interface Dto extends Map<String, Object> {

    /**
     * 以Integer类型返回属性
     *
     * @param pKey
     * @return Integer 键值
     */
    Integer getInteger(String pKey);

    /**
     * 以BigInteger类型返回属性
     *
     * @param pKey
     * @return BigInteger 键值
     */
    BigInteger getBigInteger(String pKey);

    /**
     * 以Long类型返回属性
     *
     * @param pKey
     * @return Long 键值
     */
    Long getLong(String pKey);

    /**
     * 以String类型返回属性
     *
     * @param pKey
     * @return String 键值
     */
    String getString(String pKey);

    /**
     * 以BigDecimal类型返回属性
     *
     * @param pKey
     * @return BigDecimal 键值
     */
    BigDecimal getBigDecimal(String pKey);

    /**
     * 以Date类型返回属性
     *
     * @param pKey
     * @return Date 键值(yyyy-MM-dd)
     */
    Date getDate(String pKey);

    /**
     * 以Timestamp类型返回属性
     *
     * @param pKey
     * @return Timestamp 键值(yyyy-MM-dd HH:mm:ss)
     */
    Timestamp getTimestamp(String pKey);

    /**
     * 以Boolean类型返回属性
     *
     * @param pKey
     * @return Boolean 键值
     */
    Boolean getBoolean(String pKey);

    /**
     * 以List类型返回属性
     *
     * @param pKey
     * @return List 键值
     */
    List<? extends Object> getList(String pKey);

}
