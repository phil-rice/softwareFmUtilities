package org.softwarefm.utilities.parser.internal;

import java.text.MessageFormat;

import junit.framework.TestCase;

import org.softwarefm.utilities.collections.ISimpleList;
import org.softwarefm.utilities.parser.IParser;
import org.softwarefm.utilities.parser.IParserBuilder;
import org.softwarefm.utilities.parser.UtfStringIndentifierFieldSetter;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

public class ParserBuilderTest extends TestCase {
	private final String UTF8 = "UTF-8";

	private final String libraryPattern = "<library name=''{0}''>{1}</library>";
	private final String bookPattern = "<book name=''{0}'' description=''{1}'' />";
	private final String zeroBooks = MessageFormat.format(libraryPattern, "libName", "");
	private final String oneBook = MessageFormat.format(libraryPattern, "libName", MessageFormat.format(bookPattern, "bkName1", "bkDesc1"));
	private final String twoBooks = MessageFormat.format(libraryPattern, "libName", MessageFormat.format(bookPattern, "bkName1", "bkDesc1") + MessageFormat.format(bookPattern, "bkName2", "bkDesc2"));
	private final IPoolStore poolStore = IPoolStore.Utils.threadSafePoolStore();
private final IObjectDefinition<ParserState> parserStateDefn = IParser.Utils.parserStateObjectDefinition(2, 2, 2);
	
	public void testOneObjectWithAField() {
		IParser parser = makeParser();
		ParserState parserState = IParser.Utils.parseUtfString(poolStore, parserStateDefn, parser, zeroBooks);

		Library library = parserState.<Library> root();
		assertEquals("libName", library.name);
		ISimpleList<Book> books = parserState.<Library, Book> children(library);
		assertEquals(null, books);
	}

	public void testObjectWithChild() {
		IParser parser = makeParser();
		ParserState parserState = IParser.Utils.parseUtfString(poolStore, parserStateDefn, parser, oneBook);

		Library library = parserState.<Library> root();
		assertEquals("libName", library.name);
		ISimpleList<Book> books = parserState.<Library, Book> children(library);
		assertEquals(1, books.size());
		Book book = books.get(0);
		assertEquals("bkName1", book.name);
		assertEquals("bkDesc1", book.description);
	}

	public void testObjectWithTwoChildren() {
		IParser parser = makeParser();
		ParserState parserState = IParser.Utils.parseUtfString(poolStore, parserStateDefn, parser, twoBooks);

		Library library = parserState.<Library> root();
		assertEquals("libName", library.name);
		ISimpleList<Book> books = parserState.<Library, Book> children(library);
		assertEquals(2, books.size());
		Book book1 = books.get(0);
		assertEquals("bkName1", book1.name);
		assertEquals("bkDesc1", book1.description);
		Book book2 = books.get(1);
		assertEquals("bkName2", book2.name);
		assertEquals("bkDesc2", book2.description);
	}

	private IParser makeParser() {
		IParserBuilder builder = IParserBuilder.Utils.builder(UTF8);
		builder.startObjectMarker("<library ", new LibraryDefn());
		builder.field("name='", new UtfStringIndentifierFieldSetter<Library>() {
			@Override
			protected void setField(Library object, ISimpleString value) {
				object.name = value.asString(UTF8);
			}
		}, "'>");
		builder.startObjectMarker("<book ", new BookDefn());
		builder.field("name='", new UtfStringIndentifierFieldSetter<Book>() {
			@Override
			protected void setField(Book object, ISimpleString value) {
				object.name = value.asString(UTF8);
			}
		}, "' ");
		builder.field("description='", new UtfStringIndentifierFieldSetter<Book>() {
			@Override
			protected void setField(Book object, ISimpleString value) {
				object.description = value.asString(UTF8);
			}
		}, "'");
		builder.endObjectMarker(" />");
		return builder.endObjectMarker("</library>");
	}

	private final class BookDefn implements IObjectDefinition<Book> {
		@Override
		public Class<Book> objectClass() {
			return Book.class;
		}

		@Override
		public Book createBlank(IPoolStore poolStore) {
			return new Book();
		}

		@Override
		public void clear(IPoolStore poolStore, Book oldObject) {
			oldObject.name = "";
			oldObject.description = "";
		}
	}

	private final class LibraryDefn implements IObjectDefinition<Library> {
		@Override
		public Class<Library> objectClass() {
			return Library.class;
		}

		@Override
		public Library createBlank(IPoolStore poolStore) {
			return new Library();
		}

		@Override
		public void clear(IPoolStore poolStore, Library oldObject) {
			oldObject.name = "";
		}
	}

	public static class Library {
		public String name;
	}

	public static class Book {
		public String name;
		public String description;

	}
}
