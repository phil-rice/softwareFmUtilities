package org.softwarefm.utilities.strings.internal;

import java.io.UnsupportedEncodingException;

import org.softwarefm.utilities.exceptions.WrappedException;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPool;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISettableSimpleString;
import org.softwarefm.utilities.strings.ISimpleString;

public class ByteArraySimpleString extends AbstractSimpleString implements ISettableSimpleString {

	private byte[] byteArray;

	@Override
	public byte byteAt(int offset) {
		return byteArray[start + offset];
	}

	public void populate(byte[] byteArray, int start, int length) {
		this.byteArray = byteArray;
		this.start = start;
		int actualLength = byteArray == null ? 0 : Math.min(length, byteArray.length - start);
		this.length = actualLength;
	}

	@Override
	public ISimpleString subString(IPoolStore poolStore, int offset, int length) {
		IPool<ISettableSimpleString> pool = poolStore.pool(IObjectDefinition.Utils.settableSimpleStringDefn);
		ISettableSimpleString result = pool.get(poolStore);
		result.populate(byteArray, this.start + offset, length);
		return result;
	}

	@Override
	public String asString(String encoding) {
		try {
			if (byteArray == null)
				return null;
			return new String(byteArray, start, length, encoding);
		} catch (UnsupportedEncodingException e) {
			throw WrappedException.wrap(e);
		}
	}

	@Override
	public String toString() {
		if (byteArray == null)
			return "Empty@" + hashCode();
		return asString("UTF-8");
	}

}
