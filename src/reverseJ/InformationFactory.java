package reverseJ;

public abstract class InformationFactory {
	public static ICaller createCaller(String value){
		return new ICaller(value);
	}
	public static IInterface createInterface(String value) {
		return new IInterface(value);
	}
	public static ITarget createTarget(String value) {
		return new ITarget(value);
	}
	public static IModifiers createModifiers(String value) {
		return new IModifiers(value);
	}
	public static IMethod createMethod(String value) {
		return new IMethod(value);
	}
	public static ISignature createSignature(String value) {
		return new ISignature(value);
	}
	public static IReturn createReturn(String value) {
		return new IReturn(value);
	}
	public static IThrow createThrow(String value) {
		return new IThrow(value);
	}
	public static IHandler createHandler(String value) {
		return new IHandler(value);
	}
	public static IDummy createDummy(String value) {
		return new IDummy(value);
	}
}
