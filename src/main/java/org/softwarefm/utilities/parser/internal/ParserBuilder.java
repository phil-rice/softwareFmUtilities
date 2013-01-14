package org.softwarefm.utilities.parser.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.softwarefm.utilities.parser.IFieldSetter;
import org.softwarefm.utilities.parser.IParser;
import org.softwarefm.utilities.parser.IParserBuilder;
import org.softwarefm.utilities.parser.internal.chunks.EndFieldParser;
import org.softwarefm.utilities.parser.internal.chunks.EndObjectParser;
import org.softwarefm.utilities.parser.internal.chunks.FieldChunkParser;
import org.softwarefm.utilities.parser.internal.chunks.ObjectParser;
import org.softwarefm.utilities.parser.internal.chunks.StartFieldChunk;
import org.softwarefm.utilities.parser.internal.chunks.StartObjectChunk;
import org.softwarefm.utilities.pooling.IObjectDefinition;

public class ParserBuilder implements IParserBuilder {

	final String encoding;

	@SuppressWarnings("rawtypes")
	private Stack<ParserBuilderData> stack = new Stack<ParserBuilder.ParserBuilderData>();

	static class ParserBuilderData<T> {
		private StartObjectChunk<T> startObjectChunk;
		private List<IParser> content = new ArrayList<IParser>();
		private EndObjectParser endObjectParser;

		public ParserBuilderData(StartObjectChunk<T> startObjectChunk) {
			this.startObjectChunk = startObjectChunk;
		}

		public void addContent(IParser parser) {
			content.add(parser);
		}

		public ObjectParser<T> objectParser() {
			return new ObjectParser<T>(startObjectChunk, content, endObjectParser);
		}
	}

	public ParserBuilder(String encoding) {
		this.encoding = encoding;
	}

	@Override
	public <T> void startObjectMarker(String objectMarker, IObjectDefinition<T> objectDefinition) {
		StartObjectChunk<T> newStartObject = new StartObjectChunk<T>(objectMarker, encoding, objectDefinition);
		ParserBuilderData<T> data = new ParserBuilderData<T>(newStartObject);
		stack.push(data);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public IParser endObjectMarker(String objectMarker) {
		EndObjectParser endObjectParser = new EndObjectParser(objectMarker, encoding);
		ParserBuilderData pop = stack.pop();
		pop.endObjectParser = endObjectParser;
		ObjectParser objectParser = pop.objectParser();
		if (stack.size() > 0)
			stack.peek().addContent(objectParser);
		return objectParser;
	}

	@Override
	public void field(String startMarker, IFieldSetter fieldSetter, String endMarker) {
		StartFieldChunk startFieldChunk = new StartFieldChunk(startMarker, encoding, fieldSetter);
		EndFieldParser endFieldParser = new EndFieldParser(endMarker, encoding);
		stack.peek().addContent(new FieldChunkParser(startFieldChunk, endFieldParser));

	}

}
