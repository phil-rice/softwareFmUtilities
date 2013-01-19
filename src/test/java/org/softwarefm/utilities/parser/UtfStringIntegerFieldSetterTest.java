package org.softwarefm.utilities.parser;

import java.util.concurrent.atomic.AtomicReference;

import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

import junit.framework.TestCase;

public class UtfStringIntegerFieldSetterTest extends TestCase {

	private ISimpleString input = ISimpleString.Utils.fromUtf8String("abc123%234\"3");
	private int start123 = 3;
	private int start234 = start123+4;
	private int start3 = start234 + 4;
	private ParserState parserState;

	final AtomicReference<Integer> valueRef = new AtomicReference<Integer>();
	UtfStringIntegerFieldSetter setter = new UtfStringIntegerFieldSetter() {

		@Override
		protected void setField(Object object, int value) {
			assertEquals(parserState.thisObject, object);
			valueRef.set(value);
			
		}
	};

	public void test() {
		parserState.nextIndex = start123;
		setter.setField(parserState);
		assertEquals(123, valueRef.get().intValue());
		assertEquals(start234 - 1, parserState.nextIndex);

		parserState.nextIndex = start234;
		setter.setField(parserState);
		assertEquals(234, valueRef.get().intValue());
		assertEquals(start3 - 1, parserState.nextIndex);

		parserState.nextIndex = start3;
		setter.setField(parserState);
		assertEquals(3, valueRef.get().intValue());
		assertEquals(start3 +1, parserState.nextIndex);

	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		parserState = new ParserState(IPoolStore.Utils.threadSafePoolStore(), 2, 2);
		parserState.input = input;
		parserState.thisObject = new Object();
	}
}
