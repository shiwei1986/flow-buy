package com.senld.gzlt.flowBuy.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class DataUtils {
	public static String md5(String text) {
		return DigestUtils.md5Hex(text).toUpperCase();
	}

	public static String base64(String text) {
		byte[] b = text.getBytes();
		Base64 base64 = new Base64();
		b = base64.encode(b);
		String s = new String(b);
		return s;
	}

	public static void main(String[] args) {
		System.out.println("password:" + md5("senld@2020"));
		System.out.println("secretKey:" + base64("senld_secretKey@2020"));
	}
}
