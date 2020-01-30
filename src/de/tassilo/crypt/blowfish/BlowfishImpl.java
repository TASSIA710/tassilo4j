package de.tassilo.crypt.blowfish;

public class BlowfishImpl implements Blowfish {
	private final long m;
	private final long[] p;
	private final long[][] s;

	/* Constructor */
	public BlowfishImpl(byte[] key) {
		if (key == null || key.length == 0) {
			throw new IllegalArgumentException("No valid key was given.");
		}

		// Initialize variables
		m = 0x00000000ffffffffL;
		p = new long[18];
		s = new long[4][256];

		// Populate P-array and S-boxes
		System.arraycopy(BlowfishConstants.P, 0, p, 0, p.length);
		System.arraycopy(BlowfishConstants.S0, 0, s[0], 0, s[0].length);
		System.arraycopy(BlowfishConstants.S1, 0, s[1], 0, s[1].length);
		System.arraycopy(BlowfishConstants.S2, 0, s[2], 0, s[2].length);
		System.arraycopy(BlowfishConstants.S3, 0, s[3], 0, s[3].length);

		// Initialize key
		for (int i = 0; i < 18; i++) {
			p[i] = (p[i] ^ key[i % key.length]) & m;
		}
		long[] lr = new long[2];
		for (int i = 0; i < 18; i += 2) {
			encrypt2l(lr);
			p[i] = lr[0];
			p[i+1] = lr[1];
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 256; j += 2) {
				encrypt2l(lr);
				s[i][j] = lr[0];
				s[i][j+1] = lr[1];
			}
		}
	}
	/* Constructor */


	
	

	/* Utility */
	protected long f(long x) {
		long h = s[0][(int) (x >> 24)] + s[1][(int) (x >> 16 & 0xffL)];
		return ((h ^ s[2][(int) (x >> 8 & 0xffL)]) & m) + s[3][(int) (x & 0xffL)];
	}

	protected void swap(long[] lr) {
		long l = lr[0];
		lr[0] = lr[1];
		lr[1] = l;
	}
	/* Utility */


	
	

	/* Encrypt */
	protected void encrypt2l(long[] lr) {
		for (int i = 0; i < 16; i += 2) {
			lr[0] = (lr[0] ^ p[i]) & m;
			lr[1] = (lr[1] ^ f(lr[0])) & m;
			lr[1] = (lr[1] ^ p[i+1]) & m;
			lr[0] = (lr[0] ^ f(lr[1])) & m;
		}
		lr[0] = (lr[0] ^ p[16]) & m;
		lr[1] = (lr[1] ^ p[17]) & m;
		swap(lr);
	}

	protected byte[] encrypt8b(byte[] bytes) {
		long[] x = btl(bytes);
		encrypt2l(x);
		return ltb(x);
	}

	@Override
	public byte[] encrypt(byte[] data) {
		byte[][] x = split(data);
		for (int i = 0; i < x.length; i++)
			x[i] = encrypt8b(x[i]);
		return join(x);
	}
	/* Encrypt */


	
	

	/* Decrypt */
	protected void decrypt2l(long[] lr) {
		for (int i = 16; i > 0; i -= 2) {
			lr[0] = (lr[0] ^ p[i+1]) & m;
			lr[1] = (lr[1] ^ f(lr[0])) & m;
			lr[1] = (lr[1] ^ p[i]) & m;
			lr[0] = (lr[0] ^ f(lr[1])) & m;
		}
		lr[0] = (lr[0] ^ p[1]) & m;
		lr[1] = (lr[1] ^ p[0]) & m;
		swap(lr);
	}

	protected byte[] decrypt8b(byte[] bytes) {
		long[] x = btl(bytes);
		decrypt2l(x);
		return ltb(x);
	}

	@Override
	public byte[] decrypt(byte[] data) {
		byte[][] x = split(data);
		for (int i = 0; i < x.length; i++)
			x[i] = decrypt8b(x[i]);
		return join(x);
	}
	/* Decrypt */


	
	

	/* Conversion */
	protected long[] btl(byte[] x) {
		long[] y = new long[2];
		y[0] = (Byte.toUnsignedLong(x[0]) & 0xffL) << 24
				| (Byte.toUnsignedLong(x[1]) & 0xffL) << 16
				| (Byte.toUnsignedLong(x[2]) & 0xffL) << 8
				| (Byte.toUnsignedLong(x[3]) & 0xffL);
		y[1] = (Byte.toUnsignedLong(x[4]) & 0xffL) << 24
				| (Byte.toUnsignedLong(x[5]) & 0xffL) << 16
				| (Byte.toUnsignedLong(x[6]) & 0xffL) << 8
				| (Byte.toUnsignedLong(x[7]) & 0xffL);
		return y;
	}
	
	protected byte[] ltb(long[] x) {
		byte[] y = new byte[8];
		y[0] = (byte) ((x[0] >> 24) & 0xffL);
		y[1] = (byte) ((x[0] >> 16) & 0xffL);
		y[2] = (byte) ((x[0] >> 8) & 0xffL);
		y[3] = (byte) (x[0] & 0xffL);
		y[4] = (byte) ((x[1] >> 24) & 0xffL);
		y[5] = (byte) ((x[1] >> 16) & 0xffL);
		y[6] = (byte) ((x[1] >> 8) & 0xffL);
		y[7] = (byte) (x[1] & 0xffL);
		return y;
	}
	
	protected byte[][] split(byte[] x) {
		byte[][] y = new byte[(int) Math.ceil((double)x.length / (double)8)][8];
		for (int i = 0; i < x.length; i++)
			y[Math.floorDiv(i, 8)][i % 8] = x[i];
		return y;
	}
	
	protected byte[] join(byte[][] x) {
		int cutOff = 0;
		for (int i = 0; i < 8; i++) {
			if (x[x.length - 1][i] == 0) {
				cutOff = 8 - i;
				break;
			}
		}
		byte[] y = new byte[(x.length * 8) - cutOff];
		for (int i = 0; i < y.length; i++)
			y[i] = x[Math.floorDiv(i, 8)][i % 8];
		return y;
	}
	/* Conversion */
	
}
