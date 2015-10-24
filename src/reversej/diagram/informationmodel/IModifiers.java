package reversej.diagram.informationmodel;

import reversej.diagram.Information;

public class IModifiers implements Information {
	private String value;
	IModifiers(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String describe() {
		return this.getClass().getSimpleName() + " : " + getValue();
	}
}
