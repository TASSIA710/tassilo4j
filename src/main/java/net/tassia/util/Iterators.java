package net.tassia.util;

import java.util.Iterator;
import java.util.function.Function;

/**
 * This utility class allows various operations with iterators.
 * @author Tassilo
 * @since Tassilo4J 2.0
 */
public class Iterators {

	/**
	 * This constructor is private as no instance of this class is needed.
	 */
	private Iterators() {
	}


	/**
	 * Converts an iterator object from one class to another one.
	 * @param source the source iterator
	 * @param converter the function used to convert the objects
	 * @return a new iterator spitting out converted objects
	 */
	public static <A, B> Iterator<B> convert(Iterator<A> source, Function<A, B> converter) {
		return new Iterator<B>() {
			@Override
			public boolean hasNext() {
				return source.hasNext();
			}
			@Override
			public B next() {
				return converter.apply(source.next());
			}
		};
	}



	/**
	 * Converts an iterable object from one class to another one.
	 * @param source the source iterable
	 * @param converter the function used to convert the objects
	 * @return a new iterable spitting out converted objects
	 */
	public static <A, B> Iterable<B> convert(Iterable<A> source, Function<A, B> converter) {
		return () -> convert(source.iterator(), converter);
	}

}