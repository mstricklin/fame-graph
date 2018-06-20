package com.ticomgeo.mstricklin;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author mstricklin
 * @version 1.0
 */
public abstract class AbstractNode implements Node {
	AbstractNode(String id) {
		this.id = id;
	}
	public final String id;

	@Override
	public String getId() {
		return id;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + getId();
	}

	@SuppressWarnings("unused")
	private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

	@SuppressWarnings("unused")
	private static final String NEWLINE = System.getProperty("line.separator");

}