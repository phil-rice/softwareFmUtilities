package org.softwarefm.utilities.events.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.IListenerList;
import org.softwarefm.utilities.events.IMultipleListenerList;
import org.softwarefm.utilities.events.IMultipleListenerListExecutor;
import org.softwarefm.utilities.events.IMultipleListenerListListener;
import org.softwarefm.utilities.events.IValid;
import org.softwarefm.utilities.exceptions.MultipleExceptions;
import org.softwarefm.utilities.tests.Tests;

public class ListenerListTest extends TestCase {

	static interface ITestListenerRoot {
	}

	static interface ITestListener extends ITestListenerRoot {
	}

	static interface ITestListenerAndValid extends ITestListener, IValid {
	}

	private IMultipleListenerList rawList;
	private final Object source = new Object();
	private final Object invalidSource = new Object();
	private ITestListener mockL1C1;
	private ITestListener mockL2C1;
	private ITestListener mockL1C2;
	private ITestListener mockL2C2;
	private ITestListener mockInvalid1;
	private ITestListener mockInvalid2;

	private IMultipleListenerListListener<Object> globalListener1;
	private IMultipleListenerListListener<Object> globalListener2;

	private ITestListenerAndValid mockV1;
	private ITestListenerAndValid mockV2;
	private ListenerList<ITestListener> list;

	public void testSize() {
		replay();
		assertEquals(0, list.size());
		list.addListener(mockL1C1);
		list.addListener(mockL2C1);
		assertEquals(2, list.size());
		list.clear();
		assertEquals(0, list.size());
	}

	public void testContains() {
		assertEquals(0, list.size());
		assertFalse(list.contains(mockL1C1));
		list.addListener(mockL1C1);
		assertTrue(list.contains(mockL1C1));
		list.clear();
		assertFalse(list.contains(mockL1C1));
	}

	public void testCallsFireOnAllListeners() {
		list.addListener(mockL1C1);
		list.addListener(mockL2C1);
		replay();

		final List<ITestListener> actual = Collections.synchronizedList(new ArrayList<ITestListener>());
		list.fire(new ICallback<ITestListener>() {
			public void process(ITestListener listener) throws Exception {
				actual.add(listener);
			}
		});
		verify();
		assertEquals(Arrays.asList(mockL1C1, mockL2C1), actual);
	}

	public void testCallsFireOnAllListenersEvenIfExceptionThenThrowsMultipleException() {
		list.addListener(mockL1C1);
		list.addListener(mockL2C1);
		replay();

		final List<ITestListener> actual = Collections.synchronizedList(new ArrayList<ITestListener>());
		final List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<Throwable>());

