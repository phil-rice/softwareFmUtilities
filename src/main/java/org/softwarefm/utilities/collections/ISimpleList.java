/* SoftwareFm is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.*/
/* SoftwareFm is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. */
/* You should have received a copy of the GNU General Public License along with SoftwareFm. If not, see <http://www.gnu.org/licenses/> */

package org.softwarefm.utilities.collections;

import java.util.Collections;
import java.util.List;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPooledList;
import org.softwarefm.utilities.pooling.internal.PooledList;
import org.softwarefm.utilities.pooling.internal.PooledListObjectDefinition;

public interface ISimpleList<T> {
	int size();

	T get(int index);

	public static class Utils {

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

		public static <T>IPooledList<T> pooledList(int maxArrays) {
			return new PooledList<T>(maxArrays);
			
		}
		public static <T>IObjectDefinition<IPooledList<T>> pooledListdefn(int maxArrays) {
			return new PooledListObjectDefinition<T>(maxArrays);
			
		}
	}

}