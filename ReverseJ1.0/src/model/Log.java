package model;

import java.util.ArrayList;

public class Log {
	public float id;
	public float timeStampCall;
	public Object caller;
	public Class<?> classType;
	public String className;
//	public String methodName;
	public ArrayList parameters;
	public float timeStampReturn;
	public String returnType;
//	public String returnValue;
//	public String methodType;
	public Log(){
//		parameters = new ArrayList<>();
	}
	public Log(String className, String classType, String methodName, String parameters ){
		
	}
	public float addReturn(String returnType, String returnValue){
		return timeStampReturn;
	}

}
