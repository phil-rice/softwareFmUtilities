package org.softwarefm.utilities.parser;

import java.util.concurrent.atomic.AtomicReference;

import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

import junit.framework.TestCase;

public class UtfStringIndentifierFieldSetterTest extends TestCase {

	private ISimpleString input = ISimpleString.Utils.fromUtf8String("abcIdentifier%one\"TWO");
	private int idStart = 3;
	private int oneStart = 14;
	private int twoStart = oneStart + 4;
	private ParserState parserState;

	final AtomicReference<ISimpleString> valueRef = new AtomicReference<ISimpleString>();
	UtfStringIndentifierFieldSetter<Object> setter = new UtfStringIndentifierFieldSetter<Object>() {
		@Override
		protected void setField(Object object, ISimpleString value) {
			assertEquals(parserState.thisObject, object);
			valueRef.set(value);
		}
	};

	public void test() {
		parserState.nextIndex = idStart;
		setter.setField(parserState);
		assertTrue(ISimpleString.Utils.equivalent(valueRef.get(), "Identifier"));
		assertEquals(oneStart - 1, parserState.nextIndex);

		parserState.nextIndex = oneStart;
		setter.setField(parserState);
		assertTrue(ISimpleString.Utils.equivalent(valueRef.get(), "one"));
		assertEquals(twoStart - 1, parserState.nextIndex);

		parserState.nextIndex = twoStart;
		setter.setField(parserState);
		assertTrue(valueRef.get().toString(), ISimpleString.Utils.equivalent(valueRef.get(), "TWO"));
		assertEquals(twoStart+3, parserState.nextIndex);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		parserState = new ParserState(IPoolStore.Utils.threadSafePoolStore(), 2, 2);
		parserState.input = input;
		parserState.thisObject = new Object();
	}
}
