package util;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageTest {
	private Storage storage;
	private Log log;

	@Test
	public void addLog(){
		storage.addLog(log);
		assertTrue(storage.contains(log));
	}
	@Test
	public void WhenClosedCannotAddLogs(){
		storage.close();
		storage.addLog(log);
		assertFalse(storage.contains(log));
	}
	@Test
	public void getLastLog(){
		storage.addLog(log);
		log = createLog();
		storage.addLog(log);
		log = createLog();
		storage.addLog(log);
		assertEquals(log, storage.getLast());
	}
	@Test
	public void storageEmptyLastLogReturnsNull(){
		assertNull(storage.getLast());
	}
	@Test
	public void getFirstLog(){
		Log first = log;
		storage.addLog(first);
		log = createLog();
		storage.addLog(log);
		log = createLog();
		storage.addLog(log);
		assertEquals(first, storage.getFirst());
	}
	@Test
	public void storageEmptyFirstLogReturnsNull(){
		assertNull(storage.getFirst());
	}
	
	@Before
	public void setup(){
		storage = createStorage();
		log = createLog();
	}
	private Log createLog() {
		return new Log();
	}
	private Storage createStorage() {
		return new Storage();
	}
//falta fazer o getallLogs
}
