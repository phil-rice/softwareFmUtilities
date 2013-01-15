package org.softwarefm.utilities.parser;

import java.util.List;

import org.softwarefm.utilities.arrays.LongArrayAndSize;
import org.softwarefm.utilities.pooling.IMutableSimpleList;
import org.softwarefm.utilities.pooling.internal.PooledList;

public interface IObjectsAndEdges {

	IMutableSimpleList<Object> objects();

	LongArrayAndSize parentToChildren();
	
	public static class Utils{
		
		@SuppressWarnings("rawtypes")
		public static IObjectsAndEdges objectsAndEdges(final List objects, final String...edges){
			return new IObjectsAndEdges() {
				
				@Override
				public LongArrayAndSize parentToChildren() {
					return new LongArrayAndSize(edges);
				}
				
				@Override
				public IMutableSimpleList<Object> objects() {
					PooledList<Object> result = new PooledList<Object>(objects.size());
					for (Object object: objects)
						result.add(object);
					return result;
				}
			};
		}
	}
}
