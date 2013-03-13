package org.softwarefm.utilities.eventbus.internal;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.softwarefm.utilities.callbacks.ICallback2;
import org.softwarefm.utilities.eventbus.IEventBus;
import org.softwarefm.utilities.eventbus.IEventBusWithExecutors;
import org.softwarefm.utilities.eventbus.IQueue;

abstract public class AbstractEventBusTest extends TestCase {

	protected IEventBusWithExecutors eventBus;
	protected IQueueTest1 listener1_1;
	protected IQueueTest1 listener1_2;
	protected IQueueTest2 listener2_1;
	protected IQueueTest2 listener2_2;
	protected IQueueTest12 listener12_1;
	protected IQueueTest12 listener12_2;
	protected final static String param = "param";

	protected ICallback2<IQueueTest1, Object> queue1Callback = new ICallback2<IQueueTest1, Object>() {
		@Override
		public void process(IQueueTest1 queue, Object param) throws Exception {
			queue.queue1(param);
		}
	};
	protected ICallback2<IQueueTest2, Object> queue2Callback = new ICallback2<IQueueTest2, Object>() {
		@Override
		public void process(IQueueTest2 queue, Object param) throws Exception {
			queue.queue2(param);
		}
	};

	protected void replay(Object...moreMocks) {
		EasyMock.replay(listener1_1, listener1_2, listener2_1, listener2_2, listener12_1, listener12_2);
		for (Object mock: moreMocks)
			EasyMock.replay(mock);
	}

	protected void verify(Object...moreMocks) {
		EasyMock.verify(listener1_1, listener1_2, listener2_1, listener2_2, listener12_1, listener12_2);
		for (Object mock: moreMocks)
			EasyMock.verify(mock);
	}

	protected void addQueues(boolean add1, boolean add2) {
		if (add1)
			eventBus.registerQueue(IQueueTest1.class, queue1Callback);
		if (add2)
			eventBus.registerQueue(IQueueTest2.class, queue2Callback);
	}

	protected void addListeners() {
		eventBus.registerListener(listener1_1);
		eventBus.registerListener(listener1_2);
		eventBus.registerListener(listener2_1);
		eventBus.registerListener(listener2_2);
		eventBus.registerListener(listener12_1);
		eventBus.registerListener(listener12_2);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		eventBus = IEventBus.Utils.eventBus();
		listener1_1 = EasyMock.createMock(IQueueTest1.class);
		listener1_2 = EasyMock.createMock(IQueueTest1.class);
		listener2_1 = EasyMock.createMock(IQueueTest2.class);
		listener2_2 = EasyMock.createMock(IQueueTest2.class);
		listener12_1 = EasyMock.createMock(IQueueTest12.class);
		listener12_2 = EasyMock.createMock(IQueueTest12.class);
	}

}

interface IQueueTest1 extends IQueue {
	void queue1(Object param);
}

interface IQueueTest2 extends IQueue {
	void queue2(Object param);
}

interface IQueueTest12 extends IQueueTest1, IQueueTest2 {

}