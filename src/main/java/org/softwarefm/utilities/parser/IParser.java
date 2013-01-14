package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.parser.internal.ParserState;
import org.softwarefm.utilities.parser.internal.ParserStateObjectDefinition;
import org.softwarefm.utilities.pooling.IObjectDefinition;
import org.softwarefm.utilities.pooling.IPoolStore;
import org.softwarefm.utilities.strings.ISimpleString;

public interface IParser {

	/** returns true if matches. Often will throw exception if doesn't match, unless it is allowed to not match (for example objects with cardinality optional / many */
	boolean parse(ParserState state);

	public static class Utils {

		public static IObjectDefinition<ParserState> parserStateObjectDefinition(int maxDepth, int maxParents, int maxChildren) {
			return new ParserStateObjectDefinition(maxDepth, maxParents, maxChildren);
		}

		public static ParserState parseUtfString(IPoolStore poolStore, IObjectDefinition<ParserState> parserStateObjectDefinition, IParser parser, String input) {
			return parse(poolStore, parserStateObjectDefinition, parser, ISimpleString.Utils.fromUtf8String(input));

		}

		public static ParserState parse(IPoolStore poolStore, IObjectDefinition<ParserState> parserStateObjectDefinition, IParser parser, ISimpleString input) {
			ParserState parserState = poolStore.pool(parserStateObjectDefinition).get(poolStore);
			parserState.populate(input);
			parser.parse(parserState);
			return parserState;
		}
	}

}