package util;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

public class Log {
	private static AtomicInteger lastId = new AtomicInteger(0);
	private int localId;
	public Timestamp startTimeStamp;
	public Timestamp endTimeStamp;
	
	public Log(){
		startTimeStamp = Timestamp.from(Instant.now());
		localId = lastId.incrementAndGet();
	}
	public int getId() {
		return localId;
	}
	public Timestamp getTimestamp() {
		return startTimeStamp;
	}
	public void close(){
		endTimeStamp = Timestamp.from(Instant.now());
	}
	/**
	 * @return long milliseconds
	 */
	public long getDuration(){
		return Duration.between(startTimeStamp.toInstant(), Instant.now()).toMillis();
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
