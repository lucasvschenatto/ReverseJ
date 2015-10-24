package reversej.information.impl;

import reversej.information.Information;
import reversej.information.InformationFactory;

public class InformationFactoryImpl implements InformationFactory {
	@Override
	public Information create(String type, String value){
		if(type==null)
			return createError();
		
		value = value==null?"":value;
		
		switch (type) {
		case "Class":
			return createClass(value);
		case "Caller":
			return createCaller(value);
		case "Interface":
			return createInterface(value);
		case "Target":
			return createTarget(value);
		case "Modifiers":
			return createModifiers(value);
		case "Method":
			return createMethod(value);
		case "Parameters":
			return createParameters(value);
		case "Return":
			return createReturn(value);
		case "Throw":
			return createThrow(value);
		case "Handler":
			return createHandler(value);
		case "Generic":
			return createGeneric(value);
		case "Empty":
			return createEmpty();
		default:
			return createError();
		}
	}
	
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
	public static Information createGeneric(String value) {
		return new IGeneric(value);
	}
	public static Information createEmpty() {
		return new IEmpty();
	}
	public static Information createError() {
		return new IError();
	}
}
