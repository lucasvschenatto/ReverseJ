package reverseJ;

public class IInterface implements Information {
	private String value;
	IInterface(String value){
		this.value = value;
	}
	public String getValue() {
		return value;
	}
	public String describe() {
		return this.getClass().getSimpleName() + " : " + getValue();
	}
}
