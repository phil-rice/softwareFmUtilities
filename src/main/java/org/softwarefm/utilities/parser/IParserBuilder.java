package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.parser.internal.ParserBuilder;
import org.softwarefm.utilities.pooling.IObjectDefinition;

public interface IParserBuilder {

	/** Expecting this object now */
	<T> void startObjectMarker(String objectMarker, IObjectDefinition<T> objectDefinition);

	/** Expecting this field now */
	void field(String startMarker, IFieldSetter fieldSetter, String endMarker);

	IParser endObjectMarker(String objectMarker);

	public static class Utils {
		public static IParserBuilder builder(String encoding) {
			return new ParserBuilder(encoding);
		}
	}

}
