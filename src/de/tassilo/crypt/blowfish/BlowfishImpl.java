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
			encrypt64bit(lr);
			p[i] = lr[0];
			p[i+1] = lr[1];
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 256; j += 2) {
				encrypt64bit(lr);
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
	public void encrypt64bit(long[] lr) {
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

	public void encrypt64bit(byte[] bytes) {
		long[] x = new long[2];
		x[0] = BytesToUInt(bytes[0], bytes[1], bytes[2], bytes[3]);
		x[1] = BytesToUInt(bytes[4], bytes[5], bytes[6], bytes[7]);
		encrypt64bit(x);
		byte[] d = UIntToBytes(x[0]);
		bytes[0] = d[0];
		bytes[1] = d[1];
		bytes[2] = d[2];
		bytes[3] = d[3];
		d = UIntToBytes(x[1]);
		bytes[4] = d[0];
		bytes[5] = d[1];
		bytes[6] = d[2];
		bytes[7] = d[3];
	}

	@Override
	public byte[] encrypt(byte[] data) {
		return null;
	}
	/* Encrypt */



	/* Decrypt */
	public void decrypt64bit(long[] lr) {
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

	public void decrypt64bit(byte[] bytes) {
		long[] x = new long[2];
		x[0] = BytesToUInt(bytes[0], bytes[1], bytes[2], bytes[3]);
		x[1] = BytesToUInt(bytes[4], bytes[5], bytes[6], bytes[7]);
		decrypt64bit(x);
		byte[] d = UIntToBytes(x[0]);
		bytes[0] = d[0];
		bytes[1] = d[1];
		bytes[2] = d[2];
		bytes[3] = d[3];
		d = UIntToBytes(x[1]);
		bytes[4] = d[0];
		bytes[5] = d[1];
		bytes[6] = d[2];
		bytes[7] = d[3];
	}

	@Override
	public byte[] decrypt(byte[] data) {
		return null;
	}
	/* Decrypt */



	/* Conversion */
	private static final long btl(byte x) {
		return ((long)x) & 0x00000000000000FFL;
	}

	protected long BytesToUInt(byte b0, byte b1, byte b2, byte b3) {
		return btl(b0) | (btl(b1) << 8) | (btl(b2) << 16) | (btl(b3) << 24);
	}

	protected byte[] UIntToBytes(long uint) {
		byte[] data = new byte[4];
	    data[3] = (byte) uint;
	    data[2] = (byte) (uint >>> 8);
	    data[1] = (byte) (uint >>> 16);
	    data[0] = (byte) (uint >>> 32);
		return data;
	}
	/* Conversion */

}
