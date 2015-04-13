package reverseJ;

import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

public aspect Recorder {
	private static RecorderStorage storage;
	private static RecorderInfo[] order;
	private static boolean running;
	
	pointcut immune():if(running)
		&&(!within(RecorderStorage+))
		&&!execution(* RecorderStorageTest+.*(..))
		&&(!call(* RecorderStorage+.*(..)))
		&&(!call(RecorderStorage+.new(..)))
		&&(!within(RecorderInfo+)&&!call(* RecorderInfo+.*(..)))
		&& !within(Recorder+)
		&&!call(* Recorder+.*(..));
	
	pointcut methodCall():
		call(* *.*(..))&&immune();
	pointcut methodExecution():
		execution(* *.*(..))&&immune();
	
	pointcut constructorCall():
		call(*.new(..))&& immune();
	pointcut constructorExecution():
		execution(*.new(..))&&immune();
	
//	before(Object caller):constructorCall()&&this(caller){
//		Signature s = thisJoinPointStaticPart.getSignature();
//		String callerName = caller.getClass().getCanonicalName();
//		String targetName = s.getDeclaringType().getCanonicalName();		
//		storage.addInformation(InfoOrder.CALLER, callerName);
//		storage.addInformation(InfoOrder.TARGET, targetName);		
//	}
	before():constructorExecution()||methodExecution(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		String modifiers = generateModifiers(s);
		storage.addInformation(InfoOrder.MODIFIERS, modifiers);
		storage.addInformation(InfoOrder.METHOD, methodName);
		storage.addInformation(InfoOrder.SIGNATURE, signature);
	}
	after() returning (Object r):constructorCall()||methodExecution(){
		if(r != null)
			storage.addInformation(InfoOrder.RETURN, r.getClass().getCanonicalName());
		else
			storage.addInformation(InfoOrder.RETURN, "void");
	}

	before(Object caller):(constructorCall()||methodCall())&&this(caller){
		Signature s = thisJoinPointStaticPart.getSignature();
		String callerName = caller.getClass().getCanonicalName();
		String declaredTargetName = s.getDeclaringType().getCanonicalName();		
		storage.addInformation(InfoOrder.CALLER, callerName);
		if(s.getDeclaringType().isInterface()){
			String targetName = thisJoinPoint.getTarget().getClass().getCanonicalName();
			storage.addInformation(InfoOrder.INTERFACE, declaredTargetName);
			storage.addInformation(InfoOrder.TARGET, targetName);
		}
		else
			storage.addInformation(InfoOrder.TARGET, declaredTargetName);	
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
	private String generateModifiers(Signature sig) {
		int mod = sig.getModifiers();
		List<String> modifiers = accessControlModifiers(mod);
		modifiers.addAll(nonAccessModifiers(mod));
		return String.join(" ",modifiers);
	}
	private List<String> accessControlModifiers(int mod) {
		List<String> accessControlModifier = new LinkedList<>();
		if(java.lang.reflect.Modifier.isPublic(mod))
			accessControlModifier.add("public");
		if(java.lang.reflect.Modifier.isPrivate(mod))
			accessControlModifier.add("private");
		if(java.lang.reflect.Modifier.isProtected(mod))
			accessControlModifier.add("protected");
		return accessControlModifier;
	}
	private List<String> nonAccessModifiers(int mod) {
		List<String> nonAccessModifiers = new LinkedList<>();
		if(java.lang.reflect.Modifier.isFinal(mod))
			nonAccessModifiers.add("final");
		if(java.lang.reflect.Modifier.isSynchronized(mod))
			nonAccessModifiers.add("synchronized");
		if(java.lang.reflect.Modifier.isStrict(mod))
			nonAccessModifiers.add("strictfp");
		return nonAccessModifiers;
	}	
	public static void determineStorage(RecorderStorage newStorage){
		storage = newStorage;
	}
	public static void determineOrder(RecorderInfo[] newOrder){
		order = newOrder;
	}
	public static RecorderInfo[] getOrder(){
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
