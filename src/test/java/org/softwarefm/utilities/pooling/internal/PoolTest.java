package org.softwarefm.utilities.pooling.internal;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.tests.Tests;

public class PoolTest extends TestCase {

	private final static Object object1 = new Object();
	private final static Object object2 = new Object();
	private IObjectDefinition<Object> defn;
	private IPoolStore poolStore;

	public void testGetReturnsObjectCreatedByObjectDefinitionifNothingInFreeList() {
		EasyMock.expect(defn.createBlank(poolStore)).andReturn(object1);
		// defn.clear(poolStore, object1); This is here to make it "clear" that the object isn't being cleared
		EasyMock.replay(defn);

		Pool<Object> pool = new Pool<Object>(defn);

		assertSame(object1, pool.get(poolStore));
	}

	public void testGetReturnsCleanedObjectCreatedByObjectDefinitionifNothingInFreeListWhenGettingMultiple() {
		EasyMock.expect(defn.createBlank(poolStore)).andReturn(object1);
		EasyMock.expect(defn.createBlank(poolStore)).andReturn(object2);
		EasyMock.replay(defn);

		Pool<Object> pool = new Pool<Object>(defn);

		assertSame(object1, pool.get(poolStore));
		assertSame(object2, pool.get(poolStore));
	}

	public void testGetSameObjectsAgainAfterResetAndTheyAreCleared() {
		EasyMock.expect(defn.createBlank(poolStore)).andReturn(object1);
		EasyMock.expect(defn.createBlank(poolStore)).andReturn(object2);

		defn.clear(poolStore, object1);
		defn.clear(poolStore, object2);

		EasyMock.replay(defn);

		Pool<Object> pool = new Pool<Object>(defn);

		assertSame(object1, pool.get(poolStore));
		assertSame(object2, pool.get(poolStore));

		pool.reset();

		assertSame(object1, pool.get(poolStore));
		assertSame(object2, pool.get(poolStore));
	}

	public void testGetIndex() {
		EasyMock.expect(defn.createBlank(poolStore)).andReturn(object1);
		EasyMock.expect(defn.createBlank(poolStore)).andReturn(object2);

		EasyMock.replay(defn);

		final Pool<Object> pool = new Pool<Object>(defn);

		pool.get(poolStore);
		pool.get(poolStore);
		assertSame(object1, pool.get(0));
		assertSame(object2, pool.get(1));

		Tests.assertThrows(IndexOutOfBoundsException.class, new Runnable() {
			public void run() {
				pool.get(2);
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		super.setUp();
		defn = EasyMock.createMock(IObjectDefinition.class);
		poolStore = IPoolStore.Utils.threadSafePoolStore();
	}

	protected void tearDown() throws Exception {
		EasyMock.verify(defn);
	};

}
