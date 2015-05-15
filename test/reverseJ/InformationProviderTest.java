package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class InformationProviderTest {
	RecorderStorage storage;
	InformationProvider provider;

	@Before
	public void setUp() throws Exception {
		storage = new InformationStorage();
		provider = (InformationStorage)storage;
	}
	@Test
	public void getNext() {		
		Information expected1 = InformationFactory.createDummy("nome Fulano");
		Information expected2 = InformationFactory.createDummy("idade 100");
		storage.addInformation(expected1);
		storage.addInformation(expected2);
		
		Information actual1 = provider.getNext();
		Information actual2 = provider.getNext();
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}
	@Test
	public void getNext_IfStorageIsEmpty_ReturnsNull() {
		provider = new InformationStorage();
		Information actual = provider.getNext();		
		assertNull(actual);
	}
	@Test
	public void getNext_AfterLast_ReturnsNull() {		
		Information info = InformationFactory.createDummy("nome Fulano");
		storage.addInformation(info);
		storage.addInformation(info);
		
		provider.getNext();
		provider.getNext();
		Information actual3 = provider.getNext();
		
		assertNull(actual3);
	}

	@Test
	public void getAllInformations() {		
		Information info1 = InformationFactory.createDummy("nome Fulano");
		Information info2 = InformationFactory.createDummy("idade 100");
		
		storage.addInformation(info1);
		storage.addInformation(info2);
		
		List <Information> actual = provider.getAll();
		List <Information> expected = new LinkedList <Information>();
		expected.add(info1);
		expected.add(info2);
		
		assertInformationListEquals(expected, actual);
	}
	
	private static void assertInformationListEquals(List<Information> expected, List<Information> actual){
		assertTrue("Different size", expected.size() == actual.size());
		assertTrue("Different objects",actual.containsAll(expected));
	}
}
