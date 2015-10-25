package reversej.diagram.informationmodel;

import reversej.diagram.Information;

public class ISubReturn implements Information {
	private String value;
	ISubReturn(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String describe() {
		return this.getClass().getSimpleName() + " : " + getValue();
	}
}
