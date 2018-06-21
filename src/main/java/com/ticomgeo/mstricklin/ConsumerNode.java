package com.ticomgeo.mstricklin;

import java.util.function.Consumer;

/**
 * @author mstricklin
 * @version 1.0
 */
public interface ConsumerNode extends Consumer<String>, Node {
    default String graphviz() {
        return "\"" + toString() + "\"  [shape=invhouse]";
    }
}
