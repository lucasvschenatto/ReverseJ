package reverseJ;

import java.util.LinkedList;
import java.util.List;

public class TestUtilities {

	static final String SPACE = " ";
	static final String INTERFACE = "Interface";
	static final String CLASS = "Class";
	static final String CLASS1 = "Caller";
	static final String CLASS2 = "Target";
	static final String MODIFIERS = "modifier";
	static final String METHOD = "method";
	static final String METHOD_R = "run";
	static final String METHOD_C = "<init>";
	static final String PARAMETERS = "double d";
	static final String RETURN_TYPE = "boolean";
	static final String RETURN_VOID = "void";
	static final String RETURN_CLASS1 = CLASS1;
	static final String RETURN_CLASS2 = CLASS2;
	static final String INTERACTION = "Interaction";
	static final String LIFELINE = "Lifeline";

	public static List<Information> completeMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(TestUtilities.CLASS + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD + id));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_TYPE + id));
	
		return informations;
	}

	public static List<Information> completeVoidMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(TestUtilities.CLASS + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD + id));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_VOID));
	
		return informations;
	}

	public static List<Information> completeNestedMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_TYPE + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_TYPE + id));
	
		return informations;
	}
	
	public static List<Information> completeNestedVoidMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_VOID));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_VOID));
	
		return informations;
	}

	public static List<Information> completeInterfaceTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createInterface(TestUtilities.INTERFACE + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD + id));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_TYPE + id));
	
		return informations;
	}

	public static List<Information> completeNestedInterfaceTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createInterface(TestUtilities.INTERFACE + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_TYPE + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_TYPE + id));
	
		return informations;
	}

	public static List<Information> completeConstructorTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(TestUtilities.CLASS + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_C));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_TYPE + id));
	
		return informations;
	}

	public static List<Information> completeNestedConstructorTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_C));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS2));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS1));
	
		return informations;
	}

	public static List<Information> completeBiDirectionalConstructorTrace(
			String id) {
		List<Information> informations = new LinkedList<Information>();
	
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_C));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS2));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS1));
	
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_C));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS1));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS2));
		return informations;
	}

	public static List<Information> completeBiDirectionalMethodTrace(String id) {
		List<Information> informations = new LinkedList<Information>();
	
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS2));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS1));
	
		informations.add(InformationFactory.createClass(TestUtilities.CLASS2 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createClass(TestUtilities.CLASS1 + id));
		informations.add(InformationFactory.createModifiers(TestUtilities.MODIFIERS + id));
		informations.add(InformationFactory.createMethod(TestUtilities.METHOD_R));
		informations.add(InformationFactory.createParameters(TestUtilities.PARAMETERS + id));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS1));
		informations.add(InformationFactory.createReturn(TestUtilities.RETURN_CLASS2));
		return informations;
	}

	public static List<Information> nestInformations(List<Information> nesting,
			List<Information> nested) {
		Information last = nesting.get(nesting.size() - 1);
		nesting.remove(nesting.size() - 1);
		nesting.addAll(nested);
		nesting.add(last);
		return nesting;
	}



}
