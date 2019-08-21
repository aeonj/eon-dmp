package eon.hg.fap.core.tools;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import eon.hg.fap.common.CommUtil;
import eon.hg.fap.common.tools.json.JsonIncludePreFilter;
import eon.hg.fap.core.body.ResultBody;
import eon.hg.fap.common.util.metatype.Dto;
import eon.hg.fap.common.util.metatype.impl.HashDto;
import eon.hg.fap.core.query.support.IPageList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

/**
 * Json处理器
 * 关于SerializeFilter的用法：
 *          包含的字段：new JsonIncludePreFilter(类名，包含的字段列表）
 *          排除的字段：new JsonExcludePreFilter(类名，排除的字段列表）
 * @author aeon
 */
@Slf4j
public class JsonHandler {

	/**
	 * 将不含日期时间格式的Java对象系列化为Json资料格式
	 *
	 * @param pObject
	 *            传入的Java对象
	 * @param includes
	 *            逗号分隔的字串
	 * @return
	 */
    public static final String toJson(Object pObject,String includes) {
		if (CommUtil.isNotEmpty(includes)) {
			SerializeFilter serializeFilter = new JsonIncludePreFilter(StrUtil.split(includes, ","));
			return toJson(pObject,serializeFilter);
		}
        return toJson(pObject);
    }

