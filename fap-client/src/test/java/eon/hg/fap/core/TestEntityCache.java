package eon.hg.fap.core;

import eon.hg.fap.db.service.IPermissionsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestEntityCache {
    @Autowired
    private IPermissionsService permissionsService;

    @Test
    public void testGetObjById() {
        permissionsService.getObjById(13596l);
        permissionsService.getObjById(13596l);
    }
}
