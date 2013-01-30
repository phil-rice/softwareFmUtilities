package org.softwarefm.utilities.threading;

import java.util.concurrent.Callable;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.softwarefm.utilities.lifecycle.IDispose;

public class DoubleCheckedCreatorTest extends TestCase {

	private Object lock = new Object();
	private Callable<Object> callable;
	private Object data = new Object();
	private Object data2 = new Object();
	private IDispose dispose1;
	private DoubleCheckedCreator<Object> doubleCheckedCreator;

	public void testGetReturnsObjectFromCreator() throws Exception {
		EasyMock.expect(callable.call()).andReturn(data);
		replay();
		assertSame(data, doubleCheckedCreator.get());
		assertSame(data, doubleCheckedCreator.get());
		assertSame(data, doubleCheckedCreator.get());
	}

	public void testDisposeDoesntCrashIfNeverCalled() {
		replay();
		doubleCheckedCreator.dispose();
	}

	public void testGetNewObjectAfterDispose() throws Exception {
		EasyMock.expect(callable.call()).andReturn(data);
		EasyMock.expect(callable.call()).andReturn(data2);
		replay();
		assertSame(data, doubleCheckedCreator.get());
		doubleCheckedCreator.dispose();
		assertSame(data2, doubleCheckedCreator.get());
		assertSame(data2, doubleCheckedCreator.get());
	}

	public void testAlsoDisposesDataIfCan() throws Exception {
		EasyMock.expect(callable.call()).andReturn(dispose1);
		dispose1.dispose();
		replay();
		assertSame(dispose1, doubleCheckedCreator.get());
		doubleCheckedCreator.dispose();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		callable = EasyMock.createMock(Callable.class);
		doubleCheckedCreator = new DoubleCheckedCreator<Object>(lock, callable);
		dispose1 = EasyMock.createMock(IDispose.class);
	}

	private void replay() {
		EasyMock.replay(callable, dispose1);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		EasyMock.verify(callable, dispose1);
	}
}
