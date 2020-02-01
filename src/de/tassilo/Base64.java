package de.tassilo;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * This class can be used to encode and decode Base64 data.
 * @author Tassilo
 * @since Tassilo4J 1.0
 */
public final class Base64 {

	/**
	 * The translation table for Base64.
	 * Contains 64 elements: A-Z, a-z, 0-9, '+' and '/'.
	 */
	private static final char[] TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	/* Private Constructor */
	/**
	 * This constructor is private as no instance of this class is needed.
	 */
	private Base64() {
	}
	/* Private Constructor */





	/* Encoding */
	/**
	 * Encodes the string to bytes using the UTF-8 charset and then encodes the bytes to Base64.
	 * @param str the string to encode
	 * @return the encoded Base64 string
	 */
	public static final String encode(String str) {
		return encode(str, StandardCharsets.UTF_8);
	}

	/**
	 * Encodes the string to bytes using the given charset and then encodes the bytes to Base64.
	 * @param str the string to encode
	 * @param ch the charset to use
	 * @return the encoded Base64 string
	 */
	public static final String encode(String str, Charset ch) {
		return encode(str.getBytes(ch));
	}

	/**
	 * Encodes the given byte array to Base64.
	 * @param data the bytes to encode
	 * @return the encoded Base64 string with a length of <pre>ceil( size(data) / 3 ) * 4</pre>
	 */
	public static final String encode(byte[] data) {
		StringBuilder sb = new StringBuilder((int)Math.ceil(data.length / 3) * 4);
		encode(sb, data);
		return sb.toString();
	}

	/**
	 * Encodes the given byte array and builds the string using the StringBuilder.
	 * @param sb the StringBuilder to use
	 * @param data the bytes to encode
	 */
	private static final void encode(StringBuilder sb, byte[] data) {
		for (int i = 0; i < data.length; i += 3) {
			byte a = (i) < data.length ? data[i] : 0;
			byte b = (i+1) < data.length ? data[i+1] : 0;
			byte c = (i+2) < data.length ? data[i+2] : 0;
			encodeElement(sb, ((int) a) & 0xff, ((int) b) & 0xff, ((int) c) & 0xff);
			if (i + 1 >= data.length) sb.setCharAt(sb.length() - 2, '=');
			if (i + 2 >= data.length) sb.setCharAt(sb.length() - 1, '=');
		}
	}

	/**
	 * Encodes the given byte triplet to 4 chars and appends them to the StringBuilder.
	 * @param sb the StringBuilder to append the chars to
	 * @param a the first byte (as int)
	 * @param b the second byte (as int)
	 * @param c the third byte (as int)
	 */
	private static final void encodeElement(StringBuilder sb, int a, int b, int c) {
		final int M = 0b00000000000000000000000000111111;
		int A = (a >> 2) & M;
		int B = ((a << 4 & 0b00110000) | (b >> 4 & 0b00001111)) & M;
		int C = ((b << 2 & 0b00111100) | (c >> 6 & 0b00000011)) & M;
		int D = (c & 0b00111111) & M;
		sb.append(TABLE[A]).append(TABLE[B]).append(TABLE[C]).append(TABLE[D]);
	}
	/* Encoding */





	/* Decoding */
	/**
	 *  Decodes the given Base64 and converts the bytes into a new string using the UTF-8 charset.
	 * @param str the string to decode
	 * @return the decoded string
	 * @throws IllegalArgumentException if the string is not a multiple of 4, or contains non-Base64 characters
	 */
	public static final String decodeToString(String str) throws IllegalArgumentException {
		return decodeToString(str, StandardCharsets.UTF_8);
	}

	/**
	 * Decodes the given Base64 and converts the bytes into a new string using the given charset.
	 * @param str the string to decode
	 * @param ch the charset to use to create the new string
	 * @return the decoded string
	 * @throws IllegalArgumentException if the string is not a multiple of 4, or contains non-Base64 characters
	 */
	public static final String decodeToString(String str, Charset ch) throws IllegalArgumentException {
		return new String(decode(str), ch);
	}

