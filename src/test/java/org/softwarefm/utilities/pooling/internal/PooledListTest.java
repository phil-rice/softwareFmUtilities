package org.softwarefm.utilities.pooling.internal;

import junit.framework.TestCase;

import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.pooling.IPooledList;
import org.softwarefm.utilities.tests.Tests;

public class PooledListTest extends TestCase {

	private IPoolStore poolStore;
	private IPooledList<String> pooledList;

	public void testAddGetAndSize() {
		assertEquals(0, pooledList.size());
		pooledList.add("one");
		pooledList.add("two");
		assertEquals("one", pooledList.get(0));
		assertEquals("two", pooledList.get(1));
		assertEquals(2, pooledList.size());
	}

	public void testPopreturnsLastItemAddedAndShrinksListByOne() {
		pooledList.add("one");
		pooledList.add("two");
		assertEquals("two", pooledList.pop());
		assertEquals(1, pooledList.size());
		assertEquals("one", pooledList.pop());
		assertEquals(0, pooledList.size());
	}

	public void testPopThrowsExceptionIfRunOut() {
		pooledList.add("one");
		pooledList.add("two");
		pooledList.pop();
		pooledList.pop();
		Tests.assertThrows(IndexOutOfBoundsException.class, new Runnable() {
			public void run() {
				pooledList.pop();
			}
		});
	}

	public void testClear() {
		assertEquals(0, pooledList.size());
		pooledList.add("one");
		pooledList.add("two");
		
		pooledList.clear();
		pooledList.add("three");
		pooledList.add("four");

		assertEquals("three", pooledList.get(0));
		assertEquals("four", pooledList.get(1));
		assertEquals(2, pooledList.size());

		Tests.assertThrows(IndexOutOfBoundsException.class, new Runnable() {
			public void run() {
				pooledList.get(2);
			}
		});
	}

	public void testCanAddUpToMaxSize() {
		for (int i = 0; i < 10; i++)
			pooledList.add("Item" + i);
		for (int i = 0; i < 10; i++)
			assertEquals("Item" + i, pooledList.get(i));
	}

	public void testThrowsExceptionIfAddTooMany() {
		for (int i = 0; i < 10; i++)
			pooledList.add("Item" + i);
		Tests.assertThrows(IndexOutOfBoundsException.class, new Runnable() {
			public void run() {
				pooledList.add("Item");
			}
		});

	}

	protected void setUp() throws Exception {
		poolStore = IPoolStore.Utils.threadSafePoolStore();
		pooledList = IPooledList.Utils.pooledList(10);
	};
}
