package org.softwarefm.utilities.strings.internal;

import junit.framework.TestCase;

import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

public class ByteArraySimpleStringTest extends TestCase {

	private IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
	private ISimpleString simpleString = ISimpleString.Utils.fromUtf8String("0123456789");

	public void testSubString() {
		checkSubString(0, 2, "01");
		checkSubString(2, 4, "2345");
		checkSubString(8, 2, "89");
		checkSubString(8, 4, "89");
	}

	private void checkSubString(int start, int length, String expected) {
		ISimpleString actual = simpleString.subString(poolStore, start, length);
		assertTrue(actual.asString("UTF-8"), ISimpleString.Utils.equivalent(actual, expected));

	}

}
