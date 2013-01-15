package org.softwarefm.utilities.arrays;

import junit.framework.TestCase;

public class LongArrayAndSizeTest extends TestCase {

	public void testAddGet() {
		LongArrayAndSize l = new LongArrayAndSize(10);
		for (long i = 0; i < 10; i++)
			l.add(i);
		for (long i = 0; i < 10; i++)
			assertEquals(i, l.get((int) i));
		assertEquals(10, l.size());
	}

	public void testAddAndGetHiLo() {
		LongArrayAndSize l = new LongArrayAndSize(10);
		for (int i = 0; i < 10; i++)
			l.add(i * 2, i * 3);
		for (int i = 0; i < 10; i++) {
			assertEquals(i * 2, l.getHi(i));
			assertEquals(i * 3, l.getLo(i));
		}
		assertEquals(10, l.size());
	}

	public void testClear() {
		LongArrayAndSize l = new LongArrayAndSize(10);
		for (long i = 0; i < 10; i++)
			l.add(i);
		l.clear();
		for (long i = 0; i < 10; i++)
			l.add(i * 2);
		for (long i = 0; i < 10; i++)
			assertEquals(i * 2, l.get((int) i));
		assertEquals(10, l.size());
	}

	public void testEquals() {
		LongArrayAndSize l1 = new LongArrayAndSize(10);
		LongArrayAndSize l1a = new LongArrayAndSize(10);
		LongArrayAndSize l2 = new LongArrayAndSize(10);
		for (long i = 0; i < 8; i++) {
			l1.add(i);
			l1a.add(i);
			l2.add(i + 1);
		}
		l1.data[9] = 123;// should be
		l1a.data[9] = 234; // irrelevant
		assertEquals(l1, l1a);
		assertFalse(l1.equals(l2));
		l1.add(234);
		assertFalse(l1.equals(l1a));

	}
}
