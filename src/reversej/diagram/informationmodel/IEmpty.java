package reversej.diagram.informationmodel;

import reversej.diagram.Information;

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
