package de.tassilo.math;

public class Math {
	
	private Math() {
	}
	
	public static double parse(String expression) {
		return ComplexMath.parse(expression);
	}
	
}