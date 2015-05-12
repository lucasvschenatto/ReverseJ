package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import reverseJ.Log.Information;

public class InfoProviderTest {
	RecorderStorage storage;
	InfoProvider provider;

	@Before
	public void setUp() throws Exception {
		storage = new Log();
		provider = (Log)storage;
	}
	@Test
	public void getNext() {		
		Information expected1 = ((Log)storage).createInformation();
		expected1.setName("Name");
		expected1.setValue("Fulano");
		Information expected2 = ((Log)storage).createInformation();
		expected2.setName("Idade");
		expected2.setValue("100");		
		storage.addInformation(expected1);
		storage.addInformation(expected2);
		
		Information actual1 = provider.getNext();
		Information actual2 = provider.getNext();
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}

	@Test
	public void getAllInformations() {		
		Information info = ((Log)storage).createInformation();
		info.setName("Name");
		info.setValue("Fulano");
		Information info2 = ((Log)storage).createInformation();
		info2.setName("Idade");
		info2.setValue("100");
		
		storage.addInformation(info);
		storage.addInformation(info2);
		
		List <Information> actual = provider.getAll();
		List <Information> expected = new LinkedList <Information>();
		expected.add(info);
		expected.add(info2);
		
		assertInformationListEquals(expected, actual);
	}
	
	private static void assertInformationListEquals(List<Information> expected, List<Information> actual){
		assertTrue("Different size", expected.size() == actual.size());
		assertTrue("Different objects",actual.containsAll(expected));
	}
}
