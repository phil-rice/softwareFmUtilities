package org.softwarefm.utilities.maps;

public interface IMutableSimpleMap<K, V> extends ISimpleMap<K, V> {
	void put(K key, V value);
	
	void clear();
	public static class Utils{
		public static <K,V>IMutableSimpleMap<K, V> map(int maxSize){
			return new MutableSimpleMap<K, V>(maxSize);
		}
	}
}
