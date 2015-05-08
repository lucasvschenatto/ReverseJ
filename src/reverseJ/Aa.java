package reverseJ;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

public class Aa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	protected static Generalization createGeneralization(
			Classifier specificClassifier, Classifier generalClassifier) {

//		Generalization generalization = specificClassifier
//				.createGeneralization(generalClassifier);
//
//		out("Generalization %s --|> %s created.",
//				specificClassifier.getQualifiedName(),
//				generalClassifier.getQualifiedName());
//
//		return generalization;
		return null;
	}
	
	protected static void out(String format, Object... args) {
//		if (DEBUG) {
//			System.out.printf(format, args);
//			if (!format.endsWith("%n")) {
//				System.out.println();
//			}
//		}
	}
}
