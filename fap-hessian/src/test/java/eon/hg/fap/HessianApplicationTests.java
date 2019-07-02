package eon.hg.fap;

import com.caucho.hessian.client.HessianProxyFactory;
import gov.gfmis.fap.util.XMLData;
import eon.hg.fap.hessian.ibs.IPreLogin;
import eon.hg.fap.hessian.ibs.ISysBillType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HessianApplicationTests {

	@Autowired
	private ISysBillType sysBillTypeService;

	@Test
	public void testHessian() {
		List list = sysBillTypeService.getCoa();
		System.out.println(list.get(0));
        XMLData xmlData = (XMLData) list.get(0);
        try {
            System.out.println(xmlData.asXML("root"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	@Test
	public void testOldHessian() {
		HessianProxyFactory factory = new HessianProxyFactory();
		String url = "http://localhost:8080/vcf/remote/";
		try {
			IPreLogin preLogin = (IPreLogin) factory.create(IPreLogin.class, url+"preLoginHttpInvoker");
			String userid = preLogin.getUserIdbyCode("999999999");
			System.out.println("用户ID："+userid);
			ISysBillType sysBillType = (ISysBillType) factory.create(ISysBillType.class,url+"sysBillTypeHttpInvoker");
			List list = sysBillType.getCoa();
			XMLData xmlData = (XMLData) list.get(0);
			System.out.println(xmlData.asXML("root"));
			System.out.println(preLogin.queryBusiYear());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
