package reversej.information.impl;

import reversej.information.Information;

public class IEmpty implements Information {
	IEmpty(){}
	@Override
	public String getValue() {
		return "";
	}

	@Override
	public String describe() {
		return this.getClass().getSimpleName();
	}

}
