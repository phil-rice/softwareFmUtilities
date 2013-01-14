package org.softwarefm.utilities.parser.internal;

import java.util.ArrayList;
import java.util.List;

import org.softwarefm.utilities.collections.ISimpleList;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.pooling.IPooledList;
import org.softwarefm.utilities.pooling.IPooledMapOfLists;
import org.softwarefm.utilities.strings.ISimpleString;

/** Need to redo this so that it doesn't use collections. But lets get the parser working first! */
public class ParserState {

	public boolean checkMarkers = true;
	public ISimpleString input;
	public int nextIndex;
	public IPooledList<Object> parents;
	public IPooledMapOfLists<Object, Object> parentToChildren ;
	public Object thisObject;
	public IPoolStore poolStore;

	public ParserState(IPoolStore poolStore, int maxDepth, int maxParents, int maxChildren) {
		this.poolStore = poolStore;
		this.parents  = IPooledList.Utils.pooledList(maxDepth);
		this.parentToChildren= IPooledMapOfLists.Utils.mapOfLists(maxParents, maxChildren);
	}

	public void populate(ISimpleString input) {
		this.input = input;
	}


	@SuppressWarnings("unchecked")
	public <T> T root() {
		return (T) parentToChildren.get(poolStore, null).get(0);
	}

	@SuppressWarnings("unchecked")
	public <P, C> ISimpleList<C> children(P parent) {
		ISimpleList<C> result = (ISimpleList<C>) parentToChildren.get(poolStore, parent);
		return result;
	}

}
