package reverseJ;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class SequenceDiagramGeneratorTest {
	SequenceDiagramGenerator generator;
	@Before
	public void setUp() throws Exception {
		generator = new SequenceDiagramGenerator();
	}
	@Test
	public void setProvider() {
		assertNull(generator.getInfoProvider());
		InfoProvider provider = new Log();
		generator.setInfoProvider(provider);
		InfoProvider actual = generator.getInfoProvider();
		assertEquals(provider, actual);
	}
	@Test
	public void setContextPackage(){
		assertNull(generator.getContextPackage());
		String expected = "packageTest";
		
		generator.setContextPackage(expected);
		String actual = generator.getContextPackage().getName();
		
		assertEquals(expected, actual);
	}
	@Test
	public void setContextInteraction(){
		assertNull(generator.getContextInteraction());
		String expected = "interactionTest";
		
		generator.setContextInteraction(expected);
		String actual = generator.getContextInteraction().getName();
		
		assertEquals(expected, actual);
	}
	@Test
	public void createLifeline(){
		String expected = "lifelineTest";
		assertNull(generator.getLifeline(expected));		
		
		generator.createLifeline(expected);
		
		assertNotNull(generator.getLifeline(expected));
		String actual = generator.getLifeline(expected).getName();
		assertEquals(expected, actual);
		
		assertNotNull(generator.getLifeline(expected).getInteraction());
	}
}
