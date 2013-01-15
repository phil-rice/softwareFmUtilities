package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

public interface IParser {

	/** returns true if matches. Often will throw exception if doesn't match, unless it is allowed to not match (for example objects with cardinality optional / many */
	boolean parse(ParserState state);

	public static class Utils {

		public static IObjectDefinition<ParserState> parserStateObjectDefinition(int maxDepth, int maxObjects) {
			return new ParserStateObjectDefinition(maxDepth, maxObjects);
		}

		public static ParserState parseUtfString(IPoolStore poolStore, IObjectDefinition<ParserState> parserStateObjectDefinition, IParser parser, String input) {
			return parse(poolStore, parserStateObjectDefinition, parser, ISimpleString.Utils.fromUtf8String(input));

		}

		public static ParserState parse(IPoolStore poolStore, IObjectDefinition<ParserState> parserStateObjectDefinition, IParser parser, ISimpleString input) {
			return parse(poolStore, parserStateObjectDefinition, parser, input, true);
		}
		public static ParserState parse(IPoolStore poolStore, IObjectDefinition<ParserState> parserStateObjectDefinition, IParser parser, ISimpleString input, boolean check) {
			ParserState parserState = poolStore.pool(parserStateObjectDefinition).get(poolStore);
			parserState.checkMarkers = check;
			parserState.populate(input);
			parser.parse(parserState);
			return parserState;
		}
	}

}