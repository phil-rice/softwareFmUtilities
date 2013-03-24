package org.softwarefm.utilities.cache.internal;

import java.util.List;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.softwarefm.utilities.cache.IHasCache;
import org.softwarefm.utilities.cache.IStaleCacheStrategy;
import org.softwarefm.utilities.collections.Lists;
import org.softwarefm.utilities.functions.IFunction1;
import org.softwarefm.utilities.tests.Tests;

public class SimpleCacheTest extends TestCase {

	private IStaleCacheStrategy<String> staleCacheStrategy;
	private IFunction1<String, Object> dataGetter;
	private SimpleCache<String, Object> cache;

	public void testSizeAndKey() {
		replay();
		cache.put("key1", "value1");
		checkKeysAndSizeMatch("key1");
		cache.put("key2", "value2");
		checkKeysAndSizeMatch("key1", "key2");
	}

	public void testReturnsSameValueEachTime() throws Exception {
		EasyMock.expect(dataGetter.apply("key1")).andReturn("value1");
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		replay();
		assertEquals("value1", cache.get("key1"));
		assertEquals("value1", cache.get("key1"));
		checkKeysAndSizeMatch("key1");
	}

	public void testPutMeansDontNeedToCallDataGetter() {
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		replay();
		cache.put("key1", "value1");
		assertEquals("value1", cache.get("key1"));
		assertEquals("value1", cache.get("key1"));
		checkKeysAndSizeMatch("key1");
	}

	public void testPutChangesValue() throws Exception {
		EasyMock.expect(dataGetter.apply("key1")).andReturn("value1");
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		replay();
		assertEquals("value1", cache.get("key1"));
		cache.put("key1", "value2");
		assertEquals("value2", cache.get("key1"));
		assertEquals("value2", cache.get("key1"));
		checkKeysAndSizeMatch("key1");
	}

	public void testClearCacheMeansGetDataNextTime() throws Exception {
		EasyMock.expect(dataGetter.apply("key1")).andReturn("value1");
		EasyMock.expect(dataGetter.apply("key1")).andReturn("value2");
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		replay();
		assertEquals("value1", cache.get("key1"));
		cache.clear();
		checkKeysAndSizeMatch();
		assertEquals("value2", cache.get("key1"));
		assertEquals("value2", cache.get("key1"));
		checkKeysAndSizeMatch("key1");
	}

	public void testCacheClearsIfStaleCachingStrategySaysToDoSo() throws Exception {
		EasyMock.expect(dataGetter.apply("key1")).andReturn("value1");
		EasyMock.expect(dataGetter.apply("key1")).andReturn("value2");
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(true);
		EasyMock.expect(staleCacheStrategy.isStale("key1")).andReturn(false);
		replay();
		assertEquals("value1", cache.get("key1"));
		assertEquals("value2", cache.get("key1"));
		assertEquals("value2", cache.get("key1"));
		checkKeysAndSizeMatch("key1");
	}

	private void replay() {
		EasyMock.replay(staleCacheStrategy, dataGetter);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		staleCacheStrategy = EasyMock.createMock(IStaleCacheStrategy.class);
		dataGetter = EasyMock.createMock(IFunction1.class);
		cache = (SimpleCache<String, Object>) IHasCache.Utils.<String, Object> simpleCache(staleCacheStrategy, dataGetter);
	}

	private void checkKeysAndSizeMatch(String... expected) {
		assertEquals(expected.length, cache.size());
		List<String> keys = Lists.newList();
		for (int i = 0; i < expected.length; i++)
			keys.add(cache.key(i));
		Tests.assertListEquals(keys, expected);
	}

	@Override
	protected void tearDown() throws Exception {
		EasyMock.verify(staleCacheStrategy, dataGetter);
		super.tearDown();

	}
}
