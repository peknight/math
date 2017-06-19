package com.peknight.math.factorial;

import java.math.BigInteger;

/**
 * 阶乘算法类
 * 
 * @author Peknight
 *
 */
public class Factorial {
	private Factorial() {}
	
	/** 阶乘 */
	public static long factorial(int num) {
		long result = 1;
		for (int i = 2; i <= num; i++) {
			result *= i;
		}
		return result;
	}
	/** 阶乘：num*(num-1)*(num-2)*...*min */
	public static long factorial(int num, int min) {
		long result = 1;
		for (int i = min; i <= num; i++) {
			result *= i;
		}
		return result;
	}
	/** 阶乘（数据较大时使用） */
	public static BigInteger bigFactorial(int num) {
		BigInteger result = new BigInteger("1");
		for (int i = 2; i <= num; i++) {
			result = result.multiply(new BigInteger(String.valueOf(i)));
		}
		return result;
	}
	/** 阶乘（数据较大时使用）：num*(num-1)*(num-2)*...*min */
	public static BigInteger bigFactorial(int num, int min) {
		BigInteger result = new BigInteger("1");
		for (int i = min; i <= num; i++) {
			result = result.multiply(new BigInteger(String.valueOf(i)));
		}
		return result;
	}
}
