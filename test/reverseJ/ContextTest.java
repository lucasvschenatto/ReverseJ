package reverseJ;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ContextTest {
	Context context;
	@Before
	public void setUp() throws Exception {
		context = null;
	}
	@Test
	public void getPackage(){
		String expected = "packageTest";
		context = new Context(expected,null);
		
		String actual = context.getModel().getName();
		
		assertEquals(expected, actual);
	}
	@Test
	public void getInteraction(){
		String expected = "interactionTest";
		context = new Context(null,expected);
		
		String actual = context.getInteraction().getName();
		
		assertEquals(expected, actual);
	}
}
