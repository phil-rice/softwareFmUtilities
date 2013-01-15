package org.softwarefm.utilities.pooling.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CyclicBarrier;

import junit.framework.TestCase;

import org.softwarefm.utilities.callbacks.ICallback;
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

	public void testInUse() {
		IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
		SettableSimpleStringObjectDefinition objectDefinition = new SettableSimpleStringObjectDefinition();
		IPool<ISettableSimpleString> pool = poolStore.pool(objectDefinition);
		assertEquals(0, pool.inUse());
		pool.get(poolStore);
		pool.get(poolStore);
		assertEquals(2, pool.inUse());

	}

	public void testReturnsDifferentPoolWithDifferentObjectDefn() {
		IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
		IPool<ISettableSimpleString> pool1 = poolStore.pool(new SettableSimpleStringObjectDefinition());
		IPool<ISettableSimpleString> pool2 = poolStore.pool(new SettableSimpleStringObjectDefinition());
		assertNotSame(pool1, pool2);
	}

	public void testDifferentPoolsForDifferentThreads() {
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

	public void testResetInOneThreadDoesntImpactOthers() {
		final IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
		final SettableSimpleStringObjectDefinition defn = new SettableSimpleStringObjectDefinition();
		ICallback<Integer> callback = new ICallback<Integer>() {

			final CyclicBarrier barrier1 = new CyclicBarrier(10);
			final CyclicBarrier barrier2 = new CyclicBarrier(10);

			@Override
			public void process(Integer t) throws Exception {
				IPool<ISettableSimpleString> pool = poolStore.pool(defn);
				for (int i = 0; i < 10; i++)
					pool.get(poolStore);
				assertEquals(10, pool.inUse());
				barrier1.await();
				if (t == 3)
					pool.reset();
				barrier2.await();
				assertEquals(t == 3 ? 0 : 10, pool.inUse());

			}
		};
		Tests.executeInMultipleThreads(10, callback);

	}
}
