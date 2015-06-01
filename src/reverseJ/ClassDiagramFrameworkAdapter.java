package reverseJ;

import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;


public class ClassDiagramFrameworkAdapter{
	private Context context;
	private Package rootPackage;
	protected ClassDiagramFrameworkAdapter(String packageName, Context context) {
		context = Context.getInstance();
		rootPackage = context.getModel().createNestedPackage(packageName);
	}
	public ClassDiagramFrameworkAdapter() {
		context = Context.getInstance();
		rootPackage = context.getModel().createNestedPackage("default");
	}
	public static ClassDiagramFrameworkAdapter make(Context context) {
		return new ClassDiagramFrameworkAdapter("classDiagram", context);
	}
	public Package getPackage() {
		return rootPackage;
	}
	
	public Class createConcreteClass(String name){
		return rootPackage.createOwnedClass(name, false);
	}
	public Interface createInterface(String name) {
		return rootPackage.createOwnedInterface(name);
	}
	public Operation createMethod(String className, String methodName, String signature) {
//		org.eclipse.uml2.uml.Class c = (org.eclipse.uml2.uml.Class) rootPackage.getOwnedMember(className);
//		EList<String> e;
//		c.createOwnedOperation(methodName, ownedParameterNames, ownedParameterTypes);
		return null;
	}
	public void createMethodWithReturn(String className, String methodName, String signature, String returnType) {
		// TODO Auto-generated method stub
	}
	public PrimitiveType createType(String name) {
		return rootPackage.createOwnedPrimitiveType(name);
	}
	public InterfaceRealization createImplementation(String interface_, String implementer) {
		Class imp = (Class)rootPackage.getOwnedMember(implementer);
		Interface inter = (Interface)rootPackage.getOwnedMember(interface_);
		return imp.createInterfaceRealization(interface_+implementer, inter);
	}
	public Association createUnidirectionalAssociation(String caller, String target) {
		Class c = (Class)rootPackage.getOwnedMember(caller);
		Class t = (Class)rootPackage.getOwnedMember(target);
		return c.createAssociation(
				true, AggregationKind.NONE_LITERAL, null,
				0, LiteralUnlimitedNatural.UNLIMITED,
				t, false, AggregationKind.NONE_LITERAL, null,
				0, LiteralUnlimitedNatural.UNLIMITED);	
	}
	public void createBiDirectionalAssociation(String class1, String class2) {
		// TODO Auto-generated method stub
	}
	public void createDependency(String caller, String target) {
		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args)
			throws Exception {
		Model epo2Model = createModel("epo2");

		// Create primitive types to be used as types of attributes in our
		// classes.
		PrimitiveType intPrimitiveType = createPrimitiveType(epo2Model, "int");
		PrimitiveType stringPrimitiveType = createPrimitiveType(epo2Model,
			"String");
		PrimitiveType datePrimitiveType = createPrimitiveType(epo2Model, "Date");
		PrimitiveType skuPrimitiveType = createPrimitiveType(epo2Model, "SKU");

		// Create enumerations to be used as types of attributes in our classes.
		Enumeration orderStatusEnumeration = createEnumeration(epo2Model,
			"OrderStatus");
		createEnumerationLiteral(orderStatusEnumeration, "Pending");
		createEnumerationLiteral(orderStatusEnumeration, "BackOrder");
		createEnumerationLiteral(orderStatusEnumeration, "Complete");

		// Create the classes.
		org.eclipse.uml2.uml.Class supplierClass = createClass(epo2Model,
			"Supplier", false);
		org.eclipse.uml2.uml.Class customerClass = createClass(epo2Model,
			"Customer", false);
		org.eclipse.uml2.uml.Class purchaseOrderClass = createClass(epo2Model,
			"PurchaseOrder", false);
		org.eclipse.uml2.uml.Class itemClass = createClass(epo2Model, "Item",
			false);
		org.eclipse.uml2.uml.Class addressClass = createClass(epo2Model,
			"Address", true);
		org.eclipse.uml2.uml.Class usAddressClass = createClass(epo2Model,
			"USAddress", false);
		org.eclipse.uml2.uml.Class globalAddressClass = createClass(epo2Model,
			"GlobalAddress", false);
		org.eclipse.uml2.uml.Class globalLocationClass = createClass(epo2Model,
			"GlobalLocation", false);

		// Create generalization relationships amongst our classes.
		createGeneralizationOld(usAddressClass, addressClass);
		createGeneralizationOld(globalAddressClass, addressClass);
		createGeneralizationOld(globalAddressClass, globalLocationClass);

		// Create attributes in our classes.
		createAttribute(supplierClass, "name", stringPrimitiveType, 0, 1);
		createAttribute(customerClass, "customerID", intPrimitiveType, 1, 1);
		createAttribute(purchaseOrderClass, "comment", stringPrimitiveType, 0,
			1);
		createAttribute(purchaseOrderClass, "orderDate", datePrimitiveType, 1,
			1);
		createAttribute(purchaseOrderClass, "status", orderStatusEnumeration,
			1, 1);
		createAttribute(purchaseOrderClass, "totalAmount", intPrimitiveType, 0,
			1);
		createAttribute(itemClass, "productName", stringPrimitiveType, 0, 1);
		createAttribute(itemClass, "quantity", intPrimitiveType, 0, 1);
		createAttribute(itemClass, "usPrice", intPrimitiveType, 0, 1);
		createAttribute(itemClass, "comment", stringPrimitiveType, 0, 1);
		createAttribute(itemClass, "shipDate", datePrimitiveType, 0, 1);
		createAttribute(itemClass, "partNum", skuPrimitiveType, 1, 1);
		createAttribute(addressClass, "name", stringPrimitiveType, 0, 1);
		createAttribute(addressClass, "country", stringPrimitiveType, 0, 1);
		createAttribute(usAddressClass, "street", stringPrimitiveType, 1, 1);
		createAttribute(usAddressClass, "city", stringPrimitiveType, 1, 1);
		createAttribute(usAddressClass, "state", stringPrimitiveType, 1, 1);
		createAttribute(usAddressClass, "zip", stringPrimitiveType, 1, 1);
		createAttribute(globalAddressClass, "location", stringPrimitiveType, 1,
			1);
		createAttribute(globalLocationClass, "countryCode", intPrimitiveType,
			1, 1);
		
		// Create associations between our classes.
		createAssociation(supplierClass, true,
			AggregationKind.COMPOSITE_LITERAL, "orders", 0,
			LiteralUnlimitedNatural.UNLIMITED, purchaseOrderClass, false,
			AggregationKind.NONE_LITERAL, "", 1, 1);
		createAssociation(supplierClass, true, AggregationKind.NONE_LITERAL,
			"pendingOrders", 0, LiteralUnlimitedNatural.UNLIMITED,
			purchaseOrderClass, false, AggregationKind.NONE_LITERAL, "", 0, 1);
		createAssociation(supplierClass, true, AggregationKind.NONE_LITERAL,
			"shippedOrders", 0, LiteralUnlimitedNatural.UNLIMITED,
			purchaseOrderClass, false, AggregationKind.NONE_LITERAL, "", 0, 1);
		createAssociation(supplierClass, true,
			AggregationKind.COMPOSITE_LITERAL, "customers", 0,
			LiteralUnlimitedNatural.UNLIMITED, customerClass, false,
			AggregationKind.NONE_LITERAL, "", 1, 1);
		createAssociation(customerClass, true, AggregationKind.NONE_LITERAL,
			"orders", 0, LiteralUnlimitedNatural.UNLIMITED, purchaseOrderClass,
			true, AggregationKind.NONE_LITERAL, "customer", 1, 1);
		createAssociation(purchaseOrderClass, true,
			AggregationKind.NONE_LITERAL, "previousOrder", 0, 1,
			purchaseOrderClass, false, AggregationKind.NONE_LITERAL, "", 0, 1);
		createAssociation(purchaseOrderClass, true,
			AggregationKind.COMPOSITE_LITERAL, "items", 0,
			LiteralUnlimitedNatural.UNLIMITED, itemClass, true,
			AggregationKind.NONE_LITERAL, "order", 1, 1);
		createAssociation(purchaseOrderClass, true,
			AggregationKind.COMPOSITE_LITERAL, "billTo", 1, 1, addressClass,
			false, AggregationKind.NONE_LITERAL, "", 1, 1);
		createAssociation(purchaseOrderClass, true,
			AggregationKind.COMPOSITE_LITERAL, "shipTo", 1, 1, addressClass,
			false, AggregationKind.NONE_LITERAL, "", 1, 1);
	}
	protected static Model createModel(String name) {
		Model model = UMLFactory.eINSTANCE.createModel();
		model.setName(name);
		return model;
	}

	protected static org.eclipse.uml2.uml.Package createPackage(
			org.eclipse.uml2.uml.Package nestingPackage, String name) {

		org.eclipse.uml2.uml.Package package_ = nestingPackage
			.createNestedPackage(name);
		return package_;
	}

	protected static PrimitiveType createPrimitiveType(
			org.eclipse.uml2.uml.Package package_, String name) {

		PrimitiveType primitiveType = package_.createOwnedPrimitiveType(name);
		return primitiveType;
	}

	protected static Enumeration createEnumeration(
			org.eclipse.uml2.uml.Package package_, String name) {

		Enumeration enumeration = package_.createOwnedEnumeration(name);
		return enumeration;
	}

	protected static EnumerationLiteral createEnumerationLiteral(
			Enumeration enumeration, String name) {

		EnumerationLiteral enumerationLiteral = enumeration
			.createOwnedLiteral(name);
		return enumerationLiteral;
	}

	protected static org.eclipse.uml2.uml.Class createClass(
			org.eclipse.uml2.uml.Package package_, String name,
			boolean isAbstract) {

		org.eclipse.uml2.uml.Class class_ = package_.createOwnedClass(name,
			isAbstract);
		return class_;
	}

	protected static Generalization createGeneralizationOld(
			Classifier specificClassifier, Classifier generalClassifier) {

		Generalization generalization = specificClassifier.createGeneralization(generalClassifier);
		return generalization;
	}

