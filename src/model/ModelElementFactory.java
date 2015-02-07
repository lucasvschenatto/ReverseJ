package model;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

public class ModelElementFactory extends UML2Resource {
	
	 private static ModelElementFactory modelElementFactory = null; 
	
	public ModelElementFactory(){
		registerResourceFactories();
	}
	
	public synchronized static ModelElementFactory eINSTANCE() {  
        if (modelElementFactory == null) {  
        	modelElementFactory = new ModelElementFactory();  
        }  
        return modelElementFactory;  
    } 

	public Model createModel(String name) {

		Model model = UMLFactory.eINSTANCE.createModel();
		model.setName(name);

		out("Model '" + model.getQualifiedName() + "' created.");

		return model;
	}

	public Package createPackage(
			org.eclipse.uml2.uml.Package nestingPackage, String name) {
		org.eclipse.uml2.uml.Package package_ = nestingPackage
				.createNestedPackage(name);

		out("Package '" + package_.getQualifiedName() + "' created.");

		return package_;
	}

	public PrimitiveType createPrimitiveType(
			org.eclipse.uml2.uml.Package package_, String name) {
		PrimitiveType primitiveType = (PrimitiveType) package_
				.createOwnedPrimitiveType(name);

		out("Primitive type '" + primitiveType.getQualifiedName()
				+ "' created.");

		return primitiveType;
	}

	public Enumeration createEnumeration(
			org.eclipse.uml2.uml.Package package_, String name) {
		Enumeration enumeration = (Enumeration) package_
				.createOwnedEnumeration(name);

		out("Enumeration '" + enumeration.getQualifiedName() + "' created.");

		return enumeration;
	}

	public EnumerationLiteral createEnumerationLiteral(
			Enumeration enumeration, String name) {
		EnumerationLiteral enumerationLiteral = enumeration
				.createOwnedLiteral(name);

		out("Enumeration literal '" + enumerationLiteral.getQualifiedName()
				+ "' created.");

		return enumerationLiteral;
	}

	public Class createClass(
			org.eclipse.uml2.uml.Package package_, String name,
			boolean isAbstract) {
		org.eclipse.uml2.uml.Class class_ = package_.createOwnedClass(name,
				isAbstract);

		out("Class '" + class_.getQualifiedName() + "' created.");

		return class_;
	}

	public Generalization createGeneralization(
			Classifier specificClassifier, Classifier generalClassifier) {
		Generalization generalization = specificClassifier
				.createGeneralization(generalClassifier);

		out("Generalization " + specificClassifier.getQualifiedName() + " ->> "
				+ generalClassifier.getQualifiedName() + " created.");

		return generalization;
	}

	public Property createAttribute(org.eclipse.uml2.uml.Class class_,
			String name, Type type, int lowerBound, int upperBound) {
		Property attribute = class_.createOwnedAttribute(name, type,
				lowerBound, upperBound);

		StringBuffer sb = new StringBuffer();

		sb.append("Attribute '");

		sb.append(attribute.getQualifiedName());

		sb.append("' : ");

		sb.append(type.getQualifiedName());

		sb.append(" [");
		sb.append(lowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == upperBound ? "*"
				: String.valueOf(upperBound));
		sb.append("]");

		sb.append(" created.");

		out(sb.toString());

		return attribute;
	}

