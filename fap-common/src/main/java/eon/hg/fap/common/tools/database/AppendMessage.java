package eon.hg.fap.common.tools.database;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 数据库工具类,用来拼接mysql语句信息，加入公司信息
 * @author apple
 * 
 */
public class AppendMessage {

	/**
	 * 拼接头部信息
	 * 
	 * @throws Exception
	 */
	public static String headerMessage() throws Exception {
		StringBuilder strBuilder = null;
		try {
			SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strBuilder = new StringBuilder();
			strBuilder.append("/*" + "\n").append("Data Transfer" + "\n")
					.append("author: aeon" + "\n").append(
							"date: " + smf.format(new Date()) + "\n").append(
							"*/" + "\n");
		} catch (Exception e) {
			throw e;
		}
		return strBuilder.toString();
	}

	/**
	 * 拼接表之前的信息
	 * 
	 * @throws Exception
	 */
	public static String tableHeaderMessage(String tableName) throws Exception {
		StringBuilder strBuilder = null;
		try {
			strBuilder = new StringBuilder();
			strBuilder.append("-- ----------------------------" + "\n").append(
					"-- Create Table " + tableName + "\n").append(
					"-- ----------------------------");
		} catch (Exception e) {
			throw e;
		}
		return strBuilder.toString();
	}

	/**
	 * 拼接表之前的信息
	 * 
	 * @throws Exception
	 */
	public static String insertHeaderMessage() throws Exception {
		StringBuilder strBuilder = null;
		try {
			strBuilder = new StringBuilder();
			strBuilder.append("-- ----------------------------" + "\n").append(
					"-- Insert Datas  " + "\n").append(
					"-- ----------------------------");
		} catch (Exception e) {
			throw e;
		}
		return strBuilder.toString();
	}
}
