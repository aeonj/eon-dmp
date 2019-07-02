package eon.hg.fap.common.tools.json;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class JsonExcludePreFilter extends SimplePropertyPreFilter {

    public JsonExcludePreFilter(){}

    public JsonExcludePreFilter(Class<?> clazz, String... properties){
        super(clazz);
        addExcludes(properties);
    }

    public JsonExcludePreFilter addExcludes(String... filters){
        for (String filter : filters) {
            this.getExcludes().add(filter);
        }
        return this;
    }

    public JsonExcludePreFilter addIncludes(String... filters){
        for (String filter : filters) {
            this.getIncludes().add(filter);
        }
        return this;
    }
}
