package org.softwarefm.utilities.parser.internal;

import junit.framework.TestCase;

import org.softwarefm.utilities.arrays.LongArrayAndSize;
import org.softwarefm.utilities.library.Book;
import org.softwarefm.utilities.library.Library;
import org.softwarefm.utilities.library.LibraryParser;
import org.softwarefm.utilities.parser.IParser;
import org.softwarefm.utilities.parser.ParserState;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.tests.Tests;

public class ParserBuilderTest extends TestCase {

	private final IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
	private final IObjectDefinition<ParserState> parserStateDefn = IParser.Utils.parserStateObjectDefinition(2, 3);

	public void testOneObjectWithAField() {
		IParser parser = LibraryParser.makeParser();
		ParserState parserState = IParser.Utils.parseUtfString(poolStore, parserStateDefn, parser, LibraryParser.zeroBooks);

		Tests.assertEquals(parserState.objects, new Library("libName"));
		assertEquals(new LongArrayAndSize(0), parserState.parentToChildren);
	}

	public void testObjectWithChild() {
		IParser parser = LibraryParser.makeParser();
		ParserState parserState = IParser.Utils.parseUtfString(poolStore, parserStateDefn, parser, LibraryParser.oneBook);
		Tests.assertEquals(parserState.objects, new Library("libName"), new Book("bkName1", "bkDesc1"));
		assertEquals(new LongArrayAndSize("0:1"), parserState.parentToChildren);
	}

	public void testObjectWithTwoChildren() {
		IParser parser = LibraryParser.makeParser();
		ParserState parserState = IParser.Utils.parseUtfString(poolStore, parserStateDefn, parser, LibraryParser.twoBooks);
		Tests.assertEquals(parserState.objects, new Library("libName"), new Book("bkName1", "bkDesc1"), new Book("bkName2", "bkDesc2"));
		assertEquals(new LongArrayAndSize("0:1", "0:2"), parserState.parentToChildren);
	}

}
