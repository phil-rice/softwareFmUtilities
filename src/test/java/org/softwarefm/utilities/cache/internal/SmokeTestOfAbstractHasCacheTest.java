package org.softwarefm.utilities.cache.internal;

import org.softwarefm.utilities.cache.AbstractHasCacheTest;
import org.softwarefm.utilities.cache.IHasCache;
import org.softwarefm.utilities.functions.IFunction1;

public class SmokeTestOfAbstractHasCacheTest extends AbstractHasCacheTest<String, Object> {

	private SimpleCache<String, Object> cache;
	protected String stale;

	@Override
	public void clearCaches() {
		cache.clear();
	}

	@Override
	protected String key1() {
		return "key1";
	}

	@Override
	protected String key2() {
		return "key2";
	}

	@Override
	protected Object value1() {
		return "value1";
	}

	@Override
	protected Object value2() {
		return "value2";
	}

	@Override
	protected Object dataIfNotPutForKey1_1() {
		return "Auto0";
	}

	@Override
	protected Object dataIfNotPutForKey2_1() {
		return "Auto1";
	}

	@Override
	protected Object dataIfNotPutForKey1_2() {
		return "Auto2";
	}

	@Override
	protected Object dataIfNotPutForKey2_2() {
		return "Auto3";
	}

	@Override
	protected void putData(String key, Object value) {
		cache.put(key, value);
	}

	@Override
	protected Object getDataFor(String key) {
		return cache.get(key);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cache = (SimpleCache<String, Object>) IHasCache.Utils.simpleCache(staleCacheStrategy, new IFunction1<String, Object>() {
			private int count;
			@Override
			public Object apply(String from) throws Exception {
				return "Auto" + count++;
			}
		});
	}
}
