/**
 * <p>Copyright: Copyright (c) 2011-2012</p>
 * <p>最后更新日期:2016年12月14日
 * @author cxj
 */

package eon.hg.fap.core.mv;

import org.springframework.web.servlet.view.InternalResourceView;

import java.util.Locale;

/**
 * @author aeon
 *
 */
public class JspResourceView extends InternalResourceView {

	@Override
	public boolean checkResource(Locale locale) {
		return getExtension(getUrl()).equals(".jsp");
	}
	
	/**
	 * 获取扩展名
	 * @param fileName
	 * @return
	 */
	public String getExtension(String fileName){
		if(fileName!=null&&fileName.length()>0){
			int i=fileName.lastIndexOf(".");
			if(i>-1&&i<fileName.length())
				return fileName.substring(i);
		}
		//无扩展名时，返回默认
		return ".html";
	}

	
}
