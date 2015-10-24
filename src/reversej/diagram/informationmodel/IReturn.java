package reversej.diagram.informationmodel;

import reversej.diagram.Information;

public class IReturn implements Information {
	private String value;
	IReturn(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String describe() {
		return this.getClass().getSimpleName() + " : " + getValue();
	}
}
