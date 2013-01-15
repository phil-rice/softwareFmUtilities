/* SoftwareFm is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.*/
/* SoftwareFm is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. */
/* You should have received a copy of the GNU General Public License along with SoftwareFm. If not, see <http://www.gnu.org/licenses/> */

package org.softwarefm.utilities.collections;

import java.util.Collections;
import java.util.List;

import org.softwarefm.utilities.pooling.IMutableSimpleList;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.internal.MutableListObjectDefinition;
import org.softwarefm.utilities.pooling.internal.PooledList;

public interface ISimpleList<T> {
	int size();

	T get(int index);

	public static class Utils {

		public static <T> int indexOf(ISimpleList<T> list, T t) {
			for (int i = 0; i < list.size(); i++)
				if (t == null) {
					if (list.get(i) == null)
						return i;
				} else if (t.equals(list.get(i)))
					return i;
			return -1;
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static final ISimpleList empty = fromList(Collections.EMPTY_LIST);

		public static <T> ISimpleList<T> fromList(final List<T> list) {
			return new ISimpleList<T>() {
				@Override
				public int size() {
					return list == null ? 0 : list.size();
				}

				@Override
				public T get(int index) {
					return list.get(index);
				}
			};
		}

		public static <T> IMutableSimpleList<T> mutableSimpleList(int maxSize) {
			return new PooledList<T>(maxSize);

		}

		public static <T> IObjectDefinition<IMutableSimpleList<T>> pooledListdefn(int maxSize) {
			return new MutableListObjectDefinition<T>(maxSize);

		}
	}

}