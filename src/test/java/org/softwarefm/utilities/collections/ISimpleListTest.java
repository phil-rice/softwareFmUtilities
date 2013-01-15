package org.softwarefm.utilities.collections;

import java.util.Arrays;

import junit.framework.TestCase;

import org.softwarefm.utilities.pooling.IMutableSimpleList;

public class ISimpleListTest extends TestCase {

	public void testIndexOf() {
		ISimpleList<String> list = IMutableSimpleList.Utils.fromList(Arrays.asList("0", "1", "2", "3"));
		assertEquals(0, ISimpleList.Utils.indexOf(list, "0"));
		assertEquals(1, ISimpleList.Utils.indexOf(list, "1"));
		assertEquals(2, ISimpleList.Utils.indexOf(list, "2"));
		assertEquals(3, ISimpleList.Utils.indexOf(list, "3"));
		assertEquals(-1, ISimpleList.Utils.indexOf(list, "notIn"));
		assertEquals(-1, ISimpleList.Utils.indexOf(list, null));
	}
	
	public void testWithNullInList(){
		ISimpleList<String> list = IMutableSimpleList.Utils.fromList(Arrays.asList("0", null, "2", "3"));
		assertEquals(1, ISimpleList.Utils.indexOf(list, null));
	}

}
