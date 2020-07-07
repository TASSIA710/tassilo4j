package net.tassia.io;

import java.io.IOException;

@FunctionalInterface
public interface DataOutput<T extends Object> {

	void writeOutput(DataOutputStream out, T output) throws IOException;

}
