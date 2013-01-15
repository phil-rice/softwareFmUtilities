package org.softwarefm.utilities.parser.internal;

import org.softwarefm.utilities.collections.ISimpleList;
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
		int parentIndex = ISimpleList.Utils.indexOf(state.objects, parent);
		state.thisObject = state.poolStore.pool(objectDefinition).get(state.poolStore);
		state.objects.add(state.thisObject);
		state.parents.add(state.thisObject);
		if (parentIndex != -1)
			state.parentToChildren.add(parentIndex, state.objects.size() - 1);
		return true;
	}

}