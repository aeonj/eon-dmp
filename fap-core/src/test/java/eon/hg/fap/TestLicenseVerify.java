package eon.hg.fap;

import eon.hg.fap.core.license.VerifyLicense;
import org.junit.Test;

/**
 * Created by aeon on 2018/1/12.
 */
public class TestLicenseVerify {

    @Test
    public void TestLicense(){
        VerifyLicense vLicense = new VerifyLicense();
        //验证证书
        vLicense.install("/Users/aeon/Documents/Develop/license/");
        vLicense.verify();
    }
}
