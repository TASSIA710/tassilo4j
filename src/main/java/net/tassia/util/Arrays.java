package net.tassia.util;

import java.util.function.Function;

/**
 * This utility class allows various operations with arrays.
 * @author Tassilo
 * @since Tassilo4J 2.0
 */
public class Arrays {

	/**
	 * This constructor is private as no instance of this class is needed.
	 */
	private Arrays() {
	}



	/**
	 * Converts an array from one class to another.
	 * @param source the source array
	 * @param converter the function used to convert the objects
	 * @return the converted array
	 */
	public static <A, B> B[] convert(A[] source, Function<A, B> converter) {
		return convert(source, converter, (B[]) new Object[source.length]);
	}

	/**
	 * Converts an array from one class to another.
	 * @param source the source array
	 * @param converter the function used to convert the objects
	 * @param dest the array to insert the data into, must have the same length as the source array
	 * @return the filled destination array
	 * @throws IllegalArgumentException if <code>dest.length != source.length</code>
	 */
	public static <A, B> B[] convert(A[] source, Function<A, B> converter, B[] dest) throws IllegalArgumentException {
		if (source.length != dest.length) {
			throw new IllegalArgumentException("dest-array length must be equal to source-array length");
		}
		for (int i = 0; i < source.length; i++) {
			if (source[i] != null) {
				dest[i] = converter.apply(source[i]);
			} else {
				dest[i] = null;
			}
		}
		return dest;
	}



	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static byte[][] splitArrayIntoChunks(byte[] source, int size) {
		byte[][] chunks = new byte[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static boolean[][] splitArrayIntoChunks(boolean[] source, int size) {
		boolean[][] chunks = new boolean[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static short[][] splitArrayIntoChunks(short[] source, int size) {
		short[][] chunks = new short[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static char[][] splitArrayIntoChunks(char[] source, int size) {
		char[][] chunks = new char[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static int[][] splitArrayIntoChunks(int[] source, int size) {
		int[][] chunks = new int[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static float[][] splitArrayIntoChunks(float[] source, int size) {
		float[][] chunks = new float[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static long[][] splitArrayIntoChunks(long[] source, int size) {
		long[][] chunks = new long[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static double[][] splitArrayIntoChunks(double[] source, int size) {
		double[][] chunks = new double[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

	/**
	 * Splits an array into arrays (chunks) of equal sizes.
	 * The last chunk is filled with null-objects if the length
	 * of the source array is not a multiple of size.
	 * @param source the source array
	 * @param size the size of each chunk
	 * @return an array of all the chunks
	 */
	public static <T extends Object> T[][] splitArrayIntoChunks(T[] source, int size) {
		T[][] chunks = (T[][]) new Object[(int) Math.ceil(source.length / (double) size)][size];
		for (int i = 0; i < source.length; i++) {
			chunks[i / size][i % size] = source[i];
		}
		return chunks;
	}

}