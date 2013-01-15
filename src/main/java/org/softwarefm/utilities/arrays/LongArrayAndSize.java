package org.softwarefm.utilities.arrays;

import java.util.List;

import org.softwarefm.utilities.strings.Strings;

public class LongArrayAndSize {

	private int size;
	public long[] data;

	public LongArrayAndSize(int maxSize) {
		this.data = new long[maxSize];
	}

	public LongArrayAndSize(long... data) {
		this.data = new long[data.length];
		for (long l : data)
			add(l);
	}

	public LongArrayAndSize(String... data) {
		this.data = new long[data.length];
		for (String s : data) {
			List<String> split = Strings.splitIgnoreBlanks(s, ":");
			switch (split.size()) {
			case 0:
				break;
			case 1:
				add(Long.parseLong(s));
				break;
			case 2:
				add(Integer.parseInt(split.get(0)), Integer.parseInt(split.get(1)));
				break;
			default:
				throw new IllegalArgumentException("cannot parser " + s);
			}

		}
	}

	public void add(long l) {
		data[size++] = l;
	}

	public void add(int hi, int lo) {
		long top = ((long) hi) << 32;
		data[size++] = top + lo;
	}

	public long get(int i) {
		checkIndex(i);
		return data[i];
	}

	public int getHi(int i) {
		checkIndex(i);
		return (int) (data[i] >> 32);
	}

	public int getLo(int i) {
		checkIndex(i);
		return (int) (data[i]);
	}

	private void checkIndex(int i) {
		if (i >= size)
			throw new IndexOutOfBoundsException("Index is " + i + " size is " + size);

	}

	public void clear() {
		size = 0;
	}

	public int size() {
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (int i = 0; i < Math.max(size, 10); i++)
			result = prime * result + (int) data[i];
		result = prime * result + size;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LongArrayAndSize other = (LongArrayAndSize) obj;
		if (size != other.size)
			return false;
		for (int i = 0; i < size; i++)
			if (data[i] != other.data[i])
				return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < Math.min(20, size); i++) {
			if (builder.length() > 0)
				builder.append(",");
			builder.append(getHi(i));
			builder.append(":");
			builder.append(getLo(i));
		}
		return getClass().getSimpleName() + "[size = " + size + ", data=" + builder + "]";
	}

}
