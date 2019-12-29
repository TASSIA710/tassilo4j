package de.tassilo.json;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class JsonReader extends Reader {
	private final Reader source;
	
	public JsonReader(Reader source) {
		this.source = source;
	}
	
	int nextChar = -1, position = -1;
	
	
	
	protected void nextChar() throws IOException {
		nextChar = read();
		position++;
	}
	
	protected boolean checkNext(int charToCheck) throws IOException {
		if (position == -1) nextChar(); // read the first char
		while (Character.isWhitespace(nextChar)) nextChar();
		return nextChar == charToCheck;
	}
	
	protected boolean eat(int charToEat) throws IOException {
		if (checkNext(charToEat)) {
			nextChar();
			return true;
		}
		return false;
	}
	
	public JsonObject readObject() throws ParseException, IOException {
		if (!eat('{')) throw new ParseException("expected '{', got " + new String(Character.toChars(nextChar)), position);
		JsonObject obj = new JsonObject();
		
		while (true) {
			String key = readString();
			if (!eat(':')) throw new ParseException("expected ':'", position);
			JsonValue value = readValue();
			obj.setValue(key, value);
			if (eat('}')) break;
			if (!eat(',')) throw new ParseException("expected ','", position);
		}
		
		return obj;
	}
	
	private JsonValue readValue() throws IOException, ParseException {
		if (checkNext('"')) {
			return JsonValue.getStringValue(readString());
		} else if (checkNext('[')) {
			return readArray();
		} else if (checkNext('{')) {
			return readObject();
		} else {
			return readStatic();
		}
	}
	
	private JsonValue readArray() throws ParseException, IOException {
		if (!eat('[')) throw new ParseException("expected '['", position);
		if (eat(']')) return JsonValue.getArrayValue();
		
		List<JsonValue> values = new ArrayList<JsonValue>();
		while (true) {
			values.add(readValue());
			if (eat(']')) break;
			if (!eat(',')) throw new ParseException("expected ','", position);
		}
		
		JsonValue[] array = new JsonValue[values.size()];
		array = values.toArray(array);
		return JsonValue.getArrayValue(array);
	}
	
	private JsonValue readStatic() throws IOException, ParseException {
		StringBuilder sb = new StringBuilder();
		
		while (true) {
			if (nextChar == ' ' || nextChar == ',' || nextChar == ']' || nextChar == '}' || Character.isWhitespace(nextChar)) break;
			sb.appendCodePoint(nextChar);
			nextChar();
		}
		
		String str = sb.toString();
		
		if (str.toLowerCase().equals("null")) return JsonValue.NULL;
		if (str.toLowerCase().equals("true")) return JsonValue.TRUE;
		if (str.toLowerCase().equals("false")) return JsonValue.FALSE;
		
		if (str.contains(".")) {
			try {
				double value = Double.valueOf(str).doubleValue();
				return JsonValue.getNumberValue(value);
			} catch (NumberFormatException ex) {
				throw new ParseException("unexpected '" + str + "'", position);
			}
		} else {
			try {
				long value = Double.valueOf(str).longValue();
				return JsonValue.getNumberValue(value);
			} catch (NumberFormatException ex) {
				throw new ParseException("unexpected '" + str + "'", position);
			}
		}
			
	}
	
	private String readString() throws ParseException, IOException {
		if (!eat('"')) throw new ParseException("expected '\"'", position);
		StringBuilder sb = new StringBuilder();
		
		while (true) {
			
			if (nextChar == '\\') {
				// escape sequence
				nextChar();
				
				switch (nextChar) {
				
				case 'b':
					sb.append('\b');
					break;
					
				case 'f':
					sb.append('\f');
					break;
					
				case 'n':
					sb.append('\n');
					break;
					
				case 'r':
					sb.append('\r');
					break;
					
				case 't':
					sb.append('\t');
					break;
					
				case '"':
					sb.append('"');
					break;
					
				case '\\':
					sb.append('\\');
					break;
				
				default:
					throw new ParseException("unexpected '" + new String(Character.toChars(nextChar)) + "'", position);
				}
				continue;
			}
			
			if (nextChar == '"') {
				// string ends here
				nextChar();
				break;
			}
			
			sb.appendCodePoint(nextChar);
			nextChar();
		}
		
		return sb.toString();
	}
	
	
	
	@Override
	public void close() throws IOException {
		source.close();
	}
	
	@Override
	public int read(char[] cbuf, int off, int len) throws IOException {
		return source.read(cbuf, off, len);
	}
	
}