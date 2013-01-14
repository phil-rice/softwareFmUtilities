package org.softwarefm.utilities.parser.internal;

import org.softwarefm.utilities.parser.ParserState;
import org.softwarefm.utilities.pooling.IObjectDefinition;

public class StartObjectChunk<T> extends AbstractParser {
	private final IObjectDefinition<T> objectDefinition;

	public StartObjectChunk(String objectMarker, String encoding, IObjectDefinition<T> objectDefinition) {
		super(objectMarker, encoding);
		this.objectDefinition = objectDefinition;
	}

	@Override
	public boolean parse(ParserState state) {
		boolean present = optionalMarker(state);
		if (!present)
			return false;
		Object parent = state.parents.size() == 0 ? null : state.parents.get(state.parents.size() - 1);
		state.thisObject = objectDefinition.createBlank(state.poolStore);
		state.parents.add(state.thisObject);
		state.parentToChildren.add(state.poolStore, parent, state.thisObject);
		return true;
	}

}