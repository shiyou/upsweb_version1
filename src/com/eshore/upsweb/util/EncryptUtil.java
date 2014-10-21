package com.eshore.upsweb.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	/** 加密码算法的名称 */
	public static final String MD5 = "MD5";

	public static final String SHA = "SHA";

	public static final String SHA_1 = "SHA-1";

	public static final String SHA_256 = "SHA-256";

	/** 默认的加密算法(md5) */
	public static final String DEF = MD5;

	/**
	 * 不允许构造实例
	 * 
	 */
	private EncryptUtil() {
	}

	/**
	 * 得到加密后的密码
	 * 
	 * @param strSrc
	 *            加密前的密码
	 * @param encName
	 *            加密算法的名称
	 * @return
	 */
	public static String getStrDes(String strSrc, String encName) {
		if (null == strSrc) {
			return null;
		}
		// 得到MessageDigest对象
		MessageDigest md = null;
		// 加密后的字符串
		String strDes = null;
		// 要加密的字符串字节型数组
		byte[] bt = strSrc.getBytes();
		try {
			if (encName == null || encName.equals("")) {
				// 如果加密算法的名称为空，则默认为md5加密算法
				encName = MD5;
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			// 通过执行诸如填充之类的最终操作完成哈希计算
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			System.out.println("不正确的加密算法名称.\n" + e.getMessage());
			return null;
		}
		return strDes;
	}

	/**
	 * MD5算法
	 * 
	 * @param strSrc
	 *            加密前的密码
	 * @return 加密后的密码
	 */
	public static String getMD5(String strSrc) {
		return getStrDes(strSrc, EncryptUtil.MD5);
	}

	/**
	 * SHA算法
	 * 
	 * @param strSrc
	 *            加密前的密码
	 * @return 加密后的密码
	 */
	public static String getSHA(String strSrc) {
		return getStrDes(strSrc, SHA);
	}

	/**
	 * SHA-1算法
	 * 
	 * @param strSrc
	 *            加密前的密码
	 * @return 加密后的密码
	 */
	public static String getSHA_1(String strSrc) {
		return getStrDes(strSrc, SHA_1);
	}

	/**
	 * SHA-256算法
	 * 
	 * @param strSrc
	 *            加密前的密码
	 * @return 加密后的密码
	 */
	public static String getSHA_256(String strSrc) {
		return getStrDes(strSrc, SHA_256);
	}

	/**
	 * 默认的加密算法
	 * 
	 * @param strSrc
	 *            加密前的密码
	 * @return 加密后的密码
	 */
	public static String getDEF(String strSrc) {
		return getStrDes(strSrc, DEF);
	}
	

	/**
	 * 将字节数组转换成16进制的字符串
	 */
	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		String strSrc = "testsp";
		System.out.println("要加密的密码:" + strSrc);
		System.out.println("加密之后的密码:");
		System.out.println("Use Def:" + getDEF(strSrc));
		System.out.println("Use MD5:" + getMD5(strSrc));
		System.out.println("Use SHA:" + getSHA(strSrc));
		System.out.println("Use SHA-1:"	+ getSHA_1(strSrc));
		System.out.println("Use SHA-256:" + getSHA_256(strSrc));
		System.out.println(EncryptUtil.getMD5("superuser"));
		System.out.println(EncryptUtil.getMD5("gdupstyzf@2011"));
	}
}