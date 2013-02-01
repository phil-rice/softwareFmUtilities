package org.softwarefm.utilities.events.internal;

import org.softwarefm.utilities.callbacks.ICallback;
import org.softwarefm.utilities.events.IMultipleListenerList;
import org.softwarefm.utilities.events.internal.ListenerListTest.ITestListener;

public class ProfilingListenerPersonUnit {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		IMultipleListenerList list = IMultipleListenerList.Utils.defaultList();
		list.addGlobalListener(IMultipleListenerList.Utils.sysoutProfiler());
		Object source = "src";
		list.addListener(source, ITestListener.class, new TestListener("l1"));
		list.addListener(source, ITestListener.class, new TestListener("l2"));
		for (int i = 0; i < 20; i++)
			list.fire(source, ITestListener.class, ICallback.Utils.noCallback);
	}
}
