package de.tassilo.io.json;

import java.io.IOException;
import java.io.Writer;
import java.util.Map.Entry;

public class JsonWriter extends Writer {
	private final Writer dest;
	private int deep;

	public JsonWriter(Writer dest) {
		this.dest = dest;
		this.deep = 0;
	}

	private void writeDeepness() throws IOException {
		for (int i = 0; i < deep; i++) write('\t');
	}



	public void writeJsonObject(JsonObject object, boolean compact) throws IOException {
		if (compact) writeJsonObjectCompact(object); else writeJsonObject(object);
	}

	private void writeJsonObject(JsonObject object) throws IOException {
		writeDeepness();
		write('{');
		deep++;

		boolean first = true;
		for (Entry<String, JsonValue> e : object.getValues().entrySet()) {
			if (!first) write(',');
			first = false;

			write('\n');
			writeDeepness();

			writeJsonString(e.getKey());
			write(e.getValue().isObject() ? ":\n" : ": ");
			writeJsonValue(e.getValue(), false);
		}

		write('\n');
		deep--;
		writeDeepness();
		write('}');
	}

	private void writeJsonObjectCompact(JsonObject object) throws IOException {
		write('{');
		boolean first = true;
		for (Entry<String, JsonValue> e : object.getValues().entrySet()) {
			if (!first) write(',');
			first = false;
			writeJsonString(e.getKey());
			write(':');
			writeJsonValue(e.getValue(), true);
		}
		write('}');
	}



	private void writeJsonArray(JsonValue value) throws IOException {
		if (!value.isArray()) return;
		JsonValue[] array = value.getArray();

		boolean multiline = array.length > 12;
		for (JsonValue v : array) multiline = multiline || v.isArray() || v.isObject();

		if (array.length == 0) {
			write("[]");
			return;
		}

		if (!multiline) {
			write("[ ");
			for (int i = 0; i < array.length; i++) {
				if (i != 0) write(", ");
				writeJsonValue(array[i], false);
			}
			write(" ]");
			return;
		}

		write('\n');
		writeDeepness();
		write("[\n");
		deep++;

		for (int i = 0; i < array.length; i++) {
			if (i != 0) write(",\n");
			writeDeepness();
			writeJsonValue(array[i], false);
		}

		write('\n');
		deep--;
		writeDeepness();
		write("]");
	}

	private void writeJsonArrayCompact(JsonValue value) throws IOException {
		if (!value.isArray()) return;
		JsonValue[] array = value.getArray();
		write('[');
		for (int i = 0; i < array.length; i++) {
			if (i != 0) write(',');
			writeJsonValue(array[i], true);
		}
		write(']');
	}



	private void writeJsonValue(JsonValue value, boolean compact) throws IOException {
		if (value.isObject()) {
			if (compact) writeJsonObjectCompact(value.getObject());
			else writeJsonObject(value.getObject());
		} else if (value.isArray()) {
			if (compact) writeJsonArrayCompact(value);
			else writeJsonArray(value);
		} else if (value.isString()) {
			writeJsonString(value.getString());
		} else {
			writeJsonStatic(value);
		}
	}

	private void writeJsonStatic(JsonValue value) throws IOException {
		if (value.isNull()) {
			write("null");
		} else if (value.isBoolean()) {
			write(value.getBoolean() ? "true" : "false");
		} else if (value.isNumber()) {
			write(Double.toString(value.getDouble()));
		} else; // else should never happen?
	}

	private void writeJsonString(String str) throws IOException {
		str = str.replace("\b", "\\b");
		str = str.replace("\f", "\\b");
		str = str.replace("\n", "\\b");
		str = str.replace("\r", "\\b");
		str = str.replace("\t", "\\b");
		str = str.replace("\"", "\\\"");
		str = str.replace("\\", "\\\\");
		write('"');
		write(str);
		write('"');
	}



	@Override
	public void close() throws IOException {
		dest.close();
	}

	@Override
	public void flush() throws IOException {
		dest.flush();
	}

	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		dest.write(cbuf, off, len);
	}

}
