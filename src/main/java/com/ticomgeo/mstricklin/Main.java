
package com.ticomgeo.mstricklin;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author mstricklin
 * @version 1.0
 */
public class Main {

	@SuppressWarnings("unused")
	private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

	@SuppressWarnings("unused")
	private static final String NEWLINE = System.getProperty("line.separator");

	public static void main(String[] args) {
		CLASS_LOGGER.info("Hello world");
		Builder.Graph g0 = new Builder.Graph("Graph 0");
		Builder b0 = Builder.on(g0);
		Builder b1 = Builder.on(g0);

		b0.startWith(new AProducer("begin0"));
		b1.startWith(new AProducer("begin1"));

		b0.andThen(new AProcessor("proc0a"));
		b1.andThen(new AProcessor("proc1a"));

		// TODO: b0 can't be at a producer
		b0.join(b1);



//		b0.andThen(new Proc0("proc0a"));
//		b1.andThen(new Proc0("proc0a"));
//
//		b0.andThen(new Proc0("proc0b"));
//		b0.andThen(new Proc0("proc0c"));
//		b0.lastly(new Cons0("cons0"));
		CLASS_LOGGER.info("{}", g0);
	}

	static class AProducer  extends AbstractNode implements Producer {
		AProducer(String id) {
			super(id);
		}
	}
	static class AProcessor  extends AbstractNode implements Processor {
		AProcessor(String id) {
			super(id);
		}
	}
	static class AConsumer  extends AbstractNode implements Consumer {
		AConsumer(String id) {
			super(id);
		}
	}

}
