package net.tassia.io;

import java.io.IOException;
import java.io.OutputStream;

public class DataOutputStream extends OutputStream {
	private final OutputStream dest;

	public DataOutputStream(OutputStream dest) {
		this.dest = dest;
	}



	public void writeByte(byte x) throws IOException {
		write(x);
	}

	// public void writeUnsignedByte(int x) throws IOException {
	// TODO
	// }

	public void writeBoolean(boolean x) throws IOException {
		write(x ? 1 : 0);
	}

	public void writeChar(char x) throws IOException {
		write((x >>> 8) & 0xFF);
		write((x >>> 0) & 0xFF);
	}

	public void writeShort(short x) throws IOException {
		write((x >>> 8) & 0xFF);
		write((x >>> 0) & 0xFF);
	}

	// public void writeUnsignedShort(int x) throws IOException {
	// TODO
	// }

	public void writeInt(int x) throws IOException {
		write((x >>> 24) & 0xFF);
		write((x >>> 16) & 0xFF);
		write((x >>>  8) & 0xFF);
		write((x >>>  0) & 0xFF);
	}

	// public void writeUnsignedInt(long x) throws IOException {
	// TODO
	// }

	public void writeFloat(float x) throws IOException {
		writeInt(Float.floatToIntBits(x));
	}

	public void writeLong(long x) throws IOException {
		write((int) (x >>> 56) & 0xFF);
		write((int) (x >>> 48) & 0xFF);
		write((int) (x >>> 40) & 0xFF);
		write((int) (x >>> 32) & 0xFF);
		write((int) (x >>> 24) & 0xFF);
		write((int) (x >>> 16) & 0xFF);
		write((int) (x >>>  8) & 0xFF);
		write((int) (x >>>  0) & 0xFF);
	}

	public void writeDouble(double x) throws IOException {
		writeLong(Double.doubleToLongBits(x));
	}
	
	public <T extends Object> void writeOutput(DataOutput<T> output, T object) throws IOException {
		output.writeOutput(this, object);
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

}
