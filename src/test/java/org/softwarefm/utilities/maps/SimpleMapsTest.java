/* SoftwareFm is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.*/
/* SoftwareFm is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details. */
/* You should have received a copy of the GNU General Public License along with SoftwareFm. If not, see <http://www.gnu.org/licenses/> */

package org.softwarefm.utilities.maps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class SimpleMapsTest extends TestCase {
	private final ISimpleMap<Object, Object> smA1B2 = SimpleMaps.makeMap("a", 1, "b", 2);
	private final ISimpleMap<Object, Object> smA1C3 = SimpleMaps.makeMap("a", 1, "c", 3);
	private final ISimpleMap<Object, Object> smA1B2C3 = SimpleMaps.makeMap("a", 1, "b", 2, "c", 3);

	private final Map<Object, Object> ma1b2 = Maps.makeLinkedMap("a", 1, "b", 2);
	private final Map<Object, Object> ma1b2c3 = Maps.makeLinkedMap("a", 1, "b", 2, "c", 3);

	public void testMakeMap() {
		checkMatchesLinkedMap(smA1B2, ma1b2);
	}

	@SuppressWarnings("unchecked")
	public void testToListOfMaps() {
		List<Map<Object, Object>> maps = SimpleMaps.toListOfMaps(Arrays.asList(smA1B2, smA1B2C3));
		assertEquals(Arrays.asList(ma1b2, ma1b2c3), maps);
	}

	public void testFromMap() {
		checkMatchesLinkedMap(SimpleMaps.fromMap(ma1b2), ma1b2);
	}

	@SuppressWarnings("unchecked")
	public void testMerge() {
		assertEquals(ma1b2c3, SimpleMaps.merge(smA1B2, smA1B2C3));
		assertEquals(ma1b2c3, SimpleMaps.merge(Arrays.asList(smA1B2, smA1C3)));
	}

	public void testEmpty() {
		checkMatchesLinkedMap(SimpleMaps.empty(), Collections.emptyMap());
	}


	private <K, V> void checkMatchesLinkedMap(ISimpleMap<K, V> simpleMap, Map<K, V> map) {
		List<K> simpleMapKeys = new ArrayList<K>();
		for (int i = 0; i<simpleMap.size(); i++)
			simpleMapKeys.add(simpleMap.key(i));
		assertEquals(new HashSet<K>(simpleMapKeys), new HashSet<K>(map.keySet()));
		assertEquals(simpleMap.size(), map.size());
		assertEquals(simpleMapKeys, new ArrayList<K>(map.keySet()));
		for (K key : simpleMapKeys)
			assertEquals(map.get(key), simpleMap.get(key));
	}

}