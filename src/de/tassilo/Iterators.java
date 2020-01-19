package de.tassilo;

import java.util.Iterator;
import java.util.function.Function;

public class Iterators {
	
	private Iterators() {
	}
	
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
	
	public static <A, B> Iterable<B> convert(Iterable<A> source, Function<A, B> converter) {
		return new Iterable<B>() {
			@Override
			public Iterator<B> iterator() {
				return convert(source.iterator(), converter);
			}
		};
	}
	
}