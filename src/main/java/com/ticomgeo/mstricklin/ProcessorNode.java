package com.ticomgeo.mstricklin;

import java.util.function.Function;

/**
 * @author mstricklin
 * @version 1.0
 */
public interface ProcessorNode extends Function<String,String>, Node {
    default String graphviz() {
        return "\"" + toString() + "\"  [shape=box]";
    }
}
