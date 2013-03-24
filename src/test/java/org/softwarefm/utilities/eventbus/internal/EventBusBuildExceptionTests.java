package org.softwarefm.utilities.eventbus.internal;

import org.softwarefm.utilities.callbacks.ICallback2;
import org.softwarefm.utilities.eventbus.IEventBus;
import org.softwarefm.utilities.exceptions.CannotAddDuplicateKeyException;
import org.softwarefm.utilities.tests.Tests;

public class EventBusBuildExceptionTests extends AbstractEventBusTest {

	public void testThrowsCannotChangeEventBusWhenNotifyingException() {
		checkThrowsCannotNotify(new Runnable() {
			public void run() {
				eventBus.registerListener(new Object());
			}
		});
		checkThrowsCannotNotify(new Runnable() {
			public void run() {
				eventBus.registerQueue(IQueueTest2.class, ICallback2.Utils.<IQueueTest2, Object> noCallback());
			}
		});
		checkThrowsCannotNotify(new Runnable() {
			public void run() {
				eventBus.registerListener(new Object());
			}
		});
		checkThrowsCannotNotify(new Runnable() {
			public void run() {
				eventBus.registerExecutor(Object.class, new EventBusSimpleExecutor());
			}
		});
	}

	private void checkThrowsCannotNotify(final Runnable runnable) {
		eventBus = IEventBus.Utils.eventBus();
		eventBus.registerQueue(IQueueTest1.class, new ICallback2<IQueueTest1, Object>() {
			@Override
			public void process(IQueueTest1 queue, Object param) throws Exception {
				queue.queue1(param);
				throw Tests.assertThrows(CannotChangeEventBusWhileNotifyingException.class, runnable);
			}

		});
		eventBus.registerListener(listener1_1);
		Tests.assertThrows(CannotChangeEventBusWhileNotifyingException.class, new Runnable() {
			public void run() {
				eventBus.notify(IQueueTest1.class, param);
			}
		});
	}

	public void testAddingQueueSecondTimeThrowsException() {
		eventBus.registerQueue(IQueueTest2.class, ICallback2.Utils.<IQueueTest2, Object> noCallback());
		Tests.assertThrows(CannotAddDuplicateKeyException.class, new Runnable() {
			public void run() {
				eventBus.registerQueue(IQueueTest2.class, ICallback2.Utils.<IQueueTest2, Object> noCallback());
			}
		});
	}
}
