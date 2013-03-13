package org.softwarefm.utilities.eventbus.internal;

import org.easymock.EasyMock;
import org.softwarefm.utilities.eventbus.IEventBusExecutor;

public class EventBusWithExecutorsTest extends AbstractEventBusTest {

	private IMarkerWithTestQueue1 listenerWithmarker;
	private IEventBusExecutor executor;

	public void testUsesExecutorIfListenerExtendsMarkerInterface() {
		executor.execute(queue1Callback, listenerWithmarker, param);
		replay(listenerWithmarker, executor);
		addQueues(true, false);
		eventBus.registerListener(listenerWithmarker);
		eventBus.registerExecutor(IMarkerForTest.class, executor);
		eventBus.notify(IQueueTest1.class, param);
		EasyMock.verify(listenerWithmarker, executor);
		verify();
	}

	public void testUsesNormalExecutorIfListenerDoesntExtendsMarkerInterface() {
		listener1_1.queue1(param);
		replay(listenerWithmarker, executor);
		addQueues(true, false);
		eventBus.registerListener(listener1_1);
		eventBus.registerExecutor(IMarkerForTest.class, executor);
		eventBus.notify(IQueueTest1.class, param);
		EasyMock.verify(listenerWithmarker, executor);
		verify();
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		executor = EasyMock.createMock(IEventBusExecutor.class);
		listenerWithmarker = EasyMock.createMock(IMarkerWithTestQueue1.class);
	}
}

interface IMarkerForTest {

}

interface IMarkerWithTestQueue1 extends IQueueTest1, IMarkerForTest {

}
