package de.tassilo.math;

public class ComplexMath {
	
	/* Variables & Constructor */
	private static MathOptions globalOptions;
	
	static {
		globalOptions = new MathOptions();
	}
	
	private ComplexMath() {
	}
	/* Variables & Constructor */
	
	
	
	/* Global Options */
	public static MathOptions getGlobalOptions() {
		return globalOptions;
	}
	
	public static void setGlobalOptions(MathOptions globalOptions) {
		ComplexMath.globalOptions = globalOptions;
	}
	/* Global Options */
	
	
	
	/* Parsing */
	public static double parse(String expression) {
		return parse(expression, getGlobalOptions());
	}
	
	public static double parse(String expression, MathOptions options) {
		return options.getParser().parse(expression);
	}
	/* Parsing */
	
}