package eon.hg.fap.web.manage.op;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component
public class TplManageOp {
	/**
	 * 生成问候信息
	 * 
	 * @return
	 */
	public String getWelcomeMsg() {
		Date date = new Date();
//      System.out.println(date);
        Calendar c = new GregorianCalendar();
        c.setTime(date);//给Calendar对象设置时间
        int hour = c.get(Calendar.HOUR_OF_DAY);//取得当前时间的时数
		String welcome = "晚上好";
		if (hour >= 7 && hour <= 12) {
			welcome = "上午好";
		} else if (hour > 12 && hour < 19) {
			welcome = "下午好";
		}
		return welcome;
	}

	/**
	 * 获取和主题对应匹配的颜色值
	 */
	public String getThemeColor(String theme) {
		String color = "slategray";
		if (theme.equalsIgnoreCase("default")) {
			color = "4798D7";
		} else if (theme.equalsIgnoreCase("lightRed")) {
			color = "F094C9";
		} else if (theme.equalsIgnoreCase("lightYellow")) {
			color = "EAAA85";
		} else if (theme.equalsIgnoreCase("gray")) {
			color = "969696";
		} else if (theme.equalsIgnoreCase("lightGreen")) {
			color = "53E94E";
		} else if (theme.equalsIgnoreCase("purple2")) {
			color = "BC5FD8";
		}else if (theme.equalsIgnoreCase("red")) {
			color = "FF3300";
		}
		return color;
	}


}
