package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;

public class ParserStateObjectDefinition implements IObjectDefinition<ParserState> {

	private int maxDepth;
	private int maxObjects;


	public ParserStateObjectDefinition(int maxDepth, int maxObjects) {
		this.maxDepth = maxDepth;
		this.maxObjects = maxObjects;
	}

	@Override
	public Class<ParserState> objectClass() {
		return ParserState.class;
	}

	@Override
	public ParserState createBlank(IPoolStore poolStore) {
		return new ParserState(poolStore, maxDepth, maxObjects);
	}

	@Override
	public void clear(IPoolStore poolStore, ParserState oldObject) {
		oldObject.input = null;
		oldObject.nextIndex = 0;
		oldObject.parentToChildren.clear();
		oldObject.thisObject = null;
	}
}