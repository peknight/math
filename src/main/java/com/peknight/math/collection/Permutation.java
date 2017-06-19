package com.peknight.math.collection;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.peknight.common.collection.ArrayUtils;
import com.peknight.math.factorial.Factorial;

/**
 * 全排列算法类
 * 本类提供了计算排列数以及对数据进行全排列的多种方法
 * 全排列算法提供了多个重载以便对不同类型的数据进行全排列
 * 除了递归算法外，其他算法核心思想是相同的
 * 在总体数据量不大的情况下（通常指排列长度不超过10）
 * 如需获取全排列所有情况，推荐使用本类提供的递归算法
 * 在总体数据量比较大的情况下，不建议获取全排列所有情况
 * 在总体数据量超级大的情况下，请使用bigPermutation方法获取单条情况
 * 
 * @author Peknight
 *
 */
public class Permutation {
	
	private Permutation() {}
	
	/** 计算排列数 */
	public static long countPermutation(int n, int m) {
		return Factorial.factorial(n, n-m+1);
	}
	
	/** 计算排列数（数据较大时使用） */
	public static BigInteger countBigPermutation(int n, int m) {
		return Factorial.bigFactorial(n, n-m+1);
	}

	/**
	 * 将从给定数组data中取出permutationArr数组长度的数据进行全排列而得到的第row行结果存入permutationArr中
	 * 此方法为int类型数组设计
	 * 此方法在获取多条数据时使用风味更佳
	 * @param permutationArr 存放生成数据的数组
	 * @param data 给定的源数组
	 * @param row 要获得的排列结果的行号（从0开始）
	 * @param mapping 源数组下表的映射表， 用于模拟从data中删除元素
	 */
	public static void permutation(int[] permutationArr, int[] data, long row, int[] mapping) {
		ArrayUtils.initSerialArray(mapping);
		int dataSize = data.length;
		int len = permutationArr.length;
		for (int col = 0; col < len; col++) {
			/*
			 * 本方法的核心思想在于根据行号计算要取出的数据下标
			 * 为每一列赋值前行号先对A(数据长度-已排列长度, 排列长度-已排列长度)取模
			 * 下标值为行号除以A(数据长度-已排列长度+1, 排列长度-已排列长度-1)的商
			 * 在mapping映射表中获取data数组中的实际下标存入permutationArr数组
			 */
			row %= countPermutation(dataSize-col, len-col);
			int index = (int) (row/countPermutation(dataSize-col-1, len-col-1));
			permutationArr[col] = data[mapping[index]];
			//将映射表中后面未排列的下标向前移一位
			ArrayUtils.arrayLeftShift(mapping, index, dataSize-col);
		}
	}

	/**
	 * 获得从给定数组data中取出permutationArr数组长度的数据进行全排列而得到的第row行结果
	 * 此方法为int类型数组设计
	 * 此方法在获取多条数据时使用风味更佳
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @param row 要获得的排列结果的行号（从0开始）
	 * @param mapping 第row行排列结果
	 * @return
	 */
	public static int[] permutation(int[] data, int len, long row, int[] mapping) {
		int[] permutationArr = new int[len];
		permutation(permutationArr, data, row, mapping);
		return permutationArr;
	}
	
