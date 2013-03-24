package org.softwarefm.utilities.cache;

public class StaleCacheStrategyForTest <K>implements IStaleCacheStrategy<K>{

	private boolean stale;

	@Override
	public boolean isStale(K key) {
		return stale;
	}
	
	public void setStale(boolean stale) {
		this.stale = stale;
	}

}
