package com.ticomgeo.mstricklin;

import java.util.function.Function;

/**
 * @author mstricklin
 * @version 1.0
 */
public interface ProcessorNode<T> extends Function<T,T>, Node {
    default String graphviz() {
        return "\"" + toString() + "\"  [shape=box]";
    }
}
