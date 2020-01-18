package de.tassilo.math;

public class MathOptions {
	protected MathParser parser;
	
	public MathOptions() {
		this.parser = new StandardMathParser();
	}
	
	
	
	public MathParser getParser() {
		return parser;
	}
	
	public void setParser(MathParser parser) {
		this.parser = parser;
	}
	
}