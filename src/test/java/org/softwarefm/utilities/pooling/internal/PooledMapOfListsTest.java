package org.softwarefm.utilities.pooling.internal;

import junit.framework.TestCase;

import org.softwarefm.utilities.collections.Sets;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.pooling.IPooledList;
import org.softwarefm.utilities.pooling.IPooledMapOfLists;
import org.softwarefm.utilities.strings.ISettableSimpleString;
import org.softwarefm.utilities.tests.Tests;

public class PooledMapOfListsTest extends TestCase {

	private IPoolStore poolStore;
	private IPooledMapOfLists<String, String> map;

	public void testEmptyMap() {
		assertEquals(0, map.entries().size());
		assertEquals(null, map.get(poolStore, "one"));
	}

	public void testMakingAndGetting() {
		map.add(poolStore, "one", "v11");
		map.add(poolStore, "one", "v12");
		map.add(poolStore, "two", "v21");
		map.add(poolStore, "two", "v22");

		IPooledList<String> valuesForOne = map.get(poolStore, "one");
		IPooledList<String> valuesForTwo = map.get(poolStore, "two");

		Tests.assertEquals(valuesForOne, "v11", "v12");
		Tests.assertEquals(valuesForTwo, "v21", "v22");
	}
	
	public void testCanDealWithNullKeys(){
		map.add(poolStore, null, "v11");
		map.add(poolStore, null, "v12");

		IPooledList<String> values = map.get(poolStore, null);

		Tests.assertEquals(values, "v11", "v12");
		
	}

	public void testClear() {
		map.add(poolStore, "one", "v11");
		map.add(poolStore, "one", "v12");
		map.add(poolStore, "two", "v21");
		map.add(poolStore, "two", "v22");

		map.clear();

		map.add(poolStore, "one", "v11a");
		map.add(poolStore, "one", "v12a");
		map.add(poolStore, "two", "v21a");
		map.add(poolStore, "two", "v22a");


		IPooledList<String> valuesForOne = map.get(poolStore, "one");
		IPooledList<String> valuesForTwo = map.get(poolStore, "two");

		Tests.assertEquals(valuesForOne, "v11a", "v12a");
		Tests.assertEquals(valuesForTwo, "v21a", "v22a");

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		poolStore = IPoolStore.Utils.threadSafePoolStore();
		map = IPooledMapOfLists.Utils.smallMapOfLists();
	}

}
