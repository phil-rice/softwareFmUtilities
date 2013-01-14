package org.softwarefm.utilities.parser.internal.chunks;

import org.softwarefm.utilities.parser.IFieldSetter;
import org.softwarefm.utilities.parser.internal.ParserState;

public class StartFieldChunk extends AbstractParser {

	final IFieldSetter fieldSetter;

	public StartFieldChunk(String marker, String encoding, IFieldSetter fieldSetter) {
		super(marker, encoding);
		this.fieldSetter = fieldSetter;
	}

	@Override
	public boolean parse(ParserState state) {
		needMarker(state);
		fieldSetter.setField(state);
		return true;
	}

	@Override
	public String toString() {
		return "StartFieldChunk [marker=" + marker + ", fieldSetter=" + fieldSetter + "]";
	}

}