package com.ticomgeo.mstricklin;

import com.google.common.graph.Graph;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.function.Supplier;

/**
 * @author mstricklin
 * @version 1.0
 */

// TODO: rename
public class Builder {


	@SuppressWarnings("unused")
	private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

	@SuppressWarnings("unused")
	private static final String NEWLINE = System.getProperty("line.separator");


	static class Starter<T> {
		private final Supplier<T> supplier;

		private Starter(Supplier<T> supplier) {
			this.supplier = supplier;
		}
		public static <T> Starter startWith(Supplier<T> supplier) {
			return new Starter<T>(supplier);
		}
		public Intermediate andThen() {
			return new Intermediate();
		}
		public Final endWith() {
			return new Final();
		}
	}
	static class Intermediate {
		public Intermediate andThen() {
			return this;
		}
		public Final endWith() {
			return new Final();
		}
	}
	static class Final {
		public GraphRepresentation build() {
			return null;
		}
	}

	Node frontier;

}