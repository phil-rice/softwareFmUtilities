package org.softwarefm.utilities.strings;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.softwarefm.utilities.annotations.Slow;
import org.softwarefm.utilities.exceptions.WrappedException;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.internal.ByteArraySimpleString;
import org.softwarefm.utilities.strings.internal.ByteBufferSimpleString;

/** This is a very simple string. It is small and poolable */
public interface ISimpleString {

	int length();

	byte byteAt(int offset);

	@Slow("This will make a new object")
	String asString(String encoding);
	
	ISimpleString subString(IPoolStore poolStore, int offset, int length);

	static class Utils {

		public static boolean equivalent(ISimpleString simpleString, String string) {
			if (simpleString == null || string == null)
				return simpleString == null && string == null;
			int length = simpleString.length();
			if (length != string.length())
				return false;
			for (int i = 0; i < length; i++)
				if (simpleString.byteAt(i) != string.charAt(i))
					return false;
			return true;
		}
		public static boolean equivalent(ISimpleString simpleString, ISimpleString string) {
			if (simpleString == null || string == null)
				return simpleString == null && string == null;
			int length = simpleString.length();
			if (length != string.length())
				return false;
			for (int i = 0; i < length; i++)
				if (simpleString.byteAt(i) != string.byteAt(i))
					return false;
			return true;
		}

		public static final ISimpleString empty = new ISimpleString() {

			@Override
			public int length() {
				return 0;
			}

			@Override
			public byte byteAt(int offset) {
				throw new IndexOutOfBoundsException(Integer.toString(offset));
			}

			@Override
			public String asString(String encoding) {
				return "";
			}

			@Override
			public ISimpleString subString(IPoolStore poolStore, int offset, int length) {
				return this;
			}
		};

		public static boolean equivalent(String string, ISimpleString simpleString) {
			if (string == null || simpleString == null)
				return string == null && simpleString == null;
			if (string.length() != simpleString.length())
				return false;
			for (int i = 0; i < string.length(); i++)
				if ((byte) string.charAt(i) != simpleString.byteAt(i))
					return false;
			return true;
		}

		public static ISimpleString fromBytes(byte[] bytes, int from, int size) {
			ByteArraySimpleString result = new ByteArraySimpleString();
			result.populate(bytes, from, size);
			return result;
		}

		public static ISimpleString fromByteBuffer(ByteBuffer buffer, int start, int length) {
			ByteBufferSimpleString result = new ByteBufferSimpleString();
			result.populate(buffer, start, length);
			return result;
		}

		public static ISimpleString fromUtf8String(String string) {
			try {
				byte[] bytes = string.getBytes("UTF-8");
				return fromBytes(bytes, 0, bytes.length);
			} catch (UnsupportedEncodingException e) {
				throw WrappedException.wrap(e);
			}
		}

	}

}
