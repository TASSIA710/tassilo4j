package de.tassilo.io.json;

import java.util.HashMap;
import java.util.Map;

public class JsonObject extends JsonValue {
	protected Map<String, JsonValue> values;

	public JsonObject() {
		super(JsonValue.TYPE_OBJECT);
		this.values = new HashMap<String, JsonValue>();
	}

	public JsonObject getObject() {
		return this;
	}



	public JsonObject setValue(String field, String value) {
		return setValue(field, value == null ? JsonValue.NULL : JsonValue.getStringValue(value));
	}

	public JsonObject setValue(String field, boolean value) {
		return setValue(field, value ? JsonValue.TRUE : JsonValue.FALSE);
	}

	public JsonObject setValue(String field, int value) {
		return setValue(field, JsonValue.getNumberValue(value));
	}

	public JsonObject setValue(String field, long value) {
		return setValue(field, JsonValue.getNumberValue(value));
	}

	public JsonObject setValue(String field, float value) {
		return setValue(field, JsonValue.getNumberValue(value));
	}

	public JsonObject setValue(String field, double value) {
		return setValue(field, JsonValue.getNumberValue(value));
	}

	public JsonObject setValue(String field, JsonValue value) {
		if (value == null) value = JsonValue.NULL;
		values.put(field, value);
		return this;
	}

	public Map<String, JsonValue> getValues() {
		return values;
	}

	protected JsonValue getValueAtPath(String path, String...deeper) {
		JsonValue value = values.get(path);
		if (deeper.length == 0) return value;
		if (!value.isObject()) return null;
		for (int i = 0; i < deeper.length; i++) {
			value = value.getObject().values.get(deeper[i]);
			if (i == deeper.length - 1) return value;
			if (!value.isObject()) return null;
		}
		return value;
	}

	public int getType(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getType() : JsonValue.TYPE_NULL;
	}

	public boolean isNull(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.isNull() : false;
	}

	public boolean isBoolean(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.isBoolean() : false;
	}

	public boolean getBoolean(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getBoolean() : false;
	}

	public boolean isNumber(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.isNumber() : false;
	}

	public int getInteger(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getInteger() : 0;
	}

	public long getLong(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getLong() : 0l;
	}

	public float getFloat(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getFloat() : 0f;
	}

	public double getDouble(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getDouble() : 0d;
	}

	public boolean isString(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.isString() : false;
	}

	public String getString(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getString() : null;
	}

	public boolean isArray(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.isArray() : false;
	}

	public JsonValue[] getArray(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getArray() : null;
	}

	public boolean isObject(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.isObject() : false;
	}

	public JsonObject getObject(String path, String...deeper) {
		JsonValue value = getValueAtPath(path, deeper);
		return value != null ? value.getObject() : null;
	}

}