	public Association createAssociation(Type type1,
			boolean end1IsNavigable, AggregationKind end1Aggregation,
			String end1Name, int end1LowerBound, int end1UpperBound,
			Type type2, boolean end2IsNavigable,
			AggregationKind end2Aggregation, String end2Name,
			int end2LowerBound, int end2UpperBound) {

		Association association = type1.createAssociation(end1IsNavigable,
				end1Aggregation, end1Name, end1LowerBound, end1UpperBound,
				type2, end2IsNavigable, end2Aggregation, end2Name,
				end2LowerBound, end2UpperBound);

		StringBuffer sb = new StringBuffer();

		sb.append("Association ");

		if (null == end1Name || 0 == end1Name.length()) {
			sb.append('{');
			sb.append(type1.getQualifiedName());
			sb.append('}');
		} else {
			sb.append("'");
			sb.append(type1.getQualifiedName());
			sb.append(NamedElement.SEPARATOR);
			sb.append(end1Name);
			sb.append("'");
		}

		sb.append(" [");
		sb.append(end1LowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == end1UpperBound ? "*"
				: String.valueOf(end1UpperBound));
		sb.append("] ");

		sb.append(end2IsNavigable ? '<' : '-');
		sb.append('-');
		sb.append(end1IsNavigable ? '>' : '-');
		sb.append(' ');

		if (null == end2Name || 0 == end2Name.length()) {
			sb.append('{');
			sb.append(type2.getQualifiedName());
			sb.append('}');
		} else {
			sb.append("'");
			sb.append(type2.getQualifiedName());
			sb.append(NamedElement.SEPARATOR);
			sb.append(end2Name);
			sb.append("'");
		}

		sb.append(" [");
		sb.append(end2LowerBound);
		sb.append("..");
		sb.append(LiteralUnlimitedNatural.UNLIMITED == end2UpperBound ? "*"
				: String.valueOf(end2UpperBound));
		sb.append("]");

		sb.append(" created.");

		out(sb.toString());

		return association;
	}