	/**
	 * 将含有日期时间格式的Java对象系列化为Json资料格式<br>
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有<br>
	 * 日期时间格式的Java对象进行序列化
	 *
	 * @param pObject
	 *            传入的Java对象
	 * @param preFilters  只转换其中的几个字段，逗号分隔
	 * @return
	 */
	public static final String toJson(Object pObject, SerializeFilter... preFilters) {
		String jsonString = "[]";
		if (CommUtil.isEmpty(pObject)) {
			log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			int len = preFilters.length;
			SerializeFilter[] serializeFilters = new SerializeFilter[len+1];
			PropertyFilter profilter = (object, name, value) -> {

				if (value instanceof HibernateProxy) {//hibernate代理对象
					return false;

				} else if (value instanceof PersistentCollection) {//实体关联集合一对多等
					PersistentCollection collection = (PersistentCollection) value;
					if (!collection.wasInitialized()) {
						return false;
					}
					Object val = collection.getValue();
					if (val == null) {
						return false;
					}
				}
				return true;
			};
			serializeFilters[0]=profilter;
			len =0;
			for (SerializeFilter serializeFilter : preFilters) {
				++len;
				serializeFilters[len] = serializeFilter;
			}

			if (pObject instanceof ArrayList) {
				jsonString = JSONArray.toJSONString(pObject, SerializeConfig.globalInstance, serializeFilters,"yyyy-MM-dd HH:mm:ss", JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect);
			} else {
                jsonString = JSONObject.toJSONString(pObject, SerializeConfig.globalInstance, serializeFilters, "yyyy-MM-dd HH:mm:ss", JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect);
			}
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将分页信息压入JSON字符串
	 * 此类内部使用,不对外暴露
	 * @param jsonString JSON字符串
	 * @param totalCount
	 * @return 返回合并后的字符串
	 */
	private static String joinPageJson(String jsonString, Integer totalCount) {
		jsonString = "{\"total\":" + totalCount + ", \"data\":" + jsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("合并后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 *
	 * @param list
	 *            需要编码的List对象
	 */
	public static final String toPageJson(List list) {
		return toPageJson(list,list==null?0:list.size());
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * 
	 * @param list
	 *            需要编码的List对象
	 * @param includes
	 *            仅转换第一层实体类的属性字段集合
	 */
	public static final String toPageJson(List<Object> list, Integer totalCount, String includes) {
		if (CommUtil.isNotEmpty(includes)) {
			if (list!=null && list.size()>0) {
				SerializeFilter serializeFilter = new JsonIncludePreFilter(StrUtil.split(includes,","));
				return toPageJson(list,totalCount,serializeFilter);
			}
		}
		return toPageJson(list,totalCount);
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 *
	 * @param list
	 *            需要编码的List对象
	 * @param preFilter
	 *            需转换的字段，键名为类，键值是逗号分隔字段集合
	 */
	public static final String toPageJson(List<Object> list, SerializeFilter... preFilter) {
		return toPageJson(list,list==null?0:list.size(),preFilter);
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * 
	 * @param list
	 *            需要编码的List对象
	 * @param totalCount
	 *            记录总数
	 * @param preFilter
	 *            需转换的字段，键名为类，键值是逗号分隔字段集合
	 */
	public static final String toPageJson(List<Object> list, Integer totalCount, SerializeFilter... preFilter) {
		String subJsonString = toJson(list, preFilter);
		totalCount = CommUtil.isNotEmpty(totalCount) ? totalCount : list.size();
		String jsonString = joinPageJson(subJsonString,totalCount);
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * @param pList 需要编码的List对象
	 * @param includes 需转换的字段，键名为类，键值是逗号分隔字段集合
	 * @return
	 */
	public static final String toPageJson(IPageList pList, String includes) {
		if (CommUtil.isNotEmpty(includes)) {
			List list = pList.getResult();
			if (list!=null && list.size()>0) {
				Map<String,String> map = new HashMap<String,String>();
				map.put(list.get(0).getClass().getName(),includes);
				SerializeFilter serializeFilter = new JsonIncludePreFilter(StrUtil.split(includes,","));
				return toPageJson(pList,serializeFilter);
			}
		}
		return toPageJson(pList);
	}
	
	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * @param pList 需要编码的List对象
	 * @param serializeFilters 需转换的字段，键名为类，键值是逗号分隔字段集合
	 * @return
	 */
	public static final String toPageJson(IPageList pList, SerializeFilter... serializeFilters) {
		String subJsonString = toJson(pList.getResult(), serializeFilters);
		String jsonString = "{\"page\":" + pList.getCurrentPage() + ", \"total\":" + pList.getRowCount() + ", \"data\":" + subJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将数据系列化为表单数据填充所需的Json格式
	 * @return
	 */
	public static String toExtJson(Object obj) {
		return toExtJson(obj,true);
	}

	public static String toExtJson(Object obj, boolean success, SerializeFilter... serializeFilters) {
		if (obj==null) {
			return "{success:true,data:[]}";
		}
		String sunJsonString = toJson(obj,serializeFilters);
		String jsonString = "{success:"
				+ (success ? "true" : "false") + ",data:"
				+ sunJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	public static String toResultJson(Object obj, SerializeFilter... serializeFilters) {
		if (obj==null) {
			return toJson(ResultBody.success());
		}

		String jsonString = toJson(ResultBody.success().addObject(obj),serializeFilters);
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	public static Dto getPageResultObj(IPageList pList) {
		if (pList==null) {
			return new HashDto(BeanUtil.beanToMap(ResultBody.success()));
		}
		Dto dto = new HashDto();
		dto.put("pages",pList.getPages());
		dto.put("totals",pList.getRowCount());
		dto.put("pageIndex",pList.getCurrentPage());
		dto.putAll(BeanUtil.beanToMap(ResultBody.success().addObject(pList.getResult())));
		return dto;
	}

	public static String toResultJson(IPageList pList, SerializeFilter... serializeFilters) {
		Dto dto = getPageResultObj(pList);
		String jsonString = toJson(dto,serializeFilters);
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将单一Json对象解析为DTOJava对象
	 * 
	 * @param jsonString
	 *            简单的Json对象
	 * @return dto
	 */
	public static Dto parseDto(String jsonString) {
		Dto dto = new HashDto();
		if (CommUtil.isEmpty(jsonString)) {
			return dto;
		}
		dto = JSONObject.parseObject(jsonString, HashDto.class);
		return dto;
	}

	public static <T> T parseObject(String jsonString, Class<T> cls) {
		return JSONObject.parseObject(jsonString, cls);
	}

	/**
	 * 将复杂Json资料格式转换为List对象
	 * 
	 * @param jsonString
	 *            复杂Json对象,格式必须符合如下契约<br>
	 *            {"1":{"name":"托尼.贾","age":"27"},
	 *            "2":{"name":"甄子丹","age":"72"}}
	 * @return List
	 */
	public static List<Dto> parseJson2List(String jsonString) {
		List<Dto> list = new ArrayList<Dto>();
		JSONObject jbJsonObject = JSONObject.parseObject(jsonString);
		Iterator iterator = jbJsonObject.keySet().iterator();
		while (iterator.hasNext()) {
			Dto dto = parseDto(jbJsonObject.getString(iterator.next().toString()));
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 将复杂Json格式转换为List对象
	 * 
	 * @param jsonString
	 *            复杂Json对象,格式必须符合如下契约<br>
	 *           [{'check':'true','id':'01'},{'check':'true','id':'0101'}]
	 * @return List
	 */
	public static List<Dto> parseList(String jsonString) {
		List<Dto> list = new ArrayList<Dto>();
		JSONArray jbJsonObject = JSONArray.parseArray(jsonString);
		for(int i=0;i<jbJsonObject.size();i++){
			Dto dto = parseDto(jbJsonObject.get(i).toString());
			list.add(dto);
		}
		return list;
	}

	public static <T> List<T> parseList(String jsonString, Class<T> cls) {
		return JSONArray.parseArray(jsonString, cls);
	}

}
