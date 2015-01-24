package model;

public class Storage {
	private Log[] storedLogs;
	private float executionStartTime;
	private float executionEndTime;
	
	public Storage(){
		executionStartTime = 0;
	}
	public void close(){
		executionEndTime = 0;
	}
	public void insertLog(Log log){
		
	}
	public Log[] getAllLogs(){
		return storedLogs;
	}
	public Log getLog(float idNumber){
		return new Log();
	}
	public float getStartTime(){
		return executionStartTime;
	}
	public float getEndTime(){
		return executionEndTime;
	}
}
