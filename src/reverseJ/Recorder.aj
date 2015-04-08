package reverseJ;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

import reverseJ.Log.NotFoundInformationException;

public aspect Recorder {
	private static RecorderStorage storage;
	private static RecordingOrder[] order;
	private static boolean running;
	pointcut immune():if(running)	
		&&(!within(RecorderStorage+)||within(RecorderStorageTest+))
		&&(!call(* RecorderStorage+.*(..))||call(* RecorderStorageTest+.*(..)))
		&& !within(Recorder+)
		&&!call(* Recorder+.*(..));
	pointcut interfacePublic():
		execution(public * *.*(..))
		&&immune();
	pointcut methodAll():
		call(* *.*(..))&&immune();
	pointcut methodPublic():
		(call(public * *.*(..)))
		&&immune();
	pointcut methodPrivate():
		execution(private * *.*(..))&&immune();
	
	pointcut constructorAll():
		call(*.new(..))&& immune();
	pointcut constructorPublic():
		call(public *.new(..))&& immune();
	pointcut constructorPrivate():
		execution(private *.new(..))&& immune();
	
	before(Object caller):constructorAll()&&this(caller){
		Signature s = thisJoinPointStaticPart.getSignature();
		String callerName = caller.getClass().getCanonicalName();
		String targetName = s.getDeclaringType().getCanonicalName();
		storage.addInformation("caller", callerName);
		storage.addInformation("target", targetName);
	}
	before():constructorPrivate(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		storage.addInformation("method", methodName);
		storage.addInformation("signature", signature);
	}	
	before():constructorPublic(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		storage.addInformation("method", methodName);
		storage.addInformation("signature", signature);
	}
	after() returning (Object r):constructorAll(){
		if(r != null)
			storage.addInformation("return", r.getClass().getCanonicalName());
		else
			storage.addInformation("return", "void");
	}

	before(Object caller):methodAll()&&this(caller){
		Signature s = thisJoinPointStaticPart.getSignature();
		String callerName = caller.getClass().getCanonicalName();
		String declaredTargetName = s.getDeclaringType().getCanonicalName();
		storage.addInformation("caller", callerName);
		if(s.getDeclaringType().isInterface()){
			String targetName = thisJoinPoint.getTarget().getClass().getCanonicalName();
			storage.addInformation("interface", declaredTargetName);
			storage.addInformation("target", targetName);
		}
		else{
			storage.addInformation("target", declaredTargetName);
		}
	}
	
	before():methodPrivate(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		storage.addInformation("method", methodName);
		storage.addInformation("signature", signature);
	}
	after() returning (Object r):methodPrivate()&&!call(private * *.*(..)){
		if(r != null)
			storage.addInformation("return", r.getClass().getCanonicalName());
		else
			storage.addInformation("return", "void");
	}
	
	before():methodPublic(){
		try {
			storage.describe("interface");
		} catch (NotFoundInformationException e){
			Signature s = thisJoinPoint.getSignature();
			String methodName = s.getName();
			String signature = generateSignature(s);
			storage.addInformation("method", methodName);
			storage.addInformation("signature", signature);
		}		
	}
	after() returning (Object r):methodPublic()&&!call(private * *.*(..)){
		if(r != null)
			storage.addInformation("return", r.getClass().getCanonicalName());
		else
			storage.addInformation("return", "void");
	}
	
	before(Object target):interfacePublic()&&target(target){
		try {
			storage.describe("interface");
			Signature s = thisJoinPointStaticPart.getSignature();
			String methodName = s.getName();
			String signature = generateSignature(s);
			storage.addInformation("method", methodName);
			storage.addInformation("signature", signature);
			System.out.println("signature---" + signature);
		} catch (NotFoundInformationException e) {
		}
	}
	private String generateSignature(Signature sig) {
		String signature = "";
		String[] name = ((CodeSignature)sig).getParameterNames();
		Class<?>[] type = ((CodeSignature)sig).getParameterTypes();
		signature = signature.concat("(");
		for (int i = 0; i < type.length; i++) {
			if(i != 0)
				signature = signature.concat(", ");
			signature = signature.concat(type[i].getCanonicalName() + " " + name[i]);
		}
		signature = signature.concat(")");
		return signature;
	}
	
	public static void determineStorage(RecorderStorage newStorage){
		storage = newStorage;
	}
	public static void determineOrder(RecordingOrder[] newOrder){
		order = newOrder;
	}
	public static RecordingOrder[] getOrder(){
		return order;
	}
	public static void start(RecorderStorage newStorage){
		determineStorage(newStorage);
		running = true;
	}
	public static void stop(){
		running = false;
	}
	public static RecorderStorage getStorage(){
		return storage;
	}
	public static boolean isRunning(){
		return running;
	}
}
