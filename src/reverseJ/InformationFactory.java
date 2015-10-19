package reverseJ;

public abstract class InformationFactory {
	public static Information createClass(String value){
		return new IClass(value);
	}
	public static Information createCaller(String value){
		return new ICaller(value);
	}
	public static Information createInterface(String value) {
		return new IInterface(value);
	}
	public static Information createTarget(String value) {
		return new ITarget(value);
	}
	public static Information createModifiers(String value) {
		return new IModifiers(value);
	}
	public static Information createMethod(String value) {
		return new IMethod(value);
	}
	public static Information createParameters(String value) {
		return new IParameters(value);
	}
	public static Information createReturn(String value) {
		return new IReturn(value);
	}
	public static Information createThrow(String value) {
		return new IThrow(value);
	}
	public static Information createHandler(String value) {
		return new IHandler(value);
	}
	public static Information createDummy(String value) {
		return new IDummy(value);
	}
}
