package com.peknight.math.collection;

import com.peknight.common.collection.ArrayUtils;
import com.peknight.math.factorial.Factorial;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 组合算法类
 * 本类提供了计算组合数以及对数据进行组合的多种方法
 * 组合算法提供了多个重载以便对不同类型的数据进行组合
 * 除了递归算法外，其他算法核心思想是相同的
 * 在总体数据量不大的情况下
 * 如需获取组合所有情况，推荐使用本类提供的递归算法
 * 在总体数据量比较大的情况下，不建议获取组合所有情况
 * 在总体数据量超级大的情况下，请使用bigCombination方法获取单条情况
 * 
 * @author Peknight
 *
 * 原创
 */
public class Combination {
	private Combination() {}
	/** 计算组合数 */
	public static long countCombination(int n, int m) {
		return Factorial.factorial(n, n-m+1)/ Factorial.factorial(m);
	}
	
	/** 计算组合数(数据较大时使用) */
	public static BigInteger countBigCombination(int n, int m) {
		return Factorial.bigFactorial(n, n-m+1).divide(Factorial.bigFactorial(m));
	}
	
	/**
	 * 将从给定数组data中取出combinationArr数组长度的数据进行组合而得到的第row行结果存入combinationArr中
	 * 此方法为int类型数组设计
	 * @param combinationArr 存放生成数据的数组
	 * @param data 给定的源数组
	 * @param row 要获得的组合结果的行号（从0开始）
	 */
	public static void combination(int[] combinationArr, int[] data, long row) {
		int dataSize = data.length;
		//dataIndex 当前获取数据的下标
		int dataIndex = 0;
		int len = combinationArr.length;
		for (int col = 0; col < len; col++) {
			//dataRightLen 源数组中还未组合的数据长度
			int dataRightLen = dataSize-dataIndex;
			//rightLen 还未组合的数据长度
			int rightLen = len-col;
			/*
			 * 本方法的核心思想在于根据行号获取要取出的数据下标
			 * 每列可能出现的数字共有dataRightLen-rightLen+1个
			 */
			for (int i = 0; i < dataRightLen-rightLen+1; i++) {
				long count = countCombination(dataRightLen-i-1, rightLen-1);
				if (row < count) {
					/*
					 * 如果row小于count说明此行号在data[dataIndex]的赋值区域内
					 * dataIndex对应数据使用后自增
					 */
					combinationArr[col] = data[dataIndex++];
					break;
				}
				/*
				 * 如果row不小于count说明此行号不在data[dataIndex]的赋值区域内
				 * 令dataIndex自增, row -= count
				 * 以进行下一次判断
				 */
				row -= count;
				dataIndex++;
			}
		}
	}
	
