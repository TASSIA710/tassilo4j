package net.tassia.crypto;

import net.tassia.Tassilo4J;
import net.tassia.util.Arrays;

public class SHA1 implements HashFunction {
	private int[] h = new int[5];

	public SHA1() {
		reset();
	}

	private void reset() {
		h[0] = 0x67452301;
		h[1] = 0xEFCDAB89;
		h[2] = 0x98BADCFE;
		h[3] = 0x10325476;
		h[4] = 0xC3D2E1F0;
	}

	private void digestChunk(byte[] chunk) {
		int[] w = new int[80];
		for (int i = 0; i < 16; i++) {
			w[i] = 0;
			w[i] |= chunk[i*4 + 0];
			w[i] <<= 8;
			w[i] |= chunk[i*4 + 1];
			w[i] <<= 8;
			w[i] |= chunk[i*4 + 2];
			w[i] <<= 8;
			w[i] |= chunk[i*4 + 3];
		}

		// Message schedule: extend the sixteen 32-bit words into eighty 32-bit words:
		for (int i = 16; i < 80; i++) {
			w[i] = Integer.rotateLeft(w[i - 3] ^ w[i - 8] ^ w[i - 14] ^ w[i - 16], 1);
		}

		// Initialize hash value for this chunk
		int a = h[0];
		int b = h[1];
		int c = h[2];
		int d = h[3];
		int e = h[4];

		// Main loop
		for (int i = 0; i < 80; i++) {
			int f, k;

			if (0 <= i && i <= 19) {
				f = (b & c) | ((~b) & d);
				k = 0x5A827999;
			} else if (20 <= i && i <= 39) {
				f = b ^ c ^ d;
				k = 0x6ED9EBA1;
			} else if (40 <= i && i <= 59) {
				f = (b & c) | (b & d) | (c & d);
				k = 0x8F1BBCDC;
			} else {
				f = b ^ c ^ d;
				k = 0xCA62C1D6;
			}

			int temp = Integer.rotateLeft(a, 5) + f + e + k + w[i];
			e = d;
			d = c;
			c = Integer.rotateLeft(b, 30);
			b = a;
			a = temp;
		}

		// Add this chunk's hash to result so far
		h[0] = h[0] + a;
		h[1] = h[1] + b;
		h[2] = h[2] + c;
		h[3] = h[3] + d;
		h[4] = h[4] + e;
	}

	@Override
	public byte[] hash(byte[] data) {
		int len = data.length;

		// Prepare message
		int dataLen = data.length + 8 + 1; // 8 = message length at the end, 1 = one 0x80 appended because SHA1 is a bitch
		dataLen = dataLen + ((512 / 8) - dataLen % (512 / 8)); // Make message a multiple of 512 bits
		byte[] data2 = new byte[dataLen];
		for (int i = 0; i < data.length; i++) {
			data2[i] = data[i];
		}
		data2[data.length] = (byte) 0x80;

		// Add message length to data
		data2[data2.length - 1] = (byte) ((data.length >>  0) & 0xFF);
		data2[data2.length - 2] = (byte) ((data.length >>  8) & 0xFF);
		data2[data2.length - 3] = (byte) ((data.length >> 16) & 0xFF);
		data2[data2.length - 4] = (byte) ((data.length >> 24) & 0xFF);

		Tassilo4J.dumpHex(data);
		Tassilo4J.dumpHex(data2);

		// Break data into chunks
		byte[][] chunks = Arrays.splitArrayIntoChunks(data, (512 / 8));

		// Digest chunks
		for (byte[] chunk : chunks) {
			digestChunk(chunk);
		}

		// Produce final hash
		byte[] hash = new byte[h.length * 4];
		for (int i = 0; i < h.length; i++) {
			hash[i * 4 + 0] = (byte) ((h[i] >> 24) & 0xFF);
			hash[i * 4 + 1] = (byte) ((h[i] >> 16) & 0xFF);
			hash[i * 4 + 2] = (byte) ((h[i] >>  8) & 0xFF);
			hash[i * 4 + 3] = (byte) ((h[i] >>  0) & 0xFF);
		}
		return hash;
	}

}
