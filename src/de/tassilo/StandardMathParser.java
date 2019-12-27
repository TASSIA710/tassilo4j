package de.tassilo;

class StandardMathParser extends MathParser {
	protected String expression;
	protected int pos = -1, ch;
	
	StandardMathParser() {
	}
	
	void nextChar() {
		ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
	}
	
	boolean eat(int charToEat) {
		while (ch == ' ') nextChar();
		if (ch == charToEat) {
			nextChar();
			return true;
		}
		return false;
	}
	
	@Override
	public double parse(String expression) {
		this.expression = expression.trim().toLowerCase();
		nextChar();
		double x = parseExpression();
		if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
		return x;
	}
	
	double parseExpression() {
		double x = parseTerm();
		for (;;) {
			if (eat('+')) x += parseTerm();
			else if (eat('-')) x -= parseTerm();
			else return x;
		}
	}
	
	double parseTerm() {
		double x = parseFactor();
		for (;;) {
			if (eat('*')) x *= parseFactor();
			else if (eat('/')) x/= parseFactor();
			else return x;
		}
	}
	
	double parseFactor() {
		if (eat('+')) return parseFactor();
		if (eat('-')) return -parseFactor();
		
		double x;
		int startPos = this.pos;
		if (eat('(')) {
			x = parseExpression();
			eat(')');
		} else if ((ch >= '0' && ch <= '9') || ch == '.') {
			while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
			x = Double.parseDouble(expression.substring(startPos, this.pos));
		} else if (ch >= 'a' && ch <= 'z') {
			while (ch >= 'a' && ch <= 'z') nextChar();
			String func = expression.substring(startPos, this.pos);
			x = parseFactor();
			if (func.equals("sqrt")) x = StrictMath.sqrt(x);
			else if (func.equals("sin")) x = StrictMath.sin(x);
			else if (func.equals("cos")) x = StrictMath.cos(x);
			else if (func.equals("tab")) x = StrictMath.tan(x);
			else throw new RuntimeException("Unknown function: " + func);
		} else {
			throw new RuntimeException("Unexpected: " + (char)ch);
		}
		
		if (eat('^')) x = StrictMath.pow(x, parseFactor());
		return x;
	}
	
}