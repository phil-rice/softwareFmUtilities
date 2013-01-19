package org.softwarefm.utilities.parser;

import org.softwarefm.utilities.strings.ISimpleString;

public abstract class UtfStringIntegerFieldSetter<T> implements IFieldSetter {

	abstract protected void setField(T object, int value);

	@SuppressWarnings("unchecked")
	public void setField(ParserState parserState) {
		ISimpleString input = parserState.input;
		int result = 0;
		while (parserState.nextIndex < input.length()) {
			byte b = input.byteAt(parserState.nextIndex);
			boolean isDigit = b >= 48 && b <= 57; // 0..9
			if (!isDigit)
				break;
			result = result * 10 + b - 48;
			parserState.nextIndex++;
		}
		setField((T) parserState.thisObject, result);
	}

}
