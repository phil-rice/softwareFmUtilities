package org.softwarefm.utilities.pooling.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.softwarefm.utilities.pooling.IPool;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISettableSimpleString;
import org.softwarefm.utilities.strings.SettableSimpleStringObjectDefinition;
import org.softwarefm.utilities.tests.Tests;

public class ThreadSafePoolStoreTest extends TestCase {

	public void testReturnsNewPoolFirstTime() {
		IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
		IPool<ISettableSimpleString> pool = poolStore.pool(new SettableSimpleStringObjectDefinition());
		assertNotNull(pool);
	}

	public void testReturnsSamePoolSecondTime() {
		IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
		SettableSimpleStringObjectDefinition objectDefinition = new SettableSimpleStringObjectDefinition();
		IPool<ISettableSimpleString> pool1 = poolStore.pool(objectDefinition);
		IPool<ISettableSimpleString> pool2 = poolStore.pool(objectDefinition);
		assertSame(pool1, pool2);
	}
	public void testReturnsDifferentPoolWithDifferentObjectDefn() {
		IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
		IPool<ISettableSimpleString> pool1 = poolStore.pool(new SettableSimpleStringObjectDefinition());
		IPool<ISettableSimpleString> pool2 = poolStore.pool(new SettableSimpleStringObjectDefinition());
		assertNotSame(pool1, pool2);
	}

	public void testDifferentPoolsForDifferentThreads(){
		final IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
		final SettableSimpleStringObjectDefinition defn = new SettableSimpleStringObjectDefinition();
		final Set<IPool<ISettableSimpleString>> pools = Collections.synchronizedSet(new HashSet<IPool<ISettableSimpleString>>());
		Tests.executeInMultipleThreads(10, new Runnable() {
			public void run() {
				IPool<ISettableSimpleString> pool1 = poolStore.pool(defn);
				IPool<ISettableSimpleString> pool2 = poolStore.pool(defn);
				assertSame(pool1, pool2);
				pools.add(pool1);
			}
		});
		assertEquals(10, pools.size());
	}
	
}
