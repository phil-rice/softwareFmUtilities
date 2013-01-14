package org.softwarefm.utilities.strings;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

import junit.framework.TestCase;

public class ISimpleStringTest extends TestCase {

	private final static ISimpleString empty = ISimpleString.Utils.empty;
	private final static String UTF8 = "UTF-8"; 

	public void testEmpty() {
		assertEquals(0, empty.length());
		assertEquals("", empty.asString(UTF8));
	}

	public void testEquivalent() throws UnsupportedEncodingException {
		assertTrue(ISimpleString.Utils.equivalent(empty, ""));
		assertTrue(ISimpleString.Utils.equivalent("", empty));
		ISimpleString oneToFour = ISimpleString.Utils.fromBytes("0123456789ABCDEF".getBytes(UTF8), 1, 4);
		assertTrue(oneToFour.asString(UTF8), ISimpleString.Utils.equivalent(oneToFour, "1234"));
		assertFalse(oneToFour.asString(UTF8), ISimpleString.Utils.equivalent(oneToFour, "12345"));
		assertFalse(oneToFour.asString(UTF8), ISimpleString.Utils.equivalent(oneToFour, "123X"));
	}

	public void testFromByteArray() throws UnsupportedEncodingException {
		byte[] bytes = "0123456789ABCDEF".getBytes(UTF8);
		ISimpleString oneToFour = ISimpleString.Utils.fromBytes(bytes, 1, 4);
		assertTrue(oneToFour.asString(UTF8), ISimpleString.Utils.equivalent(oneToFour, "1234"));
	}

	public void testFromByteBuffer() throws UnsupportedEncodingException {
		byte[] bytes = "0123456789ABCDEF".getBytes(UTF8);
		ByteBuffer buffer = MappedByteBuffer.allocate(bytes.length);
		buffer.put(bytes);
		ISimpleString oneToFour = ISimpleString.Utils.fromByteBuffer(buffer, 1, 4);
		assertTrue(oneToFour.asString(UTF8), ISimpleString.Utils.equivalent(oneToFour, "1234"));
	}
}