	/**
	 * 获得从给定数组data中取出长度为len的数据进行全排列而得到的第row行结果
	 * 此方法为int类型数组设计
	 * 此方法在获取单行数据时使用风味更佳
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @param row 要获得的排列结果的行号（从0开始）
	 * @return 第row行排列结果
	 */
	public static int[] permutation(int[] data, int len, long row) {
		int[] mapping = getMapping(data);
		int[] permutationArr = new int[len];
		permutation(permutationArr, data, row, mapping);
		return permutationArr;
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行全排列的结果
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @return 全排列结果
	 */
	public static int[][] permutation(int[] data, int len) {
		long count = countPermutation(data.length, len);
		if (count > Integer.MAX_VALUE) {
			throw new RuntimeException("数据量超过Integer最大范围");
		}
		int[][] permutationArrs = new int[(int) count][len];
		int[] mapping = getMapping(data);
		for (int i = 0; i <(int) count; i++) {
			permutation(permutationArrs[i], data, i, mapping);
		}
		return permutationArrs;
	}
	
	/**
	 * 从给定数组data中取出permutationArr数组长度的数据进行全排列，得到的第row行结果存入permutationArr中
	 * 此方法在获取多条数据时使用风味更佳
	 * @param permutationArr 存放生成数据的数组
	 * @param data 给定的源数组
	 * @param row 要获得的排列结果的行号（从0开始）
	 * @param mapping 源数组下表的映射表， 用于模拟从data中删除元素
	 */
	public static <T> void permutation(T[] permutationArr, T[] data, long row, int[] mapping) {
		ArrayUtils.initSerialArray(mapping);
		int dataSize = data.length;
		int len = permutationArr.length;
		for (int col = 0; col < len; col++) {
			row %= countPermutation(dataSize-col, len-col);
			int index = (int) (row/countPermutation(dataSize-col-1, len-col-1));
			permutationArr[col] = data[mapping[index]];
			ArrayUtils.arrayLeftShift(mapping, index, dataSize-col);
		}
	}
	
	/**
	 * 将从给定数组data中取出permutationArr数组长度的数据进行全排列而得到的第row行结果存入permutationArr中
	 * 此方法在获取单行数据时使用风味更佳
	 * @param permutationArr 存放生成数据的数组
	 * @param data 给定的源数组
	 * @param row 要获得的排列结果的行号（从0开始）
	 */
	public static <T> void permutation(T[] permutationArr, T[] data, long row) {
		int[] mapping = getMapping(data);
		permutation(permutationArr, data, row, mapping);
	}
	
	/**
	 * 从给定数组data中取出permutationArrs[i]数组长度的数据进行全排列，结果存入permutationArrs中
	 * @param permutationArrs 存放全排列结果数组的数组
	 * @param data 给定的源数组
	 */
	public static <T> void permutation(T[][] permutationArrs, T[] data) {
		int count = permutationArrs.length;
		int[] mapping = getMapping(data);
		for (int i = 0; i < count; i++) {
			permutation(permutationArrs[i], data, i, mapping);
		}
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行全排列而得到的第row行结果
	 * 此方法在获取多条数据时使用风味更佳
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @param row 要获取的排列结果的行号（从0开始）
	 * @param mapping 源数据下标的映射表，用于模拟从list中删除元素
	 * @return 第row行排列结果
	 */
	public static <T> List<T> permutation(List<T> data, int len, long row, int[] mapping) {
		List<T> permutationList = new ArrayList<T>(len); 
		ArrayUtils.initSerialArray(mapping);
		int dataSize = data.size();
		for (int col = 0; col < len; col++) {
			row %= countPermutation(dataSize-col, len-col);
			int index = (int) (row/countPermutation(dataSize-col-1, len-col-1));
			permutationList.add(data.get(mapping[index]));
			ArrayUtils.arrayLeftShift(mapping, index, dataSize-col);
		}
		return permutationList;
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行全排列而得到的第row行结果
	 * 此方法在获取单行数据时使用风味更佳
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @param row 要获取的排列结果的行号（从0开始）
	 * @return 第row行排列结果
	 */
	public static <T> List<T> permutation(List<T> data, int len, long row) {
		int[] mapping = getMapping(data);
		return permutation(data, len, row, mapping);
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行全排列的结果
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @return 全排列结果集合
	 */
	public static <T> List<List<T>> permutation (List<T> data, int len) {
		int dataSize = data.size();
		long count = countPermutation(dataSize, len);
		if (count > Integer.MAX_VALUE) {
			throw new RuntimeException("数据量超过Integer最大范围");
		}
		List<List<T>> permutationLists = new ArrayList<List<T>>((int) count);
		int[] mapping = getMapping(data);
		for (int i = 0; i < (int) count; i++) {
			permutationLists.add(permutation(data, len, i, mapping));
		}
		return permutationLists;
	}
		
	/**
	 * 获得从给定数据data中取出长度为len的数据进行全排列而得到的第row行结果
	 * 数据量超大时风味更佳
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @param row 要获取的排列结果的行号（从0开始）
	 * @param mapping 源数据下标的映射表，用于模拟从list中删除元素
	 * @return 第row行排列结果
	 */
	public static int[] bigPermutation(int[] data, int len, BigInteger row, int[] mapping) {
		int[] permutationArr = new int[len];
		ArrayUtils.initSerialArray(mapping);
		int dataSize = data.length;
		for (int col = 0; col < len; col++) {
			row = row.mod(countBigPermutation(dataSize-col, len-col));
			int index = row.divide(countBigPermutation(dataSize-col-1, len-col-1)).intValue();
			permutationArr[col] = data[mapping[index]];
			ArrayUtils.arrayLeftShift(mapping, index, dataSize-col);
		}
		return permutationArr;
	}
	
	/**
	 * 获得从给定数据data中取出长度为len的数据进行全排列而得到的第row行结果
	 * 数据量超大时风味更佳
	 * @param data 给定的源数据
	 * @param len 取出的数据长度
	 * @param row 要获取的排列结果的行号（从0开始）
	 * @return 第row行排列结果
	 */
	public static int[] bigPermutation(int[] data, int len, BigInteger row) {
		int[] mapping = getMapping(data);
		return bigPermutation(data, len, row, mapping);
	}
	
	/**
	 * 递归算法进行全排列
	 * @param permutationArrs 存放全排列结果数组的数组
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @param row 正在排列的起始行号
	 * @param col 正在排列的列号
	 */
	private static void recursionPermutation(int[][] permutationArrs, int[] data, int len, int row, int col) {
		int dataSize = data.length;
		//rightLen 还未排列长度的数据
		int rightLen = len-col;
		/*
		 * dataSize-col 未排列过的数据长度
		 * 对这个长度进行遍历为col列赋值
		 */
		for (int i = 0; i < dataSize-col; i++) {
			/*
			 * 未排列的数据中取出第i个值
			 * 这个值在col列出现次数为A(未排列的数据长度-1， 还需排列的数据-1)次
			 * 将第row行到第row+count-1行 第col列赋值为data[i]
			 */
			long count =countPermutation(dataSize-col-1, rightLen-1);
			for (int j = 0; j < count; j++) {
				permutationArrs[row+j][col] = data[i];
			}
			//将data[i]放在未排列的数据后面，并将后面未排列的数据向前移一位
			ArrayUtils.arrayLeftShift(data, i, dataSize-col);
			//如果还未排到最后一列则将正在排列的列号+1，递归执行本方法
			if (col+1 < len) {
				recursionPermutation(permutationArrs, data, len, row, col+1);
			}
			//递归结束将data[i]位置还原
			ArrayUtils.arrayRightShift(data, i, dataSize-col);
			//将行号更新为尚未排列的位置
			row += count;
		}
	}
	
	/**
	 * 递归算法进行全排列（附带初始化）
	 * @param data 给定的源数组
	 * @param len 取出的数据长度
	 * @return 全排列结果
	 */
	public static int[][] recursionPermutation(int[] data, int len) {
		long count = countPermutation(data.length, len);
		if (count > Integer.MAX_VALUE) {
			throw new RuntimeException("数据量超过Integer最大范围");
		}
		int[][] permutationArr = new int[(int) count][len];
		recursionPermutation(permutationArr, data, len, 0, 0);
		return permutationArr;
	}
	
	/**
	 * 根据生成的排列数组计算其对应的行号
	 * @param permutationArr 生成的排列数组
	 * @param data 给定的源数组
	 * @return 行号
	 */
	public static long getPermutationRow(int[] permutationArr, int[] data) {
		//dataIndexs 生成的子集下标映射表
		int[] dataIndexs = ArrayUtils.getSubsetIndexs(permutationArr, data);
		//mapping 生成data下标映射表
		int[] mapping = getMapping(data);
		ArrayUtils.initSerialArray(mapping);
		long row = 0;
		int dataSize = data.length;
		int len = dataIndexs.length;
		for (int col = 0; col < len; col++) {
			/*
			 * 本方法的核心思想在于根据数据下标计算行号
			 * 先获取子集下表映射表dataIndexs中第col个元素在mapping中对应的下标
			 */
			int index = ArrayUtils.indexOf(dataIndexs[col], mapping);
			row += countPermutation(dataSize-col-1, len-col-1)*index;
			//将映射表中后面未计算的下标向前移一位
			ArrayUtils.arrayLeftShift(mapping, index, dataSize-col);
		}
		return row;
	}
	
	/**
	 * 根据生成的排列数组计算其对应的行号
	 * 数据量超大时风味更佳
	 * @param permutationArr 生成的排列数组
	 * @param data 给定的源数组
	 * @return 行号
	 */
	public static BigInteger getBigPermutationRow(int[] permutationArr, int[] data) {
		//dataIndexs 生成的子集下标映射表
		int[] dataIndexs = ArrayUtils.getSubsetIndexs(permutationArr, data);
		//mapping 生成data下标映射表
		int[] mapping = getMapping(data);
		ArrayUtils.initSerialArray(mapping);
		BigInteger row = new BigInteger("0");
		int dataSize = data.length;
		int len = dataIndexs.length;
		for (int col = 0; col < len; col++) {
			/*
			 * 本方法的核心思想在于根据数据下标计算行号
			 * 先获取子集下表映射表dataIndexs中第col个元素在mapping中对应的下标
			 */
			int index = ArrayUtils.indexOf(dataIndexs[col], mapping);
			row = row.add(countBigPermutation(dataSize-col-1, len-col-1).multiply(BigInteger.valueOf(index)));
			//将映射表中后面未计算的下标向前移一位
			ArrayUtils.arrayLeftShift(mapping, index, dataSize-col);
		}
		return row;
	}
	
	/**
	 * 生成一个映射表
	 * @param data 给定的源数组
	 * @return 未初始化的映射表
	 */
	public static int[] getMapping(int[] data) {
		return new int[data.length];
	}
	
	/**
	 * 生成一个映射表
	 * @param data 给定的源数组
	 * @return 未初始化的映射表
	 */
	public static <T> int[] getMapping(T[] data) {
		return new int[data.length];
	}
	
	/**
	 * 生成一个映射表
	 * @param data 给定的源数据
	 * @return 未初始化的映射表
	 */
	public static <T> int[] getMapping(List<T> data) {
		return new int[data.size()];
	}
}
