package eon.hg.fap.common.tools.json;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class JsonIncludePreFilter extends SimplePropertyPreFilter {

    public JsonIncludePreFilter(){}

    public JsonIncludePreFilter(String... properties){
        super(properties);
    }

    public JsonIncludePreFilter(Class<?> clazz, String... properties){
        super(clazz,properties);
    }

    public JsonIncludePreFilter addExcludes(String... filters){
        for (String filter : filters) {
            this.getExcludes().add(filter);
        }
        return this;
    }

    public JsonIncludePreFilter addIncludes(String... filters){
        for (String filter : filters) {
            this.getIncludes().add(filter);
        }
        return this;
    }
}
