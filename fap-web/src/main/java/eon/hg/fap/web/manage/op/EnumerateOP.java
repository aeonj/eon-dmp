/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年12月16日
 * @author cxj
 */

package eon.hg.fap.web.manage.op;

import eon.hg.fap.db.model.primary.Enumerate;
import eon.hg.fap.db.service.IEnumerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author aeon
 *
 */
@Component
public class EnumerateOP {
	@Autowired
	private IEnumerateService enumService;
	
	public List<Enumerate> getCodeList(String field) {
		return enumService.getCodeList(field);
	}
}