	protected static Property createAttribute(
			org.eclipse.uml2.uml.Class class_, String name, Type type,
			int lowerBound, int upperBound) {

		Property attribute = class_.createOwnedAttribute(name, type,
			lowerBound, upperBound);
		return attribute;
	}

	protected static Association createAssociation(Type type1,
			boolean end1IsNavigable, AggregationKind end1Aggregation,
			String end1Name, int end1LowerBound, int end1UpperBound,
			Type type2, boolean end2IsNavigable,
			AggregationKind end2Aggregation, String end2Name,
			int end2LowerBound, int end2UpperBound) {

		Association association = type1.createAssociation(end1IsNavigable,
			end1Aggregation, end1Name, end1LowerBound, end1UpperBound, type2,
			end2IsNavigable, end2Aggregation, end2Name, end2LowerBound,
			end2UpperBound);

				return association;
	}
	protected static void save(org.eclipse.uml2.uml.Package package_, URI uri) {
		// Create a resource-set to contain the resource(s) that we are saving
		ResourceSet resourceSet = new ResourceSetImpl();

		// Initialize registrations of resource factories, library models,
		// profiles, Ecore metadata, and other dependencies required for
		// serializing and working with UML resources. This is only necessary in
		// applications that are not hosted in the Eclipse platform run-time, in
		// which case these registrations are discovered automatically from
		// Eclipse extension points.
		UMLResourcesUtil.init(resourceSet);

		// Create the output resource and add our model package to it.
		Resource resource = resourceSet.createResource(uri);
		resource.getContents().add(package_);

		// And save
		try {
			resource.save(null); // no save options needed
			System.out.println("done");
		} catch (IOException ioe) {
			System.out.println("failed to save");
		}
	}
	
}
