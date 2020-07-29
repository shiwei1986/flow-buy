package com.senld.gzlt.flowBuy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderUtils {
	public static String getOrderNo(String prefix) {
		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		String rannum = getCard(4);// 获取5位随机数
		return prefix + str + rannum;// 当前时间 + 系统5随机生成位数
	}

	public static String getCard(int size) {
		Random rand = new Random();// 生成随机数
		String cardNnumer = "";
		for (int a = 0; a < size; a++) {
			cardNnumer += rand.nextInt(10);// 生成6位数字
		}
		return cardNnumer;

	}

	public static void main(String[] args) {
		// for (int i = 0; i < 20; i++) {
		// System.out.println(getOrderNo("L"));
		// }
		// System.out.println("L201911282031013253".substring(1, 15));
		Double aDouble = 39.00;
		int i = (int) (aDouble / 5);
		System.out.println(i);
		System.out.println(getOrderNo("O"));
	}
}
