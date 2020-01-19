package de.tassilo;

import java.util.function.Function;

public class Arrays {
	
	private Arrays() {
	}
	
	public static <A, B> B[] convert(A[] source, B[] dest, Function<A, B> converter) {
		for (int i = 0; i < source.length; i++)
			dest[i] = converter.apply(source[i]);
		return dest;
	}
}