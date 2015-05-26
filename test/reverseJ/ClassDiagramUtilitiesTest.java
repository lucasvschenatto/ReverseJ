package reverseJ;

import static org.junit.Assert.*;
import org.junit.*;
import org.eclipse.uml2.uml.Package;

public class ClassDiagramUtilitiesTest {
	private ClassDiagramUtilities c;
	
	@Before
	public void setUp(){
		c = ClassDiagramUtilities.make();
	}

	@Test
	public void testMake() {
		c = ClassDiagramUtilities.make();
		assertNotNull(c);
	}
	@Test
	public void getPackage(){
		Package p = c.getPackage();
		assertNotNull(p);
	}

	@Test@Ignore
	public void testCreateConcreteClass() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateInterface() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateMethod() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateMethodWithReturn() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateType() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateImplementation() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateUnidirectionalAssociation() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateBiDirectionalAssociation() {
		fail("Not yet implemented");
	}

	@Test@Ignore
	public void testCreateDependency() {
		fail("Not yet implemented");
	}

}
