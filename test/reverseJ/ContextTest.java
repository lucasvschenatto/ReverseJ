package reverseJ;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Model;
import org.junit.*;

public class ContextTest {
	@Test
	public void getInstance_returnsInstance(){
		Diagram c  = Diagram.getInstance();
		assertNotNull(c);
	}
	@Test
	public void getInstance_returnsSameInstance(){
		Diagram first  = Diagram.getInstance();
		Diagram second = Diagram.getInstance();
		
		assertEquals(first, second);
	}
	@Test
	public void resetInstance_returnsNewInstance(){
		Diagram first  = Diagram.getInstance();
		Diagram second = Diagram.resetInstance();
		
		assertNotEquals(first, second);
	}
	@Test
	public void getModel(){
		Diagram c = Diagram.getInstance();
		Model m = c.getModel();
		
		assertNotNull(m);
		assertTrue(m.isSetName());
	}
}
