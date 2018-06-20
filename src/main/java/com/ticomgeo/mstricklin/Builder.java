package com.ticomgeo.mstricklin;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author mstricklin
 * @version 1.0
 */

// TODO: rename
public class Builder {
	static class Graph {
		public final String name;
		Graph(String name) {
			this.name = name;
		}
		MutableGraph<Node> graph = GraphBuilder.directed().build();
		@Override
		public String toString() {
			return graph.toString();
		}

	}

	@SuppressWarnings("unused")
	private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

	@SuppressWarnings("unused")
	private static final String NEWLINE = System.getProperty("line.separator");



	Node frontier;

	private Builder(Graph g) {
		this.g = g;
	}
	private Builder extend(Node n) {
		g.graph.addNode(n);
		g.graph.putEdge(frontier, n);
		frontier = n;
		return this;
	}
	final Graph g;
	static Builder on(Graph g) {
		return new Builder(g);
	}
	Builder startWith(Producer p0) {
		this.g.graph.addNode(p0);
		frontier = p0;
		return this;
	}

	Builder andThen(Processor p) {
		return extend(p);
	}

	Builder fork(Processor p0,
	             Processor... p) {
		return this;
	}

	Builder join(Builder b0,
	                     Builder... b) {
		g.graph.putEdge(b0.frontier, frontier);
		b0.frontier = frontier;
		return this;
	}

	Builder lastly(Consumer c) {
		return extend(c);
	}

}