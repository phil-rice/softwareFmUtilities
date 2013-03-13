package org.softwarefm.utilities.eventbus.internal;

import org.easymock.EasyMock;
import org.softwarefm.utilities.tests.Tests;

public class EventBusTest extends AbstractEventBusTest {


	public void testCanAddListenerAndItIsNotifedIfItImplementsQueue() {
		addQueues(true, false);
		listener1_1.queue1(param);
		replay();
		eventBus.registerListener(listener1_1);
		eventBus.notify(IQueueTest1.class, param);
		verify();
	}
	public void testOrderOfAddingQueueAndListenerNotImportant() {
		eventBus.registerListener(listener1_1);
		addQueues(true, false);
		listener1_1.queue1(param);
		replay();
		eventBus.notify(IQueueTest1.class, param);
		verify();
	}

	public void testCanAddListenerAndItIsNotNotifedIfItDoesntImplementsQueue() {
		addQueues(true, false);
		replay();
		eventBus.registerListener(listener2_1);
		eventBus.notify(IQueueTest1.class, param);
		verify();
	}

	public void testAllImplementingListenersAreCalled() {
		addQueues(true, false);
		listener1_1.queue1(param);
		listener1_2.queue1(param);
		listener12_1.queue1(param);
		listener12_2.queue1(param);
		replay();
		addListeners();
		eventBus.notify(IQueueTest1.class, param);
		verify();
	}

	public void testExceptionsStopQueueExecutionAndThrowsTheThrownException() {
		RuntimeException runtimeException = new RuntimeException();
		addQueues(true, false);
		listener1_1.queue1(param);
		listener1_2.queue1(param);
		EasyMock.expectLastCall().andThrow(runtimeException);
		replay();
		addListeners();
		assertSame(runtimeException, Tests.assertThrows(RuntimeException.class, new Runnable() {
			public void run() {
				eventBus.notify(IQueueTest1.class, param);
			}
		}));
		verify();
	}


}
