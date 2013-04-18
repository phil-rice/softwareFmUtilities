package org.softwarefm.utilities.clone;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.softwarefm.utilities.clone.internal.Cloner;
import org.softwarefm.utilities.exceptions.CannotCloneException;

public interface ICloner extends ICloneObject<Object> {

	<T> void register(Class<T> clazz, ICloneObject<T> cloneObject);

	public static class Utils {
		public static ICloner cloner() {
			return new Cloner();
		}
		public static ICloner clonerWithUseClone() {
			Cloner cloner = new Cloner();
			cloner.register(Object.class, useClone());
			return cloner;
		}

		public static ICloneObject<Object> useClone() {
			return new ICloneObject<Object>() {
				@Override
				public Object makeCopyof(Object t) {
					Class<? extends Object> class1 = t.getClass();
					try {
						Method method = class1.getMethod("clone");
						Object result = method.invoke(t);
						return result;
					} catch (Exception e) {
						throw new CannotCloneException(class1, e);
					}
				}
			};
		}

		public static ICloneObject<Object> copy() {
			return new ICloneObject<Object>() {
				@Override
				public Object makeCopyof(Object t) {
					return t;
				}
			};
		}

		public static ICloneObject<Object> copyConstructor() {
			return new ICloneObject<Object>() {
				@Override
				public Object makeCopyof(Object t) {
					Class<? extends Object> class1 = t.getClass();
					try {
						Constructor<? extends Object> constructor = class1.getConstructor(class1);
						Object result = constructor.newInstance(t);
						return result;
					} catch (Exception e) {
						throw new CannotCloneException(class1, e);
					}
				}
			};
		}
		public static Object[] copyArray(ICloner cloner, Object[] inputs) {
			Object[] result = new Object[inputs.length];
			for (int i = 0; i<inputs.length; i++)
				result[i] = cloner.makeCopyof(inputs[i]);
			return result;
		}
	}
}
