package util;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.Instant;

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
	}

	@After
	public void tearDown(){
	}

	@Test
	public void constructor() {
		localLog = new Log();
		assertNotNull(localLog);
	}
	
	@Test
	public void incrementIds(){
		Log first  = new Log();
		Log second = new Log();
		assertEquals(first.getId(), second.getId()-1);		
	}
	@Test
	public void registertimeStamp(){
		Log localLog = new Log();
		Timestamp ti = Timestamp.from(Instant.now());
		assertTrue(ti.equals(localLog.getTimestamp()));
	}
	@Test
//	adicionar o tempo em que o método levou para retornar
//	também é bom criar testes com os nomes da classe, método, parâmetros, etc
	public void close(){
		Log localLog = new Log();
	}
}
