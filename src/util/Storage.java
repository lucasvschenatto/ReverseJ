package util;

import java.util.*;

public class Storage{
	private List<Log> storedLogs;
	private boolean opened;
	public Storage(){
		storedLogs = new LinkedList<Log>();
		opened = true;
	}
	public void addLog(Log newLog) {
		if(opened)
			storedLogs.add(newLog);
	}
	public boolean contains(Log searchedLog) {
		return storedLogs.contains(searchedLog);
	}
	public void close() {
		opened = false;
	}
	public Log getLast() {
		Log result = null;
		if(!storedLogs.isEmpty())
			result = storedLogs.get(storedLogs.size()-1);
		return result;
	}
	public Log getFirst() {
		Log result = null;
		if(!storedLogs.isEmpty())
			result = storedLogs.get(0);
		return result;
	}
	
	
//	public Storage(){
//		executionStartTime = 0;
//	}
//	public void close(){
//		executionEndTime = 0;
//	}
//	public void insertLog(Log log){
//		
//	}
//	public Log[] getAllLogs(){
//		return storedLogs;
//	}
//	public Log getLog(float idNumber){
//		return new Log();
//	}
//	public float getStartTime(){
//		return executionStartTime;
//	}
//	public float getEndTime(){
//		return executionEndTime;
//	}
}
