package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.arrays.LongArrayAndSize;
import org.softwarefm.utilities.pooling.IMutableSimpleList;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

/** Need to redo this so that it doesn't use collections. But lets get the parser working first! */
public class ParserState implements IObjectsAndEdges{

	public boolean checkMarkers = true;
	public ISimpleString input;
	public int nextIndex;
	public IMutableSimpleList<Object> parents;
	public Object thisObject;
	public IPoolStore poolStore;

	public IMutableSimpleList<Object> objects;
	public LongArrayAndSize parentToChildren;

	public ParserState(IPoolStore poolStore, int maxDepth, int maxObjects) {
		this.poolStore = poolStore;
		this.parents = IMutableSimpleList.Utils.mutableSimpleList(maxDepth);
		this.parentToChildren = new LongArrayAndSize(maxObjects);
		this.objects = IMutableSimpleList.Utils.mutableSimpleList(maxObjects);
	}

	public void populate(ISimpleString input) {
		this.input = input;
		this.parents.clear();
		this.objects.clear();
		this.parentToChildren.clear();
	}

	public IMutableSimpleList<Object> objects() {
		return objects;
	}

	@Override
	public LongArrayAndSize parentToChildren() {
		return parentToChildren;
	}

}
