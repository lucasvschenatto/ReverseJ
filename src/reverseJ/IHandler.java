package reverseJ;

public class IHandler implements Information {
	private String value;
	IHandler(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String describe() {
		return this.getClass().getSimpleName() + " : " + getValue();
	}
}
