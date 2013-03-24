package org.softwarefm.utilities.cache;

import junit.framework.TestCase;

abstract public class AbstractHasCacheTest<K, V> extends TestCase implements IHasCache {

	private V value1;
	private V value2;
	private K key1;
	private K key2;
	private V dataIfNotPut1_1;
	private V dataIfNotPut2_1;
	private V dataIfNotPut1_2;
	private V dataIfNotPut2_2;

	abstract protected K key1();

	abstract protected K key2();

	abstract protected V value1();

	abstract protected V value2();

	// return null if don't do this. first number indicates key1/key2, second whether before or after clearCache
	protected V dataIfNotPutForKey1_1() {
		return null;
	}

	protected V dataIfNotPutForKey2_1() {
		return null;
	}

	protected V dataIfNotPutForKey1_2() {
		return null;
	}

	protected V dataIfNotPutForKey2_2() {
		return null;
	}

	abstract protected void putData(K key, V value);

	abstract protected V getDataFor(K key);

	protected StaleCacheStrategyForTest<K> staleCacheStrategy;

	public void testSetup() {
		assertFalse(key1.equals(key2));
		assertFalse(value1.equals(value2));

		if (dataIfNotPut1_1 != null) {
			assertFalse(dataIfNotPut1_1.equals(value1));
			assertFalse(dataIfNotPut1_1.equals(dataIfNotPut1_2));
			assertFalse(dataIfNotPut1_1.equals(dataIfNotPut2_1));

			assertFalse(dataIfNotPut2_1.equals(value2));
			assertFalse(dataIfNotPut2_1.equals(dataIfNotPut2_2));
		}
	}

	public void testReturnsSameValueEachTime() throws Exception {
		if (dataIfNotPut1_1 == null)
			return;
		assertEquals(dataIfNotPut1_1, getDataFor(key1));
		assertEquals(dataIfNotPut1_1, getDataFor(key1));

		assertEquals(dataIfNotPut2_1, getDataFor(key2));
		assertEquals(dataIfNotPut2_1, getDataFor(key2));
	}

	public void testPutMeansDontNeedToCallDataGetter() {
		putData(key1, value1);
		putData(key2, value2);

		assertEquals(value1, getDataFor(key1));
		assertEquals(value1, getDataFor(key1));
		assertEquals(value1, getDataFor(key1));

		assertEquals(value2, getDataFor(key2));
		assertEquals(value2, getDataFor(key2));
	}

	public void testPutChangesValue() throws Exception {
		if (dataIfNotPut1_1 == null)
			return;
		assertEquals(dataIfNotPut1_1, getDataFor(key1));
		assertEquals(dataIfNotPut2_1, getDataFor(key2));

		putData(key1, value1);
		putData(key2, value2);

		assertEquals(value1, getDataFor(key1));
		assertEquals(value1, getDataFor(key1));
		assertEquals(value1, getDataFor(key1));

		assertEquals(value2, getDataFor(key2));
		assertEquals(value2, getDataFor(key2));
	}

	public void testClearCacheMeansGetDataNextTime() throws Exception {
		if (dataIfNotPut1_1 == null)
			return;
		assertEquals(dataIfNotPut1_1, getDataFor(key1));
		assertEquals(dataIfNotPut2_1, getDataFor(key2));

		staleCacheStrategy.setStale(true);
		assertEquals(dataIfNotPut1_2, getDataFor(key1));
		assertEquals(dataIfNotPut2_2, getDataFor(key2));

		staleCacheStrategy.setStale(false);
		assertEquals(dataIfNotPut1_2, getDataFor(key1));
		assertEquals(dataIfNotPut2_2, getDataFor(key2));
	}

	public void testCacheClearsIfStaleCachingStrategySaysToDoSo() throws Exception {
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		key1 = key1();
		key2 = key2();
		value1 = value1();
		value2 = value2();
		dataIfNotPut1_1 = dataIfNotPutForKey1_1();
		dataIfNotPut2_1 = dataIfNotPutForKey2_1();
		dataIfNotPut1_2 = dataIfNotPutForKey1_2();
		dataIfNotPut2_2 = dataIfNotPutForKey2_2();
		staleCacheStrategy = new StaleCacheStrategyForTest<K>();
	}

}
