package reversej.information.impl;

import reversej.information.Information;

public class ITarget implements Information {
	private String value;
	ITarget(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String describe() {
		return this.getClass().getSimpleName() + " : " + getValue();
	}
}
