package org.softwarefm.utilities.library;

import java.text.MessageFormat;

import org.softwarefm.utilities.parser.IParser;
import org.softwarefm.utilities.parser.IParserBuilder;
import org.softwarefm.utilities.parser.UtfStringIndentifierFieldSetter;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

public class LibraryParser {

	private static final String libraryEncoding = "UTF-8";
	public final static  String libraryPattern = "<library name=''{0}''>{1}</library>";
	public final  static String bookPattern = "<book name=''{0}'' description=''{1}'' />";
	public final  static String zeroBooks = MessageFormat.format(libraryPattern, "libName", "");
	public final  static String oneBook = MessageFormat.format(libraryPattern, "libName", MessageFormat.format(bookPattern, "bkName1", "bkDesc1"));
	public final  static String twoBooks = MessageFormat.format(libraryPattern, "libName", MessageFormat.format(bookPattern, "bkName1", "bkDesc1") + MessageFormat.format(bookPattern, "bkName2", "bkDesc2"));

	public static IParser makeParser() {
		IParserBuilder builder = IParserBuilder.Utils.builder(libraryEncoding);
		builder.startObjectMarker("<library ", LibraryDefn);
		builder.field("name='", new UtfStringIndentifierFieldSetter<Library>() {
			@Override
			protected void setField(Library object, ISimpleString value) {
				object.name = value;
			}
		}, "'>");
		builder.startObjectMarker("<book ", bookDefn);
		builder.field("name='", new UtfStringIndentifierFieldSetter<Book>() {
			@Override
			protected void setField(Book object, ISimpleString value) {
				object.name = value;
			}
		}, "' ");
		builder.field("description='", new UtfStringIndentifierFieldSetter<Book>() {
			@Override
			protected void setField(Book object, ISimpleString value) {
				object.description = value;
			}
		}, "'");
		builder.endObjectMarker(" />");
		return builder.endObjectMarker("</library>");
	}

	public static final IObjectDefinition<Book> bookDefn = new IObjectDefinition<Book>() {
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
			oldObject.name = null;
			oldObject.description = null;
		}
	};

	public static final IObjectDefinition<Library> LibraryDefn = new IObjectDefinition<Library>() {
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
			oldObject.name = null;
		}
	};
}
