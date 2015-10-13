package com.xjd.hehe.spider.haha;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class MD5Utils {
	/**
	 * <pre>
	 * 对给定的字符串做MD5散列
	 * </pre>
	 * 
	 * add 添加字符支持，UTF-8 2014/10/09 15:18 YANG.
	 * @param text
	 * @return
	 */
	public static String md5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			try {
				md.update(text.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			byte[] digest = md.digest();

			StringBuilder buf = new StringBuilder(32);
			int t;
			for (int i = 0; i < digest.length; i++) {
				t = digest[i];
				if (t < 0) {
					t += 256;
				}
				if (t < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(t));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String md5(File file) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			FileInputStream in = new FileInputStream(file);
			byte[] bytes = new byte[1024 * 2];
			int c = 0;
			while ((c = in.read(bytes)) != -1) {
				if (c > 0) {
					md.update(bytes, 0, c);
				}
			}
			in.close();
			byte[] digest = md.digest();

			StringBuilder buf = new StringBuilder(32);
			int t;
			for (int i = 0; i < digest.length; i++) {
				t = digest[i];
				if (t < 0) {
					t += 256;
				}
				if (t < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(t));
			}
			return buf.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
