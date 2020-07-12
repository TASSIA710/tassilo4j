package net.tassia;

import java.io.PrintStream;

public class Tassilo4J {

	public static void dumpHex(byte[] array) {
		dumpHex(array, System.out);
	}

	public static void dumpHex(byte[] array, PrintStream out) {
		for (byte b : array) {
			out.print(String.format("%02X", b));
			out.print(" ");
		}
		out.println();
	}

}
