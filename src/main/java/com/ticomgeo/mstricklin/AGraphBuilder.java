package com.ticomgeo.mstricklin;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author mstricklin
 * @version 1.0
 */
public class AGraphBuilder<T> {


	@SuppressWarnings("unused")
	private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

	@SuppressWarnings("unused")
	private static final String NEWLINE = System.getProperty("line.separator");

	Set<Node> nodes = newHashSet();
	Multimap<String,String> mm = ArrayListMultimap.create();
	Map<Node, List<Node>> adjancencyList = newHashMap();
	Map<String, List<String>> m = new HashMap<String, List<String>>();

	ListMultiMap lmm = new ListMultiMap();

	public void addNode(Node n) {

		lmm.put("a", "A");
	}
	public void addEdge(Node from, Node to) {

	}

	public String graphviz() {
		//		return GraphRepresentation.graphviz(graph);
		return "";
	}

	static class Adjacencies

	// weak-sauce re-implementation of guava
	static class ListMultiMap {
		Map<String, List<String>> backing = newHashMap();
		public boolean put(String key, String value) {
			List<String> lst = backing.get(key);
			if (null == lst) {
				List<String> newList = newArrayList();
				backing.put(key, newList);
				return newList.add(value);
			} else {
				lst.add(value);
				return true;
			}
		}
		Collection<String> get(String key) {
			List<String> lst = backing.get(key);
			if (null == lst) {
				return Collections.emptySet();
			}
			return lst;

		}
		// keys, values, remove, removeAll, iterate?


	}
}