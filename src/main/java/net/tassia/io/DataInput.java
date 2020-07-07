package net.tassia.io;

import java.io.IOException;

@FunctionalInterface
public interface DataInput<T extends Object> {

	T readInput(DataInputStream in) throws IOException;

}
