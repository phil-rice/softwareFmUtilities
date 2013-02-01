package org.softwarefm.utilities.events.internal;

import junit.framework.TestCase;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.callbacks.MemoryCallback;
import org.softwarefm.utilities.events.IListenerList;
import org.softwarefm.utilities.events.internal.ListenerListTest.ITestListener;
import org.softwarefm.utilities.tests.Tests;

public class LoggingListenerTest extends TestCase {

	private MultipleListenerList rawList;
	private ListenerList<ITestListener> list;
	private ITestListener listener1;
	private ITestListener listener2;
	private MemoryCallback<String> memory;
	private final Object source = "src";

	public void testLogger() {
		list.addListener(listener1);
		list.addListener(listener2);
		rawList.addGlobalListener(new LoggingListener(memory));

		list.fire(ICallback.Utils.<ITestListener> noCallback());
		list.fire(ICallback.Utils.<ITestListener> noCallback());

		Tests.assertListEquals(memory.getResults(), //
				"fireStarted(0, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback)",//
				"  listenerStarted(0, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l1)",//
				"  listenerEnded(0, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l1)",//
				"  listenerStarted(0, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l2)",//
				"  listenerEnded(0, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l2)",//
				"fireEnded(0, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback)",//
				"fireStarted(1, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback)",//
				"  listenerStarted(1, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l1)",//
				"  listenerEnded(1, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l1)",//
				"  listenerStarted(1, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l2)",//
				"  listenerEnded(1, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback, l2)",//
				"fireEnded(1, src, interface org.softwarefm.utilities.events.internal.ListenerListTest$ITestListener, NoCallback)");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rawList = new MultipleListenerList();
		list = (ListenerList<ITestListener>) IListenerList.Utils.<ITestListener> list(rawList, ITestListener.class, source);

		listener1 = new TestListener("l1");
		listener2 = new TestListener("l2");
		memory = ICallback.Utils.memory();
	}

}
