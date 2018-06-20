package com.ticomgeo.mstricklin;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;

public class GraphRepresentation<T> {
    @SuppressWarnings("unused")
    private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

    @SuppressWarnings("unused")
    private static final String NEWLINE = System.getProperty("line.separator");
    public final String name;

    GraphRepresentation(String name) {
        this.name = name;
    }

    MutableGraph<Node> graph = GraphBuilder.directed().build();

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
        graph.nodes()
                .stream()
                .map(Node::graphviz)
                .forEach(s -> sb.append(s).append('\n'));
        graph.edges()
                .stream()
                .forEach(ep -> sb.append(String.format("\t\"%s\" -> \"%s\"\n", ep.source(), ep.target())));
        return sb.append("}\n").toString();
    }

    void addNode() {
    }

    void addEdge() {
    }

    void findBuilder() {
    }


    public Starter<T> startWith(ProducerNode<T> producer) {
        return new Starter<>(producer);
    }

    class Starter<T> {
        private final ProducerNode<T> producer;

        private Starter(ProducerNode<T> producer) {
            this.producer = producer;
            GraphRepresentation.this.graph.addNode(producer);
        }

        public Intermediate<T> andThen(ProcessorNode<T> processor) {
            return new Intermediate<>(producer, processor);
        }

        public Collection<Intermediate<T>> fanOut(ProcessorNode<T> p0, ProcessorNode<T>... ps) {
            return Stream.concat(Stream.of(p0), Stream.of(ps))
                    .map(p -> new Intermediate<>(producer, p))
                    .collect(Collectors.toList());
        }

        public Collection<Intermediate<T>> fanOut(Collection<ProcessorNode<T>> processors) {
            return processors.stream()
                    .map(p -> new Intermediate<>(producer, p))
                    .collect(Collectors.toList());
        }

        public Final<T> endWith(ConsumerNode<T> consumer) {
            return new Final<>(producer, consumer);
        }
//        public Collection<Final> fanOut(ConsumerNode<T> p0, ConsumerNode<T>... ps) {
//            return Stream.concat(Stream.of(p0), Stream.of(ps))
//                    .map(p -> new Final(producer, p))
//                    .collect(Collectors.toList());
//        }
//
//        public Collection<Final> fanOut(Collection<ConsumerNode<T>> processors) {
//            return processors.stream()
//                    .map(p -> new Final(producer, p))
//                    .collect(Collectors.toList());
//        }
    }

    class Intermediate<T> {
        private Node frontier;

        private Intermediate(Node prior, ProcessorNode<T> processor) {
            GraphRepresentation.this.graph.addNode(processor);
            GraphRepresentation.this.graph.putEdge(prior, processor);
            frontier = processor;
        }

        public Intermediate<T> andThen(ProcessorNode<T> processor) {
            return new Intermediate<>(frontier, processor);
        }

        public Collection<Intermediate<T>> fanOut(ProcessorNode<T> p0, ProcessorNode<T>... ps) {
            return Stream.concat(Stream.of(p0), Stream.of(ps))
                    .map(p -> new Intermediate<>(frontier, p))
                    .collect(Collectors.toList());
        }

        public Final<T> endWith(ConsumerNode<T> consumer) {
            return new Final<>(frontier, consumer);
        }
    }

    class Final<T> {
        private Node finalNode;

        private Final(Node prior, ConsumerNode<T> consumer) {
            GraphRepresentation.this.graph.addNode(consumer);
            GraphRepresentation.this.graph.putEdge(prior, consumer);
            finalNode = consumer;
        }
    }
}
