package reverseJ;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.junit.Test;

public class DiagramObjectTest {

	@Test
	public void WhenCreate_SetsPackage() {
		Package createdPackage = UMLFactory.eINSTANCE.createPackage();
		DiagramObject d = new DiagramObject(createdPackage);
		Package obtainedPackage = d.getPackage();
		
		assertEquals(createdPackage,obtainedPackage);
	}

}
