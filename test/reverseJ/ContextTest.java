package reverseJ;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Model;
import org.junit.*;

public class ContextTest {
	@Test
	public void getInstance_returnsInstance(){
		Context c  = Context.getInstance();
		assertNotNull(c);
	}
	@Test
	public void getInstance_returnsSameInstance(){
		Context first  = Context.getInstance();
		Context second = Context.getInstance();
		
		assertEquals(first, second);
	}
	@Test
	public void resetInstance_returnsNewInstance(){
		Context first  = Context.getInstance();
		Context second = Context.resetInstance();
		
		assertNotEquals(first, second);
	}
	@Test
	public void getModel(){
		Context c = Context.getInstance();
		Model m = c.getModel();
		
		assertNotNull(m);
		assertTrue(m.isSetName());
	}
}
