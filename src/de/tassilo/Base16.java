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
	/**
	 * This constructor is private as no instance of this class is needed.
	 */
	private Base16() {
	}
	/* Private Constructor */





	/* Generic */
	/**
	 * Returns the uppercase translation table for Base16.
	 * Contains 16 elements: 0-9 and A-F.
	 * @return the translation table
	 */
	public static final char[] getUppercaseTable() {
		return "0123456789ABCDEF".toCharArray();
	}

	/**
	 * Returns the lowercase translation table for Base16.
	 * Contains 16 elements: 0-9 and a-f.
	 * @return the translation table
	 */
	public static final char[] getLowercaseTable() {
		return "0123456789abcdef".toCharArray();
	}

	/**
	 * Returns the default translation table for Base16 which is the uppercase table.
	 * @return the default translation table
	 * @see #getUppercaseTable()
	 */
	public static final char[] getDefaultTable() {
		return getUppercaseTable();
	}
	/* Generic */





	/* Encoding */
	/**
	 * Encodes the string to bytes using the UTF-8 charset and then encodes the bytes to Base16.
	 * @param str the string to encode
	 * @return the encoded Base16 string
	 */
	public static final String encode(String str) {
		return encode(str, StandardCharsets.UTF_8);
	}

	/**
	 * Encodes the string to bytes using the given charset and then encodes the bytes to Base16.
	 * @param str the string to encode
	 * @param ch the charset to use
	 * @return the encoded Base16 string
	 */
	public static final String encode(String str, Charset ch) {
		return encode(str.getBytes(ch));
	}

	/**
	 * Encodes the given byte array to Base16.
	 * @param data the bytes to encode
	 * @return the encoded Base16 string with a length of <pre>size(data)* 2</pre>
	 */
	public static final String encode(byte[] data) {
		StringBuilder sb = new StringBuilder((int)Math.ceil(data.length / 3) * 4);
		char[] table = getDefaultTable();
		encode(sb, data, table);
		return sb.toString();
	}

	/**
	 * Encodes the given byte array and builds the string using the StringBuilder.
	 * @param sb the StringBuilder to use
	 * @param data the bytes to encode
	 * @param table the translation table to use
	 */
	private static final void encode(StringBuilder sb, byte[] data, char[] table) {
		for (int i = 0; i < data.length; i++) {
			encodeElement(sb, ((int) data[i]) & 0xff, table);
		}
	}

	/**
	 * Encodes the given byte to 2 chars and appends them to the StringBuilder.
	 * @param sb the StringBuilder to append the chars to
	 * @param a the byte (as int)
	 * @param table the translation table to use
	 */
	private static final void encodeElement(StringBuilder sb, int x, char[] table) {
		final int M = 0b00000000000000000000000000001111;
		int a = (x >> 4) & M;
		int b = x & M;
		sb.append(table[a]).append(table[b]);
	}
	/* Encoding */





	/* Decoding */
	/**
	 *  Decodes the given Base64 and converts the bytes into a new string using the UTF-8 charset.
	 * @param str the string to decode
	 * @return the decoded string
	 * @throws IllegalArgumentException if the string contains non-Base16 characters
	 */
	public static final String decodeToString(String str) throws IllegalArgumentException {
		return decodeToString(str, StandardCharsets.UTF_8);
	}

	/**
	 * Decodes the given Base64 and converts the bytes into a new string using the given charset.
	 * @param str the string to decode
	 * @param ch the charset to use to create the new string
	 * @return the decoded string
	 * @throws IllegalArgumentException if the string contains non-Base16 characters
	 */
	public static final String decodeToString(String str, Charset ch) throws IllegalArgumentException {
		return new String(decode(str), ch);
	}

	/**
	 * Decodes the given Base16 string into its raw bytes.
	 * @param str the string to decode
	 * @return the raw byte data
	 * @throws IllegalArgumentException if the string contains non-Base16 characters
	 */
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

	/**
	 * Decodes the two given chars to a single byte and appends it to the data buffer.
	 * @param buffer the data buffer
	 * @param offset the offset for the buffer
	 * @param a the first char
	 * @param b the second char
	 * @param table the translation table to use
	 * @throws IllegalArgumentException if one of the characters is not a Base16 character
	 */
	private static final void decodeElement(byte[] buffer, int offset, char a, char b, char[] table) throws IllegalArgumentException {
		byte A = decodeCharacter(a, table);
		byte B = decodeCharacter(b, table);
		buffer[offset] = (byte) ((A << 4 & 0b11110000) | (B & 0b00001111));
	}

	/**
	 * Decodes a single character to its representative byte (only the last 4 bits are relevant!)
	 * @param x the character to decode
	 * @param table the translation table to use
	 * @return the representative byte
	 * @throws IllegalArgumentException if the given character is not a Base16 character
	 */
	private static final byte decodeCharacter(char x, char[] table) throws IllegalArgumentException {
		byte b = (byte) new String(table).indexOf(x);
		if (b == -1) throw new IllegalArgumentException("'" + x + "' is not a valid Base16 character");
		return b;
	}
	/* Decoding */

}
