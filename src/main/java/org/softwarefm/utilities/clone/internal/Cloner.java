package org.softwarefm.utilities.clone.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.softwarefm.utilities.clone.ICloneObject;
import org.softwarefm.utilities.clone.ICloner;
import org.softwarefm.utilities.exceptions.CannotCloneException;

public class Cloner implements ICloner {

	private final Map<Class<?>, ICloneObject<?>> map = new LinkedHashMap<Class<?>, ICloneObject<?>>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Object makeCopyof(Object t) {
		if (t == null)
			return null;
		ICloneObject cloner = findCloner(t);
		return cloner.makeCopyof(t);
	}

	private ICloneObject<?> findCloner(Object t) {
		for (Entry<Class<?>, ICloneObject<?>> entry : map.entrySet()) {
			if (entry.getKey().isAssignableFrom(t.getClass()))
				return entry.getValue();
		}
		throw new CannotCloneException(t.getClass());
	}

	@Override
	public <T> void register(Class<T> clazz, ICloneObject<T> cloneObject) {
		map.put(clazz, cloneObject);
	}

}
