package org.softwarefm.utilities.strings;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.internal.ByteArraySimpleString;

public class SettableSimpleStringObjectDefinition  implements IObjectDefinition<ISettableSimpleString>{

	@Override
	public Class<ISettableSimpleString> objectClass() {
		return ISettableSimpleString.class;
	}

	@Override
	public ISettableSimpleString createBlank(IPoolStore poolStore) {
		return new ByteArraySimpleString();
	}

	@Override
	public void clear(IPoolStore poolStore, ISettableSimpleString oldObject) {
		ByteArraySimpleString simpleString = ((ByteArraySimpleString) oldObject);
		simpleString.populate(null, 0, 0);
	}

}
