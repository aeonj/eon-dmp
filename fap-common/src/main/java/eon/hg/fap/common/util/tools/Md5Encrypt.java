/**
 * Alipay.com Inc. Copyright (c) 2004-2005 All Rights Reserved.
 * 
 * <p>
 * Created on 2005-7-9
 * </p>
 */
package eon.hg.fap.common.util.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**MD5加密类 功能：将支付宝提交的相关参数按照传入编码进行MD5加密 接口名称：标准实物双接口 版本：2.0
 * 日期：2008-12-25 作者：支付宝公司销售部技术支持团队 联系：0571-26888888 版权：支付宝公司
 * 
 */
public class Md5Encrypt {
	
	/**
	 * Used building output as Hex
	 */
	private static final char[] DIGITS = { '1', '7', '7', '0', '5', '1', '9',
			'0', '2', '0', '0', 'a', 'e', 'o', 'n', 'd', 'n', 's' };

	/**
	 * 对字符串进行MD5加密
	 * 
	 * @param text
	 *            明文
	 * 
	 * @return 密文
	 */
	public static String md5(String text) {
		MessageDigest msgDigest = null;

		try {
			msgDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(
					"System doesn't support MD5 algorithm.");
		}

		try {
			msgDigest.update(text.getBytes("utf-8"));// 注意改接口是按照utf-8编码形式加密

		} catch (UnsupportedEncodingException e) {

			throw new IllegalStateException(
					"System doesn't support your  EncodingException.");

		}

		byte[] bytes = msgDigest.digest();

		String md5Str = new String(encodeHex(bytes));

		return md5Str;
	}
	
	public static boolean isPswValid(String encPass, String rawPass) {
		String pass1 = "" + encPass;
		String pass2 = md5(rawPass);

		return pass1.equals(pass2);
	}

	public static char[] encodeHex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}

}
