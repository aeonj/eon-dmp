package eon.hg.fap.core.tools;

import eon.hg.fap.core.annotation.Lock;
import eon.hg.fap.core.beans.propertyeditors.IdEntityEditor;
import eon.hg.fap.core.domain.entity.IdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Transient;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import java.util.Map;

@Slf4j
public class WebHandler {

    /**
     * 将bean对象转换为Map对象
     * @param obj
     * @param map
     */
    public static void Obj2Map(Object obj, Map map) {
        BeanWrapper wrapper = new BeanWrapperImpl(obj);
        PropertyDescriptor[] propertys = wrapper
                .getPropertyDescriptors();
        for (int i = 0; i < propertys.length; i++) {
            String propertyName = propertys[i].getName();
            if (!wrapper.isWritableProperty(propertyName)
                    || propertys[i].getWriteMethod() == null)
                continue;
            Transient tst = null;
            tst = propertys[i].getWriteMethod().getAnnotation(
                    Transient.class);// 获取set方法是否有Lock注解
            if (tst == null) {// 进一步获取字段上是否有注解
                java.lang.reflect.Field f;
                try {
                    f = propertys[i].getWriteMethod()
                            .getDeclaringClass().getDeclaredField(
                                    propertyName);
                    tst = f.getAnnotation(Transient.class);
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (tst == null) {
                map.put(propertyName, wrapper.getPropertyValue(propertyName));
            }
        }
    }

    /**
     * 将表单中的对象载入对应的实体中，该方法可以处理所有基础类型值转换，但不包含使用@Lock标签保护的字段
     *
     * @param map
     *            通过toPO封装的MAP值
     * @param clazz
     *            通过toPO封装的泛类型
     *
     */
    public static <T> T toPo(Map map, Class<T> clazz) {
        return toPo(map, BeanUtils.instantiateClass(clazz));
    }

    /**
     * 将表单中的对象载入对应的实体中，该方法可以处理所有基础类型值转换，但不包含使用@Lock标签保护的字段
     *
     * @param map
     *            通过toPO封装的MAP值
     * @param obj
     *            通过toPO封装的泛类型，可以是存在的obj，也可以是第一次新建的obj
     *
     */
    public static <T> T toPo(Map map, T obj) {
        BeanWrapper wrapper = new BeanWrapperImpl(obj);
        PropertyDescriptor[] propertys = wrapper
                .getPropertyDescriptors();
        for (PropertyDescriptor property : propertys) {
            String name = property.getName();
            if (!wrapper.isWritableProperty(name)
                    || property.getWriteMethod() == null)
                continue;
            Object propertyValue = null;
            Iterator keys = map.keySet().iterator();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (key.equals(property.getName())) {
                    Lock lock = null;
                    lock = property.getWriteMethod().getAnnotation(
                            Lock.class);// 获取set方法是否有Lock注解
                    if (lock == null) {// 进一步获取字段上是否有注解
                        java.lang.reflect.Field f;
                        try {
                            f = property.getWriteMethod()
                                    .getDeclaringClass().getDeclaredField(
                                            name);
                            lock = f.getAnnotation(Lock.class);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                    if (lock == null) {
                        if (IdEntity.class.isAssignableFrom(property
                                .getPropertyType())) {
                            wrapper.registerCustomEditor(property
                                    .getPropertyType(), new IdEntityEditor(
                                    property.getPropertyType()));
                            propertyValue = map.get(key);
                        } else {
                            try {
                                propertyValue = map.get(key);
                            } catch (Exception e) {
                                if (property.getPropertyType().toString()
                                        .equals("int")) {
                                    propertyValue = 0;
                                }
                                if (property.getPropertyType().toString()
                                        .toLowerCase().indexOf("boolean") >= 0) {
                                    propertyValue = false;
                                }
                            }
                        }
                        log.debug("复制值：" + propertyValue +",复制到："+ property.getName());
                        wrapper.setPropertyValue(property.getName(),
                                propertyValue);
                    }
                }
            }
        }
        return obj;
    }
}
