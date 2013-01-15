package org.softwarefm.utilities.strings;

import java.nio.ByteBuffer;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.internal.ByteBufferSimpleString;

public class ByteBufferStringObjectDefinition  implements IObjectDefinition<ISettableSimpleString>{

	@Override
	public Class<ISettableSimpleString> objectClass() {
		return ISettableSimpleString.class;
	}

	@Override
	public ISettableSimpleString createBlank(IPoolStore poolStore) {
		return new ByteBufferSimpleString();
	}

	@Override
	public void clear(IPoolStore poolStore, ISettableSimpleString oldObject) {
		ByteBufferSimpleString simpleString = ((ByteBufferSimpleString) oldObject);
		simpleString.populate((ByteBuffer)null, 0, 0);
	}

}
