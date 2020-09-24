import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Map;

public class TestJson {

    @Test
    public void testJson() {
        String jsonStr = "[{'check':'true','id':'01'},{'check':'true','id':'0101'}]";
        //JSONArray.parseArray(jsonStr,)
        String json = "{'flow':{'flowKey':'cxj','nodeName':'单位审核'}}";
        Map dto = JSONObject.parseObject(json, Map.class);
        Object obj = dto.get("flow");
    }
}
