package de.tassilo.math;

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
	
}