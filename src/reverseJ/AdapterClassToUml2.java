package reverseJ;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Type;

class AdapterClassToUml2 implements AdapterToUml2{
	private Context context;
	private Package rootPackage;

	protected AdapterClassToUml2(String packageName) {
		this.context = Context.getInstance();
		rootPackage = this.context.getModel().createNestedPackage(packageName);
	}

	public AdapterClassToUml2() {
		context = Context.getInstance();
		rootPackage = context.getModel().createNestedPackage("default");
	}

	public static AdapterClassToUml2 make() {
		return new AdapterClassToUml2("classDiagram");
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
	
	public Operation createMethod(String className, String methodName,
			String signature) {
		// TODO
		// org.eclipse.uml2.uml.Class c = (org.eclipse.uml2.uml.Class)
		// rootPackage.getOwnedMember(className);
		// EList<String> e;
		// c.createOwnedOperation(methodName, ownedParameterNames,
		// ownedParameterTypes);
		return null;
	}

	public Operation createMethodWithReturn(String className, String methodName,
			String signature, String returnType) {
		// TODO
		return null;
	}
}
