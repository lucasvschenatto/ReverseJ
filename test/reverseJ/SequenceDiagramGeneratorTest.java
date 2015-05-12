package reverseJ;

import static org.junit.Assert.*;

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
		InfoProvider provider = new Log();
		generator.setInfoProvider(provider);
		InfoProvider actual = generator.getInfoProvider();
		assertEquals(provider, actual);
	}

}
