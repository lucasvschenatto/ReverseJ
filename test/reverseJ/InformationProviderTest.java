package reverseJ;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class InformationProviderTest {
	RecorderStorage recorderStorage;
	InformationProvider provider;

	@Before
	public void setUp() throws Exception {
		recorderStorage = new InformationStorageProvider();
		provider = (InformationStorageProvider)recorderStorage;
	}
	@Test
	public void getNext() {		
		Information expected1 = InformationFactory.createDummy("nome Fulano");
		Information expected2 = InformationFactory.createDummy("idade 100");
		recorderStorage.addInformation(expected1);
		recorderStorage.addInformation(expected2);
		
		Information actual1 = provider.getNext();
		Information actual2 = provider.getNext();
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
	}
	@Test
	public void getNext_IfStorageIsEmpty_ReturnsNull() {
		provider = new InformationStorageProvider();
		Information actual = provider.getNext();		
		assertNull(actual);
	}
	@Test
	public void getNext_AfterLast_ReturnsNull() {		
		Information info = InformationFactory.createDummy("nome Fulano");
		recorderStorage.addInformation(info);
		recorderStorage.addInformation(info);
		
		provider.getNext();
		provider.getNext();
		Information actual3 = provider.getNext();
		
		assertNull(actual3);
	}

	@Test
	public void getAllInformations() {		
		Information info1 = InformationFactory.createDummy("nome Fulano");
		Information info2 = InformationFactory.createDummy("idade 100");
		
		recorderStorage.addInformation(info1);
		recorderStorage.addInformation(info2);
		
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
