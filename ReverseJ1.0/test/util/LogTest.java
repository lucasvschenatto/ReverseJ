package util;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Lucas
 *
 */
public class LogTest {
	private Log localLog;

	@Before
	public void setUp(){
		localLog = createNewLog();
	}

	@After
	public void tearDown(){
		localLog = null;
	}

	@Test
	public void constructor() {
		localLog = createNewLog();
		assertNotNull(localLog);
	}
	
	@Test
	public void incrementIds(){
		int first = localLog.getId();
		localLog = createNewLog();
		int second = localLog.getId();
		assertEquals(first, second - 1);
	}
	@Test
	public void registertimeStamp(){
		Log localLog = createNewLog();
		Timestamp ti = Timestamp.from(Instant.now());
		assertTrue(ti.equals(localLog.getTimestamp()));
	}
	@Test
	public void close(){
		localLog = createNewLog();
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		localLog.close();
		assertEquals(1000, localLog.getDuration(), 20);
		
	}
	
//	adicionar o tempo em que o método levou para retornar
//	também é bom criar testes com os nomes da classe, método, parâmetros, etc

	private Log createNewLog() {
		return new Log();
	}
}
