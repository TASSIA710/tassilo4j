package net.tassia.util;

import java.util.function.Function;

public class Arrays {

	private Arrays() {
	}



	public static <A, B> B[] convert(A[] source, B[] dest, Function<A, B> converter) {
		for (int i = 0; i < source.length; i++)
			dest[i] = converter.apply(source[i]);
		return dest;
	}



	public static byte[][] splitArrayIntoChunks(byte[] source, int size) {
		byte[][] ret = new byte[(int) Math.ceil(source.length / (double) size)][size];
		int start = 0;
		for (int i = 0; i < ret.length; i++) {
			ret[i] = java.util.Arrays.copyOfRange(source, start, start + size);
			start += size;
		}
		return ret;
	}

}