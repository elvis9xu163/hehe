package com.xjd.hehe.utl;

import org.junit.Test;

/**
 * @author elvis.xu
 * @since 2015-10-24 19:54
 */
public class DigestUtilTest {

	@Test
	public void testHexString() throws Exception {
		String s = DigestUtil.hexString(new byte[]{(byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte)-32, 24});
		System.out.println(s);
	}

	@Test
	public void testDigest() {
		String text = "hehepwd";
		System.out.println(DigestUtil.digest(text, DigestUtil.MD5));
		System.out.println(DigestUtil.digest(text, DigestUtil.SHA1));
		System.out.println(DigestUtil.digest(text, DigestUtil.SHA256));
	}
}