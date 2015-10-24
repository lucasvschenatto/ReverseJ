package reversej.diagram.strategies.uml2adapter;

import java.util.Arrays;
import java.util.List;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.ParameterDirectionKind;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

import reversej.diagram.Diagram;
import reversej.diagram.ModelAdapter;

public class AdapterClassToUml2 implements ModelAdapter{
	public static final String PACKAGE_NAME = "Class Diagram";
	private Diagram diagram;
	private Package rootPackage;

	protected AdapterClassToUml2(String packageName) {
		this.diagram = Diagram.getInstance();
		rootPackage = this.diagram.getModel().createNestedPackage(packageName);
	}

	protected AdapterClassToUml2() {
		diagram = Diagram.getInstance();
		rootPackage = diagram.getModel().createNestedPackage("default");
	}

	public static AdapterClassToUml2 make() {
		return new AdapterClassToUml2(PACKAGE_NAME);
	}
	@Override
	public Package getPackage() {
		return rootPackage;
	}

	public Class createConcreteClass(String name) {
		return rootPackage.createOwnedClass(name, false);
	}

	public Interface createInterface(String name) {
		return rootPackage.createOwnedInterface(name);
	}

	public PrimitiveType createType(String name) {
		return rootPackage.createOwnedPrimitiveType(name);
	}

	public InterfaceRealization createImplementation(String interface_,
			String implementer) {
		Class imp = (Class) rootPackage.getOwnedMember(implementer);
		Interface inter = (Interface) rootPackage.getOwnedMember(interface_);
		return imp.createInterfaceRealization(interface_ + implementer, inter);
	}

	public Association createUnidirectionalAssociation(String source,
			String target) {
		Type s = (Type) rootPackage.getOwnedMember(source);
		Type t = (Type) rootPackage.getOwnedMember(target);
		return s.createAssociation(true, AggregationKind.NONE_LITERAL, "", 0,
				LiteralUnlimitedNatural.UNLIMITED, t, false,
				AggregationKind.NONE_LITERAL, "", 0,
				LiteralUnlimitedNatural.UNLIMITED);
	}

	public Association createBidirectionalAssociation(String class1,
			String class2) {
		Type end1 = (Type) rootPackage.getOwnedMember(class1);
		Type end2 = (Type) rootPackage.getOwnedMember(class2);
		return end1.createAssociation(true, AggregationKind.NONE_LITERAL, "",
				0, LiteralUnlimitedNatural.UNLIMITED, end2, true,
				AggregationKind.NONE_LITERAL, "", 0,
				LiteralUnlimitedNatural.UNLIMITED);
	}

	public Dependency createDependency(String source, String target) {
		Type s = (Type) rootPackage.getOwnedMember(source);
		Type t = (Type) rootPackage.getOwnedMember(target);
		return s.createDependency(t);
	}
	
	public Operation createMethodWithReturn(String className, String methodName,
			String signature, String returnType) {
		Type t = (Type)rootPackage.getOwnedMember(returnType);
		Operation o = createMethod(className, methodName, signature);
		o.createOwnedParameter("return", t).setDirection(ParameterDirectionKind.RETURN_LITERAL);
		return o;
	}
	
	public Operation createMethod(String className, String methodName,
			String signature) {
		Operation o = UMLFactory.eINSTANCE.createOperation();
		o.setName(methodName);
		if(signature != null && signature.length() > 0){
			createParameters(o, signature);
		}		
		NamedElement ne = rootPackage.getOwnedMember(className);		
		if(ne instanceof Class)
			o.setClass_((org.eclipse.uml2.uml.Class)ne);
		if(ne instanceof Interface)
			o.setInterface((org.eclipse.uml2.uml.Interface)ne);
		return o;
	}

	private void createParameters(Operation method, String signature) {
		List<String> listOfParameters = Arrays.asList(signature.split(","));
		for (String s : listOfParameters) {
			s = s.trim();
			String parameter[] = s.split(" ");
			String name = parameter[1];
			Type t = (Type)rootPackage.getOwnedMember(parameter[0]);
			method.createOwnedParameter(name, t).setDirection(ParameterDirectionKind.IN_LITERAL);
		}
	}
}