	/**
	 * Decodes the given Base64 string into its raw bytes.
	 * @param str the string to decode
	 * @return the raw byte data
	 * @throws IllegalArgumentException if the string is not a multiple of 4, or contains non-Base64 characters
	 */
	public static final byte[] decode(String str) throws IllegalArgumentException {
		if (str.length() % 4 != 0) throw new IllegalArgumentException("not a base64 string (length mod 4 != 0)");
		byte[] data = new byte[(str.length() / 4) * 3];
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length/4; i++) {
			decodeElement(data, i*3, chars[i*4], chars[i*4+1], chars[i*4+2], chars[i*4+3]);
		}
		return data;
	}

	/**
	 * Decodes the four given chars to three bytes and appends them to the data buffer.
	 * @param buffer the data buffer
	 * @param offset the offset for the buffer
	 * @param a the first char
	 * @param b the second char
	 * @param c the third char
	 * @param d the fourth char
	 * @throws IllegalArgumentException if one of the characters is not a Base64 character
	 */
	private static final void decodeElement(byte[] buffer, int offset, char a, char b, char c, char d) throws IllegalArgumentException {
		byte A = decodeCharacter(a);
		byte B = decodeCharacter(b);
		byte C = decodeCharacter(c);
		byte D = decodeCharacter(d);
		buffer[offset+0] = (byte) ((A << 2 & 0b11111100) | (B >> 4 & 0b00000011));
		buffer[offset+1] = (byte) ((B << 4 & 0b11110000) | (C >> 2 & 0b00001111));
		buffer[offset+2] = (byte) ((C << 6 & 0b11000000) | (D & 0b00111111));
	}

	/**
	 * Decodes a single character to its representative byte (only the last 6 bits are relevant!)
	 * @param x the character to decode
	 * @return the representative byte
	 * @throws IllegalArgumentException if the given character is not a Base64 character
	 */
	private static final byte decodeCharacter(char x) throws IllegalArgumentException {
		switch (x) {
			case 'A': return 0;
			case 'B': return 1;
			case 'C': return 2;
			case 'D': return 3;
			case 'E': return 4;
			case 'F': return 5;
			case 'G': return 6;
			case 'H': return 7;
			case 'I': return 8;
			case 'J': return 9;
			case 'K': return 10;
			case 'L': return 11;
			case 'M': return 12;
			case 'N': return 13;
			case 'O': return 14;
			case 'P': return 15;
			case 'Q': return 16;
			case 'R': return 17;
			case 'S': return 18;
			case 'T': return 19;
			case 'U': return 20;
			case 'V': return 21;
			case 'W': return 22;
			case 'X': return 23;
			case 'Y': return 24;
			case 'Z': return 25;
			case 'a': return 26;
			case 'b': return 27;
			case 'c': return 28;
			case 'd': return 29;
			case 'e': return 30;
			case 'f': return 31;
			case 'g': return 32;
			case 'h': return 33;
			case 'i': return 34;
			case 'j': return 35;
			case 'k': return 36;
			case 'l': return 37;
			case 'm': return 38;
			case 'n': return 39;
			case 'o': return 40;
			case 'p': return 41;
			case 'q': return 42;
			case 'r': return 43;
			case 's': return 44;
			case 't': return 45;
			case 'u': return 46;
			case 'v': return 47;
			case 'w': return 48;
			case 'x': return 49;
			case 'y': return 50;
			case 'z': return 51;
			case '0': return 52;
			case '1': return 53;
			case '2': return 54;
			case '3': return 55;
			case '4': return 56;
			case '5': return 57;
			case '6': return 58;
			case '7': return 59;
			case '8': return 60;
			case '9': return 61;
			case '+': return 62;
			case '/': return 63;
			case '=': return 0;
			default: throw new IllegalArgumentException("not a base64 string (invalid character: '" + x + "'");
		}
	}
	/* Decoding */

}
