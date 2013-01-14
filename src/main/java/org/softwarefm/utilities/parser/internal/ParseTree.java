package org.softwarefm.utilities.parser.internal;

import java.util.List;
import java.util.Map;

import org.softwarefm.utilities.collections.ISimpleList;
import org.softwarefm.utilities.parser.IParseTree;

@SuppressWarnings("unchecked")
public class ParseTree implements IParseTree {

	private final Object root;
	private final Map<Object, List<Object>> redo;

	public ParseTree(Object root, Map<Object, List<Object>> redo) {
		this.root = root;
		this.redo = redo;
	}

	@Override
	public <T> T root() {
		return (T) root;
	}

	@Override
	public <P, C> ISimpleList<C> children(P parent) {
		List<C> result = (List<C>) redo.get(parent);
		if (result == null)
			return ISimpleList.Utils.empty;
		else
			return ISimpleList.Utils.<C> fromList(result);
	}

}
