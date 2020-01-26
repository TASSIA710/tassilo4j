package de.tassilo.crypt.blowfish;

public interface Blowfish {
	
	byte[] encrypt(byte[] data);
	byte[] decrypt(byte[] data);
	
}