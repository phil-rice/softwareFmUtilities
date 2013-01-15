package org.softwarefm.utilities.pooling.internal;

import junit.framework.TestCase;

import org.softwarefm.utilities.pooling.IMutableSimpleList;
import org.softwarefm.utilities.tests.Tests;

public class PooledListTest extends TestCase {

	private IMutableSimpleList<String> mutableSimpleList;

	public void testAddGetAndSize() {
		assertEquals(0, mutableSimpleList.size());
		mutableSimpleList.add("one");
		mutableSimpleList.add("two");
		assertEquals("one", mutableSimpleList.get(0));
		assertEquals("two", mutableSimpleList.get(1));
		assertEquals(2, mutableSimpleList.size());
	}

	public void testPopreturnsLastItemAddedAndShrinksListByOne() {
		mutableSimpleList.add("one");
		mutableSimpleList.add("two");
		assertEquals("two", mutableSimpleList.pop());
		assertEquals(1, mutableSimpleList.size());
		assertEquals("one", mutableSimpleList.pop());
		assertEquals(0, mutableSimpleList.size());
	}

	public void testPopThrowsExceptionIfRunOut() {
		mutableSimpleList.add("one");
		mutableSimpleList.add("two");
		mutableSimpleList.pop();
		mutableSimpleList.pop();
		Tests.assertThrows(IndexOutOfBoundsException.class, new Runnable() {
			public void run() {
				mutableSimpleList.pop();
			}
		});
	}

	public void testClear() {
		assertEquals(0, mutableSimpleList.size());
		mutableSimpleList.add("one");
		mutableSimpleList.add("two");
		
		mutableSimpleList.clear();
		mutableSimpleList.add("three");
		mutableSimpleList.add("four");

		assertEquals("three", mutableSimpleList.get(0));
		assertEquals("four", mutableSimpleList.get(1));
		assertEquals(2, mutableSimpleList.size());

		Tests.assertThrows(IndexOutOfBoundsException.class, new Runnable() {
			public void run() {
				mutableSimpleList.get(2);
			}
		});
	}

	public void testCanAddUpToMaxSize() {
		for (int i = 0; i < 10; i++)
			mutableSimpleList.add("Item" + i);
		for (int i = 0; i < 10; i++)
			assertEquals("Item" + i, mutableSimpleList.get(i));
	}

	public void testThrowsExceptionIfAddTooMany() {
		for (int i = 0; i < 10; i++)
			mutableSimpleList.add("Item" + i);
		Tests.assertThrows(IndexOutOfBoundsException.class, new Runnable() {
			public void run() {
				mutableSimpleList.add("Item");
			}
		});

	}

	protected void setUp() throws Exception {
		mutableSimpleList = IMutableSimpleList.Utils.mutableSimpleList(10);
	};
}
