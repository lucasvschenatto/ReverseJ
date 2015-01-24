package util;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class Log {
	private static AtomicInteger lastId = new AtomicInteger(0);
	private int localId;
	public Timestamp timeStamp;
	
	public Log(){
		timeStamp = Timestamp.from(Instant.now());
		localId = lastId.incrementAndGet();
	}
	public int getId() {
		return localId;
	}
	public Timestamp getTimestamp() {
		return timeStamp;
	}
		
//	public Object caller;
//	public Class<?> classType;
//	public String className;
//	public String methodName;
//	public ArrayList parameters;
//	public float timeStampReturn;
//	public String returnType;
//	public String returnValue;
//	public String methodType;
//	}
//	public Log(String className, String classType, String methodName, String parameters ){
//		
//	}
//	public float addReturn(String returnValue){
//		return timeStampReturn;
//	}

}