		MultipleExceptions actualException = Tests.assertThrows(MultipleExceptions.class, new Runnable() {
			public void run() {
				list.fire(new ICallback<ITestListener>() {
					public void process(ITestListener t) throws Exception {
						actual.add(t);
						Error e = new Error();
						exceptions.add(e);
						throw e;
					}
				});
			}
		});
		verify();
		assertEquals(exceptions, actualException.getCauses());
		assertEquals(Arrays.asList(mockL1C1, mockL2C1), actual);
	}

	public void testClearRemoversListeners() {
		list.addListener(mockL1C1);
		list.addListener(mockL1C2);
		replay();
		list.clear();
		list.addListener(mockL2C1);
		list.addListener(mockL2C2);
		final List<ITestListener> actual = new ArrayList<ListenerListTest.ITestListener>();
		list.fire(new ICallback<ITestListener>() {
			public void process(ITestListener t) throws Exception {
				actual.add(t);
			}
		});
		assertEquals(Arrays.asList(mockL2C1, mockL2C2), actual);
	}

	public void testClearWorksWhenNoListenersWereAdded() {
		replay();
		list.clear();
		list.addListener(mockL2C1);
		list.addListener(mockL2C2);
		final List<ITestListener> actual = new ArrayList<ListenerListTest.ITestListener>();
		list.fire(new ICallback<ITestListener>() {
			public void process(ITestListener t) throws Exception {
				actual.add(t);
			}
		});
		assertEquals(Arrays.asList(mockL2C1, mockL2C2), actual);
	}

	public void testThreadDeathStopsListeners() {
		list.addListener(mockL1C1);
		list.addListener(mockL2C1);
		replay();
		final List<ITestListener> actual = Collections.synchronizedList(new ArrayList<ITestListener>());

		final ThreadDeath e = new ThreadDeath();
		ThreadDeath actualException = Tests.assertThrows(ThreadDeath.class, new Runnable() {
			public void run() {
				list.fire(new ICallback<ITestListener>() {
					public void process(ITestListener t) throws Exception {
						actual.add(t);
						throw e;
					}
				});
			}
		});
		verify();
		assertEquals(e, actualException);
		assertEquals(Arrays.asList(mockL1C1), actual);
	}

	public void testIfImplementsValidThenIsValidIsCalled() {
		list.addListener(mockV1);
		list.addListener(mockV2);

		EasyMock.expect(mockV1.isValid()).andReturn(true);
		EasyMock.expect(mockV2.isValid()).andReturn(true);
		replay();

		final List<ITestListener> actual = Collections.synchronizedList(new ArrayList<ITestListener>());
		list.fire(new ICallback<ITestListener>() {
			public void process(ITestListener listener) throws Exception {
				actual.add(listener);
			}
		});
		verify();
		assertEquals(Arrays.asList(mockV1, mockV2), actual);
	}

	public void testIfValidReturnsFalseThenListenerIsNotCalledAndRemovedFromList() {
		list.addListener(mockV1);
		list.addListener(mockV2);

		EasyMock.expect(mockV1.isValid()).andReturn(false);
		EasyMock.expect(mockV2.isValid()).andReturn(true);
		replay();

		final List<ITestListener> actual = Collections.synchronizedList(new ArrayList<ITestListener>());
		list.fire(new ICallback<ITestListener>() {
			public void process(ITestListener listener) throws Exception {
				actual.add(listener);
			}
		});
		verify();
		assertEquals(Arrays.asList(mockV2), actual);
	}

	public void testGlobalListenersAreCalledWhenListenersAreFired() {
		ICallback<ITestListener> noCallback = ICallback.Utils.<ITestListener> noCallback();

		list.addListener(mockL1C1);
		list.addListener(mockL2C1);
		Object context = "context";
		setupGlobalListenerMock(0, globalListener1, context, noCallback, mockL1C1, mockL2C1);
		setupGlobalListenerMock(0, globalListener2, context, noCallback, mockL1C1, mockL2C1);
		replay();
		rawList.addGlobalListener(globalListener1);
		rawList.addGlobalListener(globalListener2);
		list.fire(noCallback);
		verify();
	}

	public void testAppropriateExecutorUsed() {
		final ICallback<ITestListener> callback = ICallback.Utils.noCallback();
		list.addListener(mockL1C1);
		list.addListener(mockV1);
		EasyMock.expect(mockV1.isValid()).andReturn(true);// just using the IValid as it is available
		final AtomicInteger count = new AtomicInteger();
		rawList.registerExecutor(IValid.class, new IMultipleListenerListExecutor() {
			@Override
			public <L> void execute(int eventId, ICallback<L> actualCallback, L listener, GlobalListenerList globalList, GlobalListenerContext contexts) {
				assertEquals(callback, actualCallback);
				assertEquals(mockV1, listener);
				count.incrementAndGet();
			}
		});
		replay();

		list.fire(callback);
		verify();
		assertEquals(1, count.get());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setupGlobalListenerMock(int eventiId, IMultipleListenerListListener globalListener, Object context, ICallback<ITestListener> callback, Object... listeners) {
		EasyMock.expect(globalListener.fireStarted(eventiId, source, ITestListener.class, callback)).andReturn(context);
		for (Object listener : listeners) {
			globalListener.listenerStarted(eventiId, context, listener);
			globalListener.listenerEnded(eventiId, context, listener);
		}
		globalListener.fireEnded(eventiId, context);
	}

	private void replay() {
		EasyMock.replay(mockL1C1, mockL2C1, mockL1C2, mockL2C2, mockV1, mockV2, mockInvalid1, mockInvalid2, globalListener1, globalListener2);
	}

	private void verify() {
		EasyMock.verify(mockL1C1, mockL2C1, mockL1C2, mockL2C2, mockV1, mockV2, mockInvalid1, mockInvalid2, globalListener1, globalListener2);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rawList = new MultipleListenerList();
		list = (ListenerList<ITestListener>) IListenerList.Utils.<ITestListener> list(rawList, ITestListener.class, source);

		mockL1C1 = EasyMock.createMock(ITestListener.class);
		mockL2C1 = EasyMock.createMock(ITestListener.class);

		mockL1C2 = EasyMock.createMock(ITestListener.class);
		mockL2C2 = EasyMock.createMock(ITestListener.class);

		mockInvalid1 = EasyMock.createMock(ITestListenerAndValid.class);
		mockInvalid2 = EasyMock.createMock(ITestListenerAndValid.class);

		mockV1 = EasyMock.createMock(ITestListenerAndValid.class);
		mockV2 = EasyMock.createMock(ITestListenerAndValid.class);

		globalListener1 = EasyMock.createMock(IMultipleListenerListListener.class);
		globalListener2 = EasyMock.createMock(IMultipleListenerListListener.class);

		rawList.addListener(source, Object.class, mockL1C2);
		rawList.addListener(source, Object.class, mockL2C2);

		rawList.addListener(invalidSource, ITestListener.class, mockInvalid1);
		rawList.addListener(invalidSource, ITestListener.class, mockInvalid2);
	}
}
