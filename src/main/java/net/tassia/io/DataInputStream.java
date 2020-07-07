package net.tassia.io;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public class DataInputStream extends InputStream {
	private final InputStream source;

	public DataInputStream(InputStream source) {
		this.source = source;
	}



	public byte readByte() throws IOException {
		int ch = read();
		if (ch < 0) throw new EOFException();
		return (byte)(ch);
	}

	// public int readUnsignedByte() throws IOException {
	// TODO
	// }

	public boolean readBoolean() throws IOException {
		int ch = read();
		if (ch < 0) throw new EOFException();
		return (ch != 0);
	}

	public char readChar() throws IOException {
		int ch1 = read();
		int ch2 = read();
		if ((ch1 | ch2) < 0) throw new EOFException();
		return (char) ((ch1 << 8) + (ch2 << 0));
	}

	public short readShort() throws IOException {
		int ch1 = read();
		int ch2 = read();
		if ((ch1 | ch2) < 0) throw new EOFException();
		return (short) ((ch1 << 8) + (ch2 << 0));
	}

	// public int readUnsignedShort() throws IOException {
	// TODO
	// }

	public int readInt() throws IOException {
		int ch1 = read();
		int ch2 = read();
		int ch3 = read();
		int ch4 = read();
		if ((ch1 | ch2 | ch3 | ch4) < 0) throw new EOFException();
		return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
	}

	// public long readUnsignedInt() throws IOException {
	// TODO
	// }

	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public long readLong() throws IOException {
		int a = readInt();
		int b = readInt();
		return (long) a << 32 | b;
	}

	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	public <T extends Object> T readInput(DataInput<T> input) throws IOException {
		return input.readInput(this);
	}



	@Override
	public int read() throws IOException {
		return source.read();
	}

	@Override
	public int available() throws IOException {
		return source.available();
	}

	@Override
	public void close() throws IOException {
		source.close();
	}

	@Override
	public synchronized void mark(int readLimit) {
		source.mark(readLimit);
	}

	@Override
	public synchronized void reset() throws IOException {
		source.reset();
	}

	@Override
	public boolean markSupported() {
		return source.markSupported();
	}

}
