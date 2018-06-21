package com.ticomgeo.mstricklin;

import com.google.common.collect.Iterables;
import com.google.common.graph.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class GraphRepresentation {
	@SuppressWarnings("unused")
	private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

	@SuppressWarnings("unused")
	private static final String NEWLINE = System.getProperty("line.separator");
	public final String id;
	Graph<Node> graph;


	public GraphRepresentation(String id) {
		this.id = id;
		graph = GraphBuilder.directed().allowsSelfLoops(false).build();
	}
	private GraphRepresentation(Graph g) {
		this.id = "aaa";
		this.graph = g;
	}


	@Override
	public String toString() {
		return graph.toString();
	}

	public void dump() {
		for (EndpointPair<Node> ep : graph.edges()) {
			CLASS_LOGGER.info("{} -> {}", ep.source(), ep.target());
		}
	}

	public String graphviz() {
		// house, invhouse, box
		// node1 [shape=box]
		// node2 [fillcolor=yellow, style="rounded,filled", shape=diamond]
		StringBuilder sb = new StringBuilder("digraph {\n");
		graph.nodes().stream().map(Node::graphviz).forEach(s -> sb.append(s).append('\n'));
		graph.edges().stream().forEach(ep -> sb.append(String.format("\t\"%s\" -> \"%s\"\n", ep.source(), ep.target())));
		return sb.append("}\n").toString();
	}

	private static String graphviz(Graph<Node> g) {
		// house, invhouse, box
		// node1 [shape=box]
		// node2 [fillcolor=yellow, style="rounded,filled", shape=diamond]
		StringBuilder sb = new StringBuilder("digraph {\n");
		g.nodes().stream().map(Node::graphviz).forEach(s -> sb.append(s).append('\n'));
		g.edges().stream().forEach(ep -> sb.append(String.format("\t\"%s\" -> \"%s\"\n", ep.source(), ep.target())));
		return sb.append("}\n").toString();
	}

	void addNode() {
	}

	void addEdge() {
	}

	void findBuilder() {
	}

	public Assembler assembler() {
		return new Assembler();
	}
	public Assembler assembler(ProducerNode start) {
		Assembler as = new Assembler(start);
		return as;
	}
	public Assembler collect(Collection<Assembler> assemblers, ProcessorNode pn) {
		// check not empty/null
		Assembler first = Iterables.getFirst(assemblers, null);
		Assembler newA = new Assembler(first);
		newA.frontier = pn;
		for (Assembler a: assemblers) {
			newA.graph.putEdge(a.frontier, pn);
		}
		return newA;
	}
	// ==========================================
	static class Assembler {
		private Node frontier;
		private final MutableGraph<Node> graph;
		private Assembler() {
			graph = GraphBuilder.directed().build();
		}
		private Assembler(ProducerNode producer) {
			graph = GraphBuilder.directed().build();
			frontier = producer;
		}
		private Assembler(Assembler canonical) {
			graph = canonical.graph;
		}
		private Assembler(Assembler from, Node next) {
			graph = from.graph;
			graph.addNode(next);
			graph.putEdge(from.frontier, next);
			frontier = next;
		}

//		private Assembler(ProducerNode producer) {
//			this.frontier = producer;
//		}

//		private Assembler(Node prior,
//		                  Node next) {
//			GraphRepresentation.this.graph.addNode(next);
//			GraphRepresentation.this.graph.putEdge(prior, next);
//			frontier = next;
//		}

		public Assembler startWith(ProducerNode producer) {
			Assembler as = new Assembler(this);
			frontier = producer;
			return as;
		}

		public Assembler andThen(ProcessorNode processor) {
			return new Assembler(this, processor);
		}

		public Collection<Assembler> andThen(Collection<ProcessorNode> processors) {
			return processors.stream().map(p -> new Assembler(this, p)).collect(Collectors.toList());
		}

		public Assembler endWith(ConsumerNode consumer) {
			return new Assembler(this, consumer);
		}

		public GraphRepresentation assemble() {
			return new GraphRepresentation(graph);
		}

		//	    static public Assembler endWith(Collection<ProcessorNode> pn, ConsumerNode consumer) {
		//		    return new Assembler(frontier, consumer);
		//	    }
		public String graphviz() {
			return GraphRepresentation.graphviz(graph);
		}
	}

	public static Builder startWith(ProducerNode producer) {
		return new Builder(producer);
	}

	static class Builder {
		MutableGraph<Node> graph = GraphBuilder.directed().build();
		private final Node frontier;

		private Builder(ProducerNode p) {
			this.frontier = p;
			graph.addNode(p);
		}

		public Node addNode(Node n) {
			graph.addNode(n);
			return n;
		}

		public void addEdge(Node from,
		                    Node to) {
			graph.putEdge(from, to);
		}

		public String graphviz() {
			return GraphRepresentation.graphviz(graph);
		}

	}

}
