package eon.hg.fap.core.beans.propertyeditors;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import eon.hg.fap.core.beans.SpringUtils;
import eon.hg.fap.core.domain.entity.IdEntity;
import eon.hg.fap.core.jpa.EonDao;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.Assert;

import java.beans.PropertyEditorSupport;

public class IdEntityEditor extends PropertyEditorSupport {

	private EonDao eonDao = SpringUtils.getBean("eonDao",EonDao.class);;
	
	private Class idClass;
	
	public IdEntityEditor() {
		
	}

	public IdEntityEditor(Class idClass) throws IllegalArgumentException {
		if (idClass == null || !IdEntity.class.isAssignableFrom(idClass)) {
			throw new IllegalArgumentException(
					"Property class must be a subclass of IdEntity");
		}
		this.idClass = idClass;
	}
	
	/**
	 * @return the idClass
	 */
	public Class getIdClass() {
		return idClass;
	}

	/**
	 * @param idClass the idClass to set
	 */
	public void setIdClass(Class idClass) {
		this.idClass = idClass;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (StrUtil.isBlank(text)) {
			// Treat empty String as null value.
			setValue(null);
		}
		else {
			setValue(parseIdEntity(text, this.idClass));
		}
	}

	public String getAsText() {
		Object obj = getValue();
		if (obj == null) {
			return "";
		} else {
			BeanWrapper wrapper = new BeanWrapperImpl(obj);
			Object value = wrapper.getPropertyValue("id");
			return value.toString();
		}
	}

	public Object parseIdEntity(String text, Class targetClass) {
		Assert.notNull(text, "Text must not be null");
		Assert.notNull(targetClass, "Target class must not be null");
		
		Long propertyValue = Convert.toLong(text.trim());
		Object obj = this.eonDao.get(targetClass, propertyValue);
		if (obj!=null) {
			return obj;
		} else {
			return null;
		}
	}
	
	public Object parsePropertys(String text, Class targetClass) {
		Assert.notNull(text, "Text must not be null");
		Assert.notNull(targetClass, "Target class must not be null");

		String trimmed = text.trim();

		try {
			Object obj = targetClass.newInstance();
			BeanWrapper wrapper = new BeanWrapperImpl(obj);
			java.beans.PropertyDescriptor[] propertys = wrapper
					.getPropertyDescriptors();
			for (int i = 0; i < propertys.length; i++) {
				if ("id".equals(propertys[i].getName())) {
					Object propertyValue = null;
					try {
						propertyValue = Convert.convert(propertys[i].getPropertyType(),trimmed);
					} catch (Exception e) {
						if (propertys[i].getPropertyType().toString()
								.equals("int")) {
							propertyValue = 0;
						}
						if (propertys[i].getPropertyType().toString()
								.toLowerCase().indexOf("boolean") >= 0) {
							propertyValue = false;
						}
					}
					wrapper.setPropertyValue(propertys[i].getName(),
							propertyValue);
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
