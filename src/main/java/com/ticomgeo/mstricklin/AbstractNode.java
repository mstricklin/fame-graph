package com.ticomgeo.mstricklin;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.Objects;

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
	@Override
	public int hashCode() {
		return Objects.hash(getClass(), id);
	}
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (getClass() != other.getClass())
			return false;
		AbstractNode an = (AbstractNode) other;
		return Objects.equals(id, an.id);
	}

	@SuppressWarnings("unused")
	private static final Logger CLASS_LOGGER = LoggerFactory.getLogger((new Throwable()).getStackTrace()[0].getClassName());

	@SuppressWarnings("unused")
	private static final String NEWLINE = System.getProperty("line.separator");

}