package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.parser.internal.ParserState;
import org.softwarefm.utilities.strings.ISimpleString;

public abstract class UtfStringIndentifierFieldSetter<T> implements IFieldSetter {

	abstract protected void setField(T object, ISimpleString value);

	@SuppressWarnings("unchecked")
	public void setField(ParserState parserState) {
		int start = parserState.nextIndex;
		ISimpleString input = parserState.input;
		while (parserState.nextIndex < input.length()) {
			byte b = input.byteAt(parserState.nextIndex);
			boolean isAlpha = b >= 48 && b <= 57 || b >= 65 && b < 90 || b >= 97 && b < 122; // 0..9 or A..Z or a..z
			if (!isAlpha)
				break;
			parserState.nextIndex++;
		}
		ISimpleString value = input.subString(parserState.poolStore, start, parserState.nextIndex - start );
		setField((T) parserState.thisObject, value);
	}

}
