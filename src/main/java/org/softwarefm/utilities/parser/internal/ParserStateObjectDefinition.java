package org.softwarefm.utilities.parser.internal;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;

public class ParserStateObjectDefinition implements IObjectDefinition<ParserState> {

	private int maxDepth;
	private int maxParents;
	private int maxChildren;

	public ParserStateObjectDefinition(int maxDepth, int maxParents, int maxChildren) {
		this.maxDepth = maxDepth;
		this.maxParents = maxParents;
		this.maxChildren = maxChildren;
	}

	@Override
	public Class<ParserState> objectClass() {
		return ParserState.class;
	}

	@Override
	public ParserState createBlank(IPoolStore poolStore) {
		return new ParserState(poolStore, maxDepth, maxParents, maxChildren);
	}

	@Override
	public void clear(IPoolStore poolStore, ParserState oldObject) {
		oldObject.input = null;
		oldObject.nextIndex = 0;
		oldObject.parentToChildren.clear();
		oldObject.thisObject = null;
	}
}