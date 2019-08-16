package eon.hg.fap;

import eon.hg.fap.core.query.QueryObject;
import eon.hg.fap.db.model.mapper.BaseData;
import eon.hg.fap.db.model.primary.EleDepartment;
import eon.hg.fap.db.service.IBaseDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseDaoTests {

    @Resource
    IBaseDataService baseDataService;

    @Test
    public void testEleDepartment() {
        QueryObject qo = new QueryObject();
        List<BaseData> bds = baseDataService.find(EleDepartment.class,qo);
        for (BaseData bd:bds) {
            System.out.println(bd.getParent());
        }
    }
}
