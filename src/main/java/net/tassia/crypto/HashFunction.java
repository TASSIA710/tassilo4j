package net.tassia.crypto;

import net.tassia.util.Base16;

import java.nio.charset.StandardCharsets;

public interface HashFunction {

	byte[] hash(byte[] hash);

	default byte[] hash(String hash) {
		return hash(hash.getBytes(StandardCharsets.UTF_8));
	}

	default String hashToHex(byte[] hash) {
		return Base16.encode(hash(hash));
	}

	default String hashToHex(String hash) {
		return Base16.encode(hash(hash));
	}

}
