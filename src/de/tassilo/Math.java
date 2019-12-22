package de.tassilo;

public class Math {
	
	private Math() {
	}
	
	public static double parse(String expression) {
		return new StandardMathParser().parse(expression);
	}
	
}