/**
 * MIT License
 *
 * Copyright (c) 2017-2027 PeKnight(JKpeknight@gmail.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
