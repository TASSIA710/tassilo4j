package de.tassilo.io.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;

public class JsonInputStream extends InputStream {
	private final InputStream src;
	private final JsonReader jsonReader;

	public JsonInputStream(InputStream src) {
		this.src = src;
		this.jsonReader = new JsonReader(new InputStreamReader(this));
	}

	@Override
	public int read() throws IOException {
		return src.read();
	}

	@Override
	public void close() throws IOException {
		src.close();
	}

	public JsonObject readJsonObject() throws ParseException, IOException {
		return jsonReader.readJsonObject();
	}

}
