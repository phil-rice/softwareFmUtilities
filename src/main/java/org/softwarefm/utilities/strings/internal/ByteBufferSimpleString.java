package org.softwarefm.utilities.strings.internal;

import java.nio.ByteBuffer;

public class ByteBufferSimpleString extends ByteArraySimpleString {

	public void populate(ByteBuffer byteBuffer, int start, int length) {
		populate(byteBuffer == null ? null : byteBuffer.array(), start, length);
	}

}
