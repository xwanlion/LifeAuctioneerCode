package com.github.xwanlion.lifeauctioneer.util;

import cn.hutool.core.util.NumberUtil;

public class NumericUtil {
	final static char[] digits = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'R',
			'S', 'T', 'U', 'W', 'X', 'Y', 'Z'
	};

	/**
	 * 将十进制的数字转换为指定进制的字符串。
	 * 
	 * @param i
	 *            十进制的数字。
	 * @param system
	 *            指定的进制，常见的2/8/16。
	 * @return 转换后的字符串。
	 */
	public static String numericToString(int i, int system) {
		long num = 0;
		if (i < 0) {
			num = ((long) 2 * 0x7fffffff) + i + 2;
		} else {
			num = i;
		}

		char[] buf = new char[32];
		int charPos = 32;
		while ((num / system) > 0) {
			buf[--charPos] = digits[(int) (num % system)];
			num /= system;
		}

		buf[--charPos] = digits[(int) (num % system)];
		return new String(buf, charPos, (32 - charPos));
	}

	/**
	 * 将其它进制的数字（字符串形式）转换为十进制的数字。
	 * 
	 * @param s
	 *            其它进制的数字（字符串形式）
	 * @param system
	 *            指定的进制，常见的2/8/16。
	 * @return 转换后的数字。
	 */
	public int stringToNumeric(String s, int system) {
		char[] buf = new char[s.length()];
		s.getChars(0, s.length(), buf, 0);
		long num = 0;
		for (int i = 0; i < buf.length; i++) {
			for (int j = 0; j < digits.length; j++) {
				if (digits[j] == buf[i]) {
					num += j * Math.pow(system, buf.length - i - 1);
					break;
				}
			}
		}

		return (int) num;

	}

	public static boolean isNumeric(String s) {
		try {
			double d = Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static double toNumeric(String s) {
		return Double.parseDouble(s);

	}

	public static double[] toNumeric(String[] arr) {
		double[] nArr = new double[arr.length];
		for (int i = 0; i < arr.length; i++) {
			nArr[i] = NumericUtil.toNumeric(arr[i]);
		}

		return nArr;

	}

	public static int[] toInteger(double[] arr) {
		int[] nArr = new int[arr.length];

		for (int i = 0; i < arr.length; i++) {
			nArr[i] = (int) arr[i];
		}

		return nArr;

	}

	public static int toInteger(String str) {
		Integer i = Integer.parseInt(str);
		return i;
	}

	public static long toLong(String str) {
		Long l = Long.parseLong(str);
		return l;
	}

	public static boolean lessThanZero(String str) {
		Double d = NumberUtil.parseDouble(str);
		return (d < 0);
	}

	public static boolean greaterThanZero(String str) {
		Double d = NumberUtil.parseDouble(str);
		return (d > 0);
	}

}
