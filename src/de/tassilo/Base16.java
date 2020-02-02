package de.tassilo;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * This class can be used to encode and decode Base16 data.
 * @author Tassilo
 * @since Tassilo4J 1.0
 */
public final class Base16 {

	/* Private Constructor */
	private Base16() {
	}
	/* Private Constructor */





	/* Generic */
	public static final char[] getUppercaseTable() {
		return "0123456789ABCDEF".toCharArray();
	}

	public static final char[] getLowercaseTable() {
		return "0123456789abcdef".toCharArray();
	}

	public static final char[] getDefaultTable() {
		return getUppercaseTable();
	}
	/* Generic */





	/* Encoding */
	public static final String encode(String str) {
		return encode(str, StandardCharsets.UTF_8);
	}

	public static final String encode(String str, Charset ch) {
		return encode(str.getBytes(ch));
	}

	public static final String encode(byte[] data) {
		StringBuilder sb = new StringBuilder((int)Math.ceil(data.length / 3) * 4);
		char[] table = getDefaultTable();
		encode(sb, data, table);
		return sb.toString();
	}

	private static final void encode(StringBuilder sb, byte[] data, char[] table) {
		for (int i = 0; i < data.length; i++) {
			encodeElement(sb, ((int) data[i]) & 0xff, table);
		}
	}

	private static final void encodeElement(StringBuilder sb, int x, char[] table) {
		final int M = 0b00000000000000000000000000001111;
		int a = (x >> 4) & M;
		int b = x & M;
		sb.append(table[a]).append(table[b]);
	}
	/* Encoding */





	/* Decoding */
	public static final String decodeToString(String str) throws IllegalArgumentException {
		return decodeToString(str, StandardCharsets.UTF_8);
	}

	public static final String decodeToString(String str, Charset ch) throws IllegalArgumentException {
		return new String(decode(str), ch);
	}

	public static final byte[] decode(String str) throws IllegalArgumentException {
		char[] table = getDefaultTable();
		while (str.length() % 2 != 0) throw new IllegalArgumentException("string length is not a multiple of two");
		byte[] data = new byte[str.length() / 2];
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length/2; i++) {
			decodeElement(data, i, chars[i*2], chars[i*2+1], table);
		}
		return data;
	}

	private static final void decodeElement(byte[] buffer, int offset, char a, char b, char[] table) throws IllegalArgumentException {
		byte A = decodeCharacter(a, table);
		byte B = decodeCharacter(b, table);
		buffer[offset] = (byte) ((A << 4 & 0b11110000) | (B & 0b00001111));
	}

	private static final byte decodeCharacter(char x, char[] table) throws IllegalArgumentException {
		byte b = (byte) new String(table).indexOf(x);
		if (b == -1) throw new IllegalArgumentException("'" + x + "' is not a valid Base16 character");
		return b;
	}
	/* Decoding */

}
