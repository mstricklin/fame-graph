package com.ticomgeo.mstricklin;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Collection;

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

		GraphRepresentation gr = new GraphRepresentation("trance a");
		gr.assembler(new AProducer("0a"))
				.andThen(new AProcessor("a"))
						.andThen(new AProcessor("a"))
						.andThen(new BProcessor("a"))
						.endWith(new AConsumer("a"))
				.assemble();



		System.out.println(gr.graphviz());

		//		GraphRepresentation.Assembler as0 = GraphRepresentation.assembler(new AProducer("0a"))
		//				.andThen(new AProcessor("a"))
		//				.andThen(new AProcessor("a"))
		//				.andThen(new BProcessor("a"))
		//				.endWith(new AConsumer("a"));
		//
		//		System.out.println(as0.graphviz());
		////		AConsumer ac = new AConsumer("c");
		////		gr.startWith(new AProducer("c"))
		////				.fanOut(new AProcessor("c"), new AProcessor("c"))
		////				.stream().forEach(p -> p.endWith(new AConsumer("c")));
		////
		//		CLASS_LOGGER.info("tranche e");
		//		GraphRepresentation.Assembler s1 = GraphRepresentation.assembler(new AProducer("e"));
		//		Collection<GraphRepresentation.Assembler> as1= s1.andThen(newArrayList(new AProcessor("e"), new BProcessor("e")));
		////		ConsumerNode cn = new AConsumer("e");
		////		as1.stream().forEach(p -> p.endWith(cn));
		//		GraphRepresentation gr0 = s1.assemble();
		//
		//		System.out.println(gr0.graphviz());
		//
		//
		//		CLASS_LOGGER.info("tranche f");
		//		AProducer aProd = new AProducer("f");
		//		GraphRepresentation.Builder mb = GraphRepresentation.startWith(aProd);
		//		Node aProc = mb.addNode(new AProcessor("f"));
		//		mb.addEdge(aProd, aProc);
		//		Node aCons = mb.addNode(new AConsumer("f"));
		//		mb.addEdge(aProc, aCons);

		//		System.out.println(mb.graphviz());


		// scenarios:
		// find an intermediate by id

		// X fan from one producer to multiple processors
		// fan from one processor to multiple consumers
		// X fan from one processor to multiple processors
		// fan from one producer to multiple consumers

		// join from multiple producers to one consumer
		// join from multiple producers to one processor
		// join from multiple processors to one consumer

		// X generate GraphViz
		// create a collector?

		//		gr.dump();


		//		b0.andThen(new Proc0("proc0a"));
		//		b1.andThen(new Proc0("proc0a"));
		//
		//		b0.andThen(new Proc0("proc0b"));
		//		b0.andThen(new Proc0("proc0c"));
		//		b0.lastly(new Cons0("cons0"));
		//		CLASS_LOGGER.info("{}", g0);
	}

	static int CNT = 0;

	static class AProducer extends AbstractNode implements ProducerNode {
		AProducer(String id) {
			super(id + CNT++);
		}

		@Override
		public String get() {
			return null;
		}
	}

	static class AProcessor extends AbstractNode implements ProcessorNode {
		AProcessor(String id) {
			super(id + CNT++);
		}

		@Override
		public String apply(String s) {
			return null;
		}
	}

	static class BProcessor extends AbstractNode implements ProcessorNode {
		BProcessor(String id) {
			super(id + CNT++);
		}

		@Override
		public String apply(String s) {
			return null;
		}
	}

	static class AConsumer extends AbstractNode implements ConsumerNode {
		AConsumer(String id) {
			super(id + CNT++);
		}

		@Override
		public void accept(String s) {

		}
	}

}
