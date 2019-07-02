package eon.hg.fap.core.cache;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CachePool extends HashMap<String,Map<String,Object>> {

}
