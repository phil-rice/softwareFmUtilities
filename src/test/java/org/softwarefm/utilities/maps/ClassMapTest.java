package org.softwarefm.utilities.maps;

import junit.framework.TestCase;

public class ClassMapTest extends TestCase {

	public void testGetReturnPutTakingIntoInheritanceAndOrder() {
		ClassMap<String> map = new ClassMap<String>();
		map.put(String.class, "s");
		map.put(Integer.class, "i");
		map.put(Number.class, "n");
		map.put(Double.class, "d");
		
		assertEquals("s", map.get(String.class));
		assertEquals("i", map.get(Integer.class));
		assertEquals("n", map.get(Number.class));
		assertEquals("n", map.get(Double.class));
		assertEquals("n", map.get(Float.class));
		assertEquals(null, map.get(Object.class));
	}

}
