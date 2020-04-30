package de.tassilo.math;

import java.util.function.Consumer;

public class Math {

	private Math() {
	}

	/* Expression Parsing */
	public static double parse(String expression) {
		return ComplexMath.parse(expression);
	}
	/* Expression Parsing */



	/* Single Parsing */
	public static int parseInt(String s) {
		return parseInt(s, 0);
	}

	public static int parseInt(String s, int fallback) {
		if (s == null) return fallback;
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException ex) {
			return fallback;
		}
	}

	public static long parseLong(String s) {
		return parseLong(s, 0l);
	}

	public static long parseLong(String s, long fallback) {
		if (s == null) return fallback;
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException ex) {
			return fallback;
		}
	}

	public static float parseFloat(String s) {
		return parseFloat(s, 0f);
	}

	public static float parseFloat(String s, float fallback) {
		if (s == null) return fallback;
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException ex) {
			return fallback;
		}
	}

	public static double parseDouble(String s) {
		return parseDouble(s, 0d);
	}

	public static double parseDouble(String s, double fallback) {
		if (s == null) return fallback;
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException ex) {
			return fallback;
		}
	}
	/* Single Parsing */
	
	
	
	/* Fibonacci */
	protected static void _fibonacci(int index, long buffer[], int a, int b) {
		buffer[index] = b;
		index++;
		if (index >= buffer.length) return;
		_fibonacci(index, buffer, b, a + b);
	}
	
	public static long[] fibonacci(int length) {
		long[] buffer = new long[length];
		_fibonacci(0, buffer, 0, 1);
		return buffer;
	}
	/* Fibonacci */
	
	

	/* Collatz Conjecture */
	public static long collatzConjectureOptimised(long start) {
		if (start == 0) return 0;
		if (start == 1) return 1;
		if (start % 2 == 0) return collatzConjectureOptimised(start / 2);
		else return collatzConjectureOptimised((start * 3 + 1) / 2);
	}
	
	public static long collatzConjecture(long start) {
		return collatzConjecture(start, (e) -> {});
	}
	
	public static long collatzConjecture(long start, Consumer<Long> callbacks) {
		callbacks.accept(start);
		
		if (start == 0) {
			// If we reach 0 (which should basically be impossible, unless we start at it
			// we also end up in an infinite loop as we are stuck in zero (0 / 2 = 0)
			return 0;
		}
		
		if (start == 1) {
			// If we reach 1, we can just assume that we would end in a loop:
			// 1 goes to 4, which will then go back to 1
			return 1;
		}
		
		if (start % 2 == 0) return collatzConjecture(start / 2, callbacks);
		else return collatzConjecture(start * 3 + 1, callbacks);
	}
	/* Collatz Conjecture */

}
