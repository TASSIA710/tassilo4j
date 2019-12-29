package de.tassilo.json;

public class JsonValue {
	public static final int TYPE_NULL		= 0x00;
	public static final int TYPE_BOOLEAN	= 0x01;
	public static final int TYPE_NUMBER		= 0x02;
	public static final int TYPE_STRING		= 0x03;
	public static final int TYPE_ARRAY		= 0x04;
	public static final int TYPE_OBJECT		= 0x05;
	private final int type;
	
	public static final JsonValue NULL = new JsonValue(TYPE_NULL);
	
	public static final JsonValue TRUE = new JsonValue(TYPE_BOOLEAN) {
		@Override
		public boolean getBoolean() { return true; }
	};
	
	public static final JsonValue FALSE = new JsonValue(TYPE_BOOLEAN) {
		@Override
		public boolean getBoolean() { return false; }
	};
	
	public static JsonValue getNumberValue(int x) {
		return new JsonValue(TYPE_NUMBER) {
			@Override
			public int getInteger() { return x; }
			@Override
			public long getLong() { return x; }
			@Override
			public float getFloat() { return x; }
			@Override
			public double getDouble() { return x; }
		};
	}
	
	public static JsonValue getNumberValue(long x) {
		return new JsonValue(TYPE_NUMBER) {
			@Override
			public int getInteger() { return (int) x; }
			@Override
			public long getLong() { return x; }
			@Override
			public float getFloat() { return x; }
			@Override
			public double getDouble() { return x; }
		};
	}
	
	public static JsonValue getNumberValue(float x) {
		return new JsonValue(TYPE_NUMBER) {
			@Override
			public int getInteger() { return Math.round(x); }
			@Override
			public long getLong() { return Math.round(x); }
			@Override
			public float getFloat() { return x; }
			@Override
			public double getDouble() { return x; }
		};
	}
	
	public static JsonValue getNumberValue(double x) {
		return new JsonValue(TYPE_NUMBER) {
			@Override
			public int getInteger() { return (int) Math.round(x); }
			@Override
			public long getLong() { return Math.round(x); }
			@Override
			public float getFloat() { return (float) x; }
			@Override
			public double getDouble() { return x; }
		};
	}
	
	public static JsonValue getStringValue(String x) {
		return new JsonValue(TYPE_STRING) {
			@Override
			public String getString() { return x; }
		};
	}
	
	public static JsonValue getArrayValue(JsonValue...values) {
		return new JsonValue(TYPE_ARRAY) {
			@Override
			public JsonValue[] getArray() { return values; }
		};
	}
	
	
	
	public JsonValue(int type) {
		this.type = type;
	}
	
	
	
	public int getType() {
		return type;
	}
	
	
	
	public boolean isNull() {
		return type == TYPE_NULL;
	}
	
	
	
	public boolean isBoolean() {
		return type == TYPE_BOOLEAN;
	}
	
	public boolean getBoolean() {
		return false;
	}
	
	
	
	public boolean isNumber() {
		return type == TYPE_NUMBER;
	}
	
	public int getInteger() {
		return 0;
	}
	
	public long getLong() {
		return 0l;
	}
	
	public float getFloat() {
		return 0f;
	}
	
	public double getDouble() {
		return 0d;
	}
	
	
	
	public boolean isString() {
		return type == TYPE_STRING;
	}
	
	public String getString() {
		return null;
	}
	
	
	
	public boolean isArray() {
		return type == TYPE_ARRAY;
	}
	
	public JsonValue[] getArray() {
		return null;
	}
	
	
	
	public boolean isObject() {
		return type == TYPE_OBJECT;
	}
	
	public JsonObject getObject() {
		return null;
	}
	
}