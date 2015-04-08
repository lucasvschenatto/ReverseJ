package reverseJ;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

public aspect Recorder {
	private static RecorderStorage storage;
	private static RecorderInfo[] order;
	private static boolean running;
	
	pointcut immune():if(running)	
		&&(!within(RecorderStorage+)||within(RecorderStorageTest+))
		&&!execution(* RecorderStorageTest+.*(..))
		&&(!call(* RecorderStorage+.*(..))||call(* RecorderStorageTest+.*(..)))
		&&(!within(RecorderInfo+)&&!call(* RecorderInfo+.*(..)))
		&& !within(Recorder+)
		&&!call(* Recorder+.*(..));
	
	pointcut methodCall():
		call(* *.*(..))&&immune();
	pointcut methodExecution():
		(execution(public * *.*(..))||execution(private * *.*(..))||execution(protected * *.*(..)))
//		(!execution(private * *.*(..))&&execution(* *.*(..)))
		&&immune();
	
	pointcut constructorCall():
		call(*.new(..))&& immune();
	pointcut constructorExecution():
		execution(*.new(..))
		&& immune();
	
	before(Object caller):constructorCall()&&this(caller){
		Signature s = thisJoinPointStaticPart.getSignature();
		String callerName = caller.getClass().getCanonicalName();
		String targetName = s.getDeclaringType().getCanonicalName();		
		storage.addInformation(InfoOrder.CALLER, callerName);
		storage.addInformation(InfoOrder.TARGET, targetName);		
	}
	before():constructorExecution(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		String modifiers = generateModifiers(s);
		storage.addInformation(InfoOrder.MODIFIERS, modifiers);
		storage.addInformation(InfoOrder.METHOD, methodName);
		storage.addInformation(InfoOrder.SIGNATURE, signature);
	}
	after() returning (Object r):constructorCall(){
		if(r != null)
			storage.addInformation(InfoOrder.RETURN, r.getClass().getCanonicalName());
		else
			storage.addInformation(InfoOrder.RETURN, "void");
	}

	before(Object caller):methodCall()&&this(caller){
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

	before():methodExecution(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String modifiers = generateModifiers(s);
		String methodName = s.getName();
		String signature = generateSignature(s);
		storage.addInformation(InfoOrder.MODIFIERS, modifiers);
		storage.addInformation(InfoOrder.METHOD, methodName);
		storage.addInformation(InfoOrder.SIGNATURE, signature);
	}
	after() returning (Object r):methodExecution(){
		if(r != null)
			storage.addInformation(InfoOrder.RETURN, r.getClass().getCanonicalName());
		else
			storage.addInformation(InfoOrder.RETURN, "void");
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
		return generateAccessControlModifiers(mod) + generateNonAccessModifiers(mod);
	}
	private String generateAccessControlModifiers(int mod) {
		String accessControlModifier = new String();
		if(java.lang.reflect.Modifier.isPublic(mod))
			accessControlModifier = "public";
		if(java.lang.reflect.Modifier.isPrivate(mod))
			accessControlModifier = "private";
		if(java.lang.reflect.Modifier.isProtected(mod))
			accessControlModifier = "protected";
		return accessControlModifier;
	}
	private String generateNonAccessModifiers(int mod) {
		String nonAccessModifiers = new String();
		if(java.lang.reflect.Modifier.isPublic(mod))
			;
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
