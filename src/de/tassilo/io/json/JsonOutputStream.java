package de.tassilo.io.json;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class JsonOutputStream extends OutputStream {
	private final OutputStream dest;
	private final JsonWriter jsonWriter;

	public JsonOutputStream(OutputStream dest) {
		this.dest = dest;
		this.jsonWriter = new JsonWriter(new OutputStreamWriter(this));
	}

	@Override
	public void write(int b) throws IOException {
		dest.write(b);
	}

	@Override
	public void flush() throws IOException {
		dest.flush();
	}

	@Override
	public void close() throws IOException {
		dest.close();
	}

	public void writeObject(JsonObject object, boolean compact) throws IOException {
		jsonWriter.writeObject(object, compact);
	}

}