	/**
	 * 获得从给定数组data中取出长度为len的数据进行组合而得到的第row行结果
	 * 此方法为int类型数组设计
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @param row 要获得的组合结果的行号（从0开始）
	 * @return 第row行组合结果
	 */
	public static int[] combination(int[] data, int len, long row) {
		int[] combinationArr = new int[len];
		combination(combinationArr, data, row);
		return combinationArr;
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行组合的结果
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @return 组合结果
	 */
	public static int[][] combination(int[] data, int len) {
		long count = countCombination(data.length, len);
		if (count > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("数据量超过Integer最大范围");
		}
		int[][] combinationArrs = new int[(int) count][len];
		for (int i = 0; i < count; i++) {
			combination(combinationArrs[i], data, i);
		}
		return combinationArrs;
	}
	
	/**
	 * 将从给定数组data中取出combinationArr数组长度的数据进行组合而得到的第row行结果存入combinationArr中
	 * @param combinationArr 存放生成数据的数组
	 * @param data 给定的源数组
	 * @param row 要获得的组合结果的行号（从0开始）
	 */
	public static <T> void combination(T[] combinationArr, T[] data, long row) {
		int dataSize = data.length;
		int dataIndex = 0;
		int len = combinationArr.length;
		for (int col = 0; col < len; col++) {
			int dataRightLen = dataSize-dataIndex;
			int rightLen = len-col;
			for (int i = 0; i < dataRightLen-rightLen+1; i++) {
				long count = countCombination(dataRightLen-i-1, rightLen-1);
				if (row < count) {
					combinationArr[col] = data[dataIndex++];
					break;
				}
				row -= count;
				dataIndex++;
			}
		}
	}
	
	/**
	 * 从给定数组data中取出combinationArrs[i]数组长度的数据进行组合，结果存入combinationArrs中
	 * @param combinationArrs 存放组合结果数组的数组
	 * @param data 给定的源数组
	 */
	public static <T> void combination(T[][] combinationArrs, T[] data) {
		int count = combinationArrs.length;
		for (int i = 0; i < count; i++) {
			combination(combinationArrs[i], data, i);
		}
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行组合而得到的第row行结果
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @param row 要获取的组合结果的行号（从0开始）
	 * @return 第row行组合结果
	 */
	public static <T> List<T> combination(List<T> data, int len, long row) {
		List<T> combinationList = new ArrayList<T>(len);
		int dataSize = data.size();
		int dataIndex = 0;
		for (int col = 0; col < len; col++) {
			int dataRightLen = dataSize-dataIndex;
			int rightLen = len-col;
			for (int i = 0; i < dataRightLen-rightLen+1; i++) {
				long count = countCombination(dataRightLen-i-1, rightLen-1);
				if (row < count) {
					combinationList.add(data.get(dataIndex++));
					break;
				}
				row -= count;
				dataIndex++;
			}
		}
		return combinationList;
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行组合的结果
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @return 组合结果集合
	 */
	public static <T> List<List<T>> combination(List<T> data, int len) {
		int dataSize = data.size();
		long count = countCombination(dataSize, len);
		if (count > Integer.MAX_VALUE) {
			throw new IllegalArgumentException("数据量超过Integer最大范围");
		}
		List<List<T>> combinationLists = new ArrayList<List<T>>((int) count);
		for (int i = 0; i < (int) count; i++) {
			combinationLists.add(combination(data, len, i));
		}
		return combinationLists;
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行组合而得到的第row行结果
	 * 数据量超大时风味更佳
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @param row 要获取的组合结果的行号（从0开始）
	 * @return 第row行组合结果
	 */
	public static int[] bigCombination(int[] data, int len, BigInteger row) {
		int[] combinationArr = new int[len];
		int dataSize = data.length;
		int dataIndex = 0;
		for (int col = 0; col < len; col++) {
			int dataRightLen = dataSize-dataIndex;
			int rightLen = len-col;
			for (int i = 0; i < dataRightLen-rightLen+1; i++) {
				BigInteger count = Combination.countBigCombination(dataRightLen-i-1, rightLen-1);
				if (row.compareTo(count) < 0) {
					combinationArr[col] = data[dataIndex++];
					break;
				}
				row = row.subtract(count);
				dataIndex++;
			}
		}
		return combinationArr;
	}
	
	/**
	 * 递归算法进行组合
	 * @param combinationArr 存放组合结果数组的数组
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @param dataIndex 正在组合的原数组起始列号
	 * @param row 给combinationArr 正在组合的起始行号
	 * @param col 给combinationArr 正在组合的列号
	 */
	private static void recursionCombination(int[][] combinationArr, int[] data, int len, int dataIndex, int row, int col) {
		//dataRightLen 源数组中还未组合的数据长度
		int dataRightLen = data.length-dataIndex;
		//rightLen 还未组合的数据长度
		int rightLen = len-col;
		/*
		 * dataRightLen-rightLen+1 源数组中可以为col列赋值的长度
		 * 对这个长度进行遍历为col列赋值
		 */
		for (int i = 0; i < dataRightLen-rightLen+1; i++) {
			/*
			 * 未组合的数据中取出第i个值
			 * 这个值在col列出现次数为C(未组合的数据长度-i-1， 还需组合的数据-1)次
			 * 将第row行到第row+count-1行 第col列赋值为data[dataCol+i]
			 */
			long count = countCombination(dataRightLen-i-1, rightLen-1);
			for (int j = 0; j < count; j++) {
				combinationArr[row+j][col] = data[dataIndex+i];
			}
			//如果还未排到最后一列则将正在组合的列号+1，递归执行本方法
			if (col+1 < len)
				recursionCombination(combinationArr, data, len, dataIndex+i+1, row, col+1);
			//将行号更新为尚未组合的位置
			row += count;
		}
	}
	
	/**
	 * 递归算法进行组合(附带初始化)
	 * @param data 给定的源数组
	 * @param len 组合的数据长度
	 * @return 组合的结果
	 */
	public static int[][] recursionCombination(int[] data, int len) {
		long count = countCombination(data.length, len);
		if (count > Integer.MAX_VALUE)
			throw new IllegalArgumentException("数据量超过Integer最大范围");
		int[][] combinationArr = new int[(int) count][len];
		recursionCombination(combinationArr, data, len, 0, 0, 0);
		return combinationArr;
	}
	
	/**
	 * 根据生成的组合数组计算其对应的行号
	 * @param combinationArr 生成的组合数组
	 * @param data 给定的源数组
	 * @return 行号
	 */
	public static long getCombinationRow(int[] combinationArr, int[] data) {
		//dataIndexs 生成的子集下标映射表
		int[] dataIndexs = ArrayUtils.getSubsetIndexs(combinationArr, data);
		long row = 0;
		int dataSize = data.length;
		//dataIndex 当前获取数据的下标
		int dataIndex = 0;
		int len = dataIndexs.length;
		for (int col = 0; col < len; col++) {
			//dataRightLen 源数组中还未计算的数据长度
			int dataRightLen = dataSize-dataIndex;
			//rightLen 还未计算的数据长度
			int rightLen = len-col;
			/*
			 * 本方法的核心思想在于根据数据下标计算行号
			 * 每列可能出现的数字共有dataRightLen-rightLen+1个
			 */
			for (int i = 0; i < dataRightLen-rightLen+1; i++) {
				//如果dataIndex和dataIndexs[col]相等时则跳出循环
				if (dataIndex == dataIndexs[col]) {
					dataIndex++;
					break;
				}
				long count = countCombination(dataRightLen-i-1, rightLen-1);
				dataIndex++;
				//如果dataIndex和dataIndexs[col]不相等(小于)则令row += count
				row += count;
			}
		}
		return row;
	}
	
	/**
	 * 根据生成的组合数组计算其对应的行号
	 * 数据量超大时风味更佳
	 * @param combinationArr 生成的组合数组
	 * @param data 给定的源数组
	 * @return 行号
	 */
	public static BigInteger getBigCombinationRow(int[] combinationArr, int[] data) {
		//dataIndexs 生成的子集下标映射表
		int[] dataIndexs = ArrayUtils.getSubsetIndexs(combinationArr, data);
		BigInteger row = new BigInteger("0");
		int dataSize = data.length;
		//dataIndex 当前获取数据的下标
		int dataIndex = 0;
		int len = dataIndexs.length;
		for (int col = 0; col < len; col++) {
			//dataRightLen 源数组中还未计算的数据长度
			int dataRightLen = dataSize-dataIndex;
			//rightLen 还未计算的数据长度
			int rightLen = len-col;
			/*
			 * 本方法的核心思想在于根据数据下标计算行号
			 * 每列可能出现的数字共有dataRightLen-rightLen+1个
			 */
			for (int i = 0; i < dataRightLen-rightLen+1; i++) {
				//如果dataIndex和dataIndexs[col]相等时则跳出循环
				if (dataIndex == dataIndexs[col]) {
					dataIndex++;
					break;
				}
				BigInteger count = countBigCombination(dataRightLen-i-1, rightLen-1);
				dataIndex++;
				//如果dataIndex和dataIndexs[col]不相等(小于)则令row += count
				row = row.add(count);
			}
		}
		return row;
	}
}
