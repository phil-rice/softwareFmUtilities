/* SoftwareFm is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.*/
/* SoftwareFm is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. */
/* You should have received a copy of the GNU General Public License along with SoftwareFm. If not, see <http://www.gnu.org/licenses/> */

package org.softwarefm.utilities.cache;

import org.softwarefm.utilities.cache.internal.SimpleCache;
import org.softwarefm.utilities.functions.IFunction1;
import org.softwarefm.utilities.maps.IMutableSimpleMap;

public interface IHasCache {

	void clearCaches();

	public static class Utils {
		public static <K, V> IMutableSimpleMap<K, V> simpleCache(IStaleCacheStrategy<K> staleCacheStrategy, IFunction1<K, V> dataGetter) {
			return new SimpleCache<K,V>(staleCacheStrategy, dataGetter);
		}
	}

}