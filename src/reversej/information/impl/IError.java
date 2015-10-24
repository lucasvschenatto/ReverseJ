package reversej.information.impl;

import reversej.information.Information;

public class IError implements Information {
	private static String VALUE = "information_type_not_found";
	IError(){}
	@Override
	public String getValue() {
		return VALUE;
	}

	@Override
	public String describe() {
		return this.getClass().getSimpleName() + " : " + getValue();
	}
}
