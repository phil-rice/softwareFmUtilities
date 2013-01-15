/* SoftwareFm is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.*/
/* SoftwareFm is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. */
/* You should have received a copy of the GNU General Public License along with SoftwareFm. If not, see <http://www.gnu.org/licenses/> */

package org.softwarefm.utilities.maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.softwarefm.utilities.collections.Iterables;
import org.softwarefm.utilities.collections.Lists;

public class SimpleMaps {

	private static class SimpleMap<K, V> implements ISimpleMap<K, V> {
		private final Map<K, V> map;
		private final List<K> keyList;

		private SimpleMap(Map<K, V> map) {
			this.map = map;
			keyList = Iterables.list(map.keySet());
		}

		public V get(K key) {
			return map.get(key);
		}

		@Override
		public K key(int i) {
			return keyList.get(i);
		}
		@Override
		public int size() {
			return keyList.size();
		}

		@Override
		public String toString() {
			return map.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((map == null) ? 0 : map.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			@SuppressWarnings("rawtypes")
			SimpleMap other = (SimpleMap) obj;
			if (map == null) {
				if (other.map != null)
					return false;
			} else if (!map.equals(other.map))
				return false;
			return true;
		}

	}

	public static <K, V> ISimpleMap<K, V> makeMap(Object... kvs) {
		return fromMap(Maps.<K, V> makeLinkedMap(kvs));
	}

	public static <K, V, T extends Iterable<? extends ISimpleMap<K, V>>> List<Map<K, V>> toListOfMaps(T from) {
		List<Map<K, V>> result = Lists.newList();
		for (ISimpleMap<K, V> map : from)
			result.add(Maps.fromSimpleMap(map));
		return result;
	}

	public static <K, V> ISimpleMap<K, V> fromMap(final Map<K, V> map) {
		return new SimpleMap<K, V>(map);
	}

	public static <K, V> Map<K, V> merge(Iterable<? extends ISimpleMap<K, V>> maps) {
		Map<K, V> result = new HashMap<K, V>();
		for (ISimpleMap<K, V> map : maps)
			for (int i = 0; i < map.size(); i++) {
				K key = map.key(i);
				result.put(key, map.get(key));
			}
		return result;
	}

	public static <K, V> Map<K, V> merge(ISimpleMap<K, V>... maps) {
		Map<K, V> result = new HashMap<K, V>();
		for (ISimpleMap<K, V> map : maps)
			for (int i = 0; i < map.size(); i++) {
				K key = map.key(i);
				result.put(key, map.get(key));
			}
		return result;
	}

	public static <K, V> ISimpleMap<K, V> empty() {
		return new ISimpleMap<K, V>() {

			public V get(K key) {
				return null;
			}

			@Override
			public int size() {
				return 0;
			}

			@Override
			public K key(int i) {
				throw new IndexOutOfBoundsException();
			}

		};
	}

}