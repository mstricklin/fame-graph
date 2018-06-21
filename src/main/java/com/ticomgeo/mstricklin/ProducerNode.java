package com.ticomgeo.mstricklin;

import java.util.function.Supplier;

/**
 * @author mstricklin
 * @version 1.0
 */
public interface ProducerNode extends Supplier<String>, Node {
    default String graphviz() {
        return "\"" + toString() + "\"  [shape=house]";
    }
}
