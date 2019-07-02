package eon.hg.fap;

import cn.hutool.json.JSONUtil;
import eon.hg.fap.core.constant.Globals;
import eon.hg.fap.core.jpa.EonDao;
import eon.hg.fap.db.dao.primary.AppDao;
import eon.hg.fap.db.model.primary.App;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppDaoTests {
    @Resource
    private AppDao appDAO;
    @Resource
    private EonDao geDao;

    @Test
    public void testFindOne() {
        System.out.println(appDAO.findById(Long.valueOf(6)).get().getName());
        App app= appDAO.get(Long.valueOf(6));
        System.out.println(app.getName());
        System.out.println(appDAO.getBy("id",Long.valueOf(6)).getName());
    }

    @Test
    public void testSave() {
        App app = new App();
        app.setName("cxj");
        appDAO.save(app);
    }

    @Test
    public void testNativeSql() {
        //appDAO.executeBySql("insert into "+Globals.SYS_TABLE_SUFFIX+"app(id,is_deleted,name,sequence) values(19,0,'hqj19',5)");
        //App app= appDAO.get(Long.valueOf(7));
        //System.out.println(app.getName());
        List list = geDao.findBySql("select * from "+ Globals.SYS_TABLE_SUFFIX+"app");
        System.out.println(JSONUtil.toJsonStr(list));
        Map map = new HashMap<String,Object>();
        map.put("id",Long.valueOf(9));
        List<App> listapp = appDAO.query("select app from App app where app.id=:id ", map,0,0);
        System.out.println(listapp.get(0).getName());
        listapp = appDAO.find("new App(id,name)","obj.id=:id",map,0,0);
        System.out.println(listapp.get(0).getName());
    }
}