	public void main(String[] args) {



	

//		out("Creating model...");
//
//		Model epo2Model = createModel("epo2");
//
//		out("Creating primitive types...");
//
//		PrimitiveType intPrimitiveType = createPrimitiveType(epo2Model, "int");
//		PrimitiveType stringPrimitiveType = createPrimitiveType(epo2Model,
//				"String");
//		PrimitiveType datePrimitiveType = createPrimitiveType(epo2Model, "Date");
//		PrimitiveType skuPrimitiveType = createPrimitiveType(epo2Model, "SKU");
//
//		out("Creating enumerations...");
//
//		Enumeration orderStatusEnumeration = createEnumeration(epo2Model,
//				"OrderStatus");
//
//		out("Creating enumeration literals...");
//
//		createEnumerationLiteral(orderStatusEnumeration, "Pending");
//		createEnumerationLiteral(orderStatusEnumeration, "BackOrder");
//		createEnumerationLiteral(orderStatusEnumeration, "Complete");
//
//		out("Creating classes...");
//
//		org.eclipse.uml2.uml.Class supplierClass = createClass(epo2Model,
//				"Supplier", false);
//		org.eclipse.uml2.uml.Class customerClass = createClass(epo2Model,
//				"Customer", false);
//		org.eclipse.uml2.uml.Class purchaseOrderClass = createClass(epo2Model,
//				"PurchaseOrder", false);
//		org.eclipse.uml2.uml.Class itemClass = createClass(epo2Model, "Item",
//				false);
//		org.eclipse.uml2.uml.Class addressClass = createClass(epo2Model,
//				"Address", true);
//		org.eclipse.uml2.uml.Class usAddressClass = createClass(epo2Model,
//				"USAddress", false);
//		org.eclipse.uml2.uml.Class globalAddressClass = createClass(epo2Model,
//				"GlobalAddress", false);
//		org.eclipse.uml2.uml.Class globalLocationClass = createClass(epo2Model,
//				"GlobalLocation", false);
//
//		out("Creating generalizations...");
//
//		createGeneralization(usAddressClass, addressClass);
//		createGeneralization(globalAddressClass, addressClass);
//		createGeneralization(globalAddressClass, globalLocationClass);
//
//		out("Creating attributes...");
//
//		createAttribute(supplierClass, "name", stringPrimitiveType, 0, 1);
//		createAttribute(customerClass, "customerID", intPrimitiveType, 0, 1);
//		createAttribute(purchaseOrderClass, "comment", stringPrimitiveType, 0,
//				1);
//		createAttribute(purchaseOrderClass, "orderDate", datePrimitiveType, 0,
//				1);
//		createAttribute(purchaseOrderClass, "status", orderStatusEnumeration,
//				0, 1);
//		createAttribute(purchaseOrderClass, "totalAmount", intPrimitiveType, 0,
//				1);
//		createAttribute(itemClass, "productName", stringPrimitiveType, 0, 1);
//		createAttribute(itemClass, "quantity", intPrimitiveType, 0, 1);
//		createAttribute(itemClass, "USPrice", intPrimitiveType, 0, 1);
//		createAttribute(itemClass, "comment", stringPrimitiveType, 0, 1);
//		createAttribute(itemClass, "shipDate", datePrimitiveType, 0, 1);
//		createAttribute(itemClass, "partNum", skuPrimitiveType, 0, 1);
//		createAttribute(addressClass, "name", stringPrimitiveType, 0, 1);
//		createAttribute(addressClass, "country", stringPrimitiveType, 0, 1);
//		createAttribute(usAddressClass, "street", stringPrimitiveType, 0, 1);
//		createAttribute(usAddressClass, "city", stringPrimitiveType, 0, 1);
//		createAttribute(usAddressClass, "state", stringPrimitiveType, 0, 1);
//		createAttribute(usAddressClass, "zip", intPrimitiveType, 0, 1);
//		createAttribute(globalAddressClass, "location", stringPrimitiveType, 0,
//				LiteralUnlimitedNatural.UNLIMITED);
//		createAttribute(globalLocationClass, "countryCode", intPrimitiveType,
//				0, 1);
//
//		out("Creating associations...");
//
//		createAssociation(supplierClass, true,
//				AggregationKind.COMPOSITE_LITERAL, "orders", 0,
//				LiteralUnlimitedNatural.UNLIMITED, purchaseOrderClass, false,
//				AggregationKind.NONE_LITERAL, "", 1, 1);
//
//		createAssociation(supplierClass, true, AggregationKind.NONE_LITERAL,
//				"pendingOrders", 0, LiteralUnlimitedNatural.UNLIMITED,
//				purchaseOrderClass, false, AggregationKind.NONE_LITERAL, "", 0,
//				1);
//
//		createAssociation(supplierClass, true, AggregationKind.NONE_LITERAL,
//				"shippedOrders", 0, LiteralUnlimitedNatural.UNLIMITED,
//				purchaseOrderClass, false, AggregationKind.NONE_LITERAL, "", 0,
//				1);
//
//		createAssociation(supplierClass, true,
//				AggregationKind.COMPOSITE_LITERAL, "customers", 0,
//				LiteralUnlimitedNatural.UNLIMITED, customerClass, false,
//				AggregationKind.NONE_LITERAL, "", 1, 1);
//
//		createAssociation(customerClass, true, AggregationKind.NONE_LITERAL,
//				"orders", 0, LiteralUnlimitedNatural.UNLIMITED,
//				purchaseOrderClass, true, AggregationKind.NONE_LITERAL,
//				"customer", 1, 1);
//
//		createAssociation(purchaseOrderClass, true,
//				AggregationKind.NONE_LITERAL, "previousOrder", 0, 1,
//				purchaseOrderClass, false, AggregationKind.NONE_LITERAL, "", 0,
//				1);
//
//		createAssociation(purchaseOrderClass, true,
//				AggregationKind.COMPOSITE_LITERAL, "items", 0,
//				LiteralUnlimitedNatural.UNLIMITED, itemClass, true,
//				AggregationKind.NONE_LITERAL, "order", 1, 1);
//
//		createAssociation(purchaseOrderClass, true,
//				AggregationKind.COMPOSITE_LITERAL, "billTo", 1, 1,
//				addressClass, false, AggregationKind.NONE_LITERAL, "", 1, 1);
//
//		createAssociation(purchaseOrderClass, true,
//				AggregationKind.COMPOSITE_LITERAL, "shipTo", 0, 1,
//				addressClass, false, AggregationKind.NONE_LITERAL, "", 1, 1);
//
//		out("Saving model...");
//
//		String aux3 = "files/ExtendedPO222.uml";
//		save(epo2Model, URI.createURI(aux3));
	}

}
