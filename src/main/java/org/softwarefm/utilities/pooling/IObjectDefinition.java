package org.softwarefm.utilities.pooling;

import org.softwarefm.utilities.pooling.internal.IntArrayDefn;
import org.softwarefm.utilities.pooling.internal.ObjectArrayDefn;
import org.softwarefm.utilities.strings.ISettableSimpleString;
import org.softwarefm.utilities.strings.SettableSimpleStringObjectDefinition;

public interface IObjectDefinition<T> {

	Class<T> objectClass();

	T createBlank(IPoolStore poolStore);

	void clear(IPoolStore poolStore, T oldObject);

	public static class Utils {
		public static IObjectDefinition<ISettableSimpleString> settableSimpleStringDefn = new SettableSimpleStringObjectDefinition();

		public static IObjectDefinition<Object[]> objectArrayDefn(int size) {
			return new ObjectArrayDefn(size);
		}
		public static IObjectDefinition<int[]> intArrayDefn(int size) {
			return new IntArrayDefn(size);
		}
	}

}
