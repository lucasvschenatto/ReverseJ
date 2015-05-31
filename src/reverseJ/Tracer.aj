package reverseJ;

import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

public aspect Tracer {
	private static RecorderStorage recorderStorage;
	private static boolean running;
	
	pointcut immune():if(running)
		&&(!within(RecorderStorage+))
		&&!execution(* TracerImmunity+.*(..))
		&&(!call(* RecorderStorage+.*(..)))
		&&(!call(RecorderStorage+.new(..)))
		&&(!within(Information+))
		&&(!within(InformationFactory+))
		&& !within(Tracer+)
		&&!call(* Tracer+.*(..))
		;
	pointcut withinClass():within(*)&&immune();
	pointcut methodCall():
		call(* *.*(..))&&immune();
	pointcut methodExecution():
		execution(* *.*(..))&&immune();
	
	pointcut constructorCall():
		call(*.new(..))&& immune();
	pointcut constructorExecution():
		execution(*.new(..))&&immune();
	
	pointcut exceptionHandle():
		handler(Exception)&&immune();
	
	before():constructorExecution()||methodExecution(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String className = s.getDeclaringType().getCanonicalName();
		String modifiers = generateModifiers(s);
		String methodName = s.getName();
		String parameters = generateParameters(s);
		recorderStorage.addInformation(InformationFactory.createClass(className));
		recorderStorage.addInformation(InformationFactory.createModifiers(modifiers));
		recorderStorage.addInformation(InformationFactory.createMethod(methodName));
		recorderStorage.addInformation(InformationFactory.createParameters(parameters));
	}
	
	before():methodCall(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String declaredTargetName = s.getDeclaringType().getCanonicalName();		
		if(s.getDeclaringType().isInterface()){
			String targetName = thisJoinPoint.getTarget().getClass().getCanonicalName();
			recorderStorage.addInformation(InformationFactory.createInterface(declaredTargetName));
//			recorderStorage.addInformation(InformationFactory.createTarget(targetName));
		}
//		else
//			recorderStorage.addInformation(InformationFactory.createTarget(declaredTargetName));	
	}
	before():exceptionHandle(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String handlerName = s.getDeclaringType().getCanonicalName();
		recorderStorage.addInformation(InformationFactory.createHandler(handlerName));
	}
	
	after() returning (Object r):constructorCall()||methodExecution(){
		if(r != null)
			recorderStorage.addInformation(InformationFactory.createReturn(r.getClass().getCanonicalName()));
		else
			recorderStorage.addInformation(InformationFactory.createReturn("void"));
	}
	after() throwing (Exception e):methodExecution(){
		String exceptionName = e.getClass().getCanonicalName();
		recorderStorage.addInformation(InformationFactory.createThrow(exceptionName));
	}


	private String generateParameters(Signature sig) {
		String parameters = "";
		String[] name = ((CodeSignature)sig).getParameterNames();
		Class<?>[] type = ((CodeSignature)sig).getParameterTypes();
		for (int i = 0; i < type.length; i++) {
			if(i != 0)
				parameters = parameters.concat(", ");
			parameters = parameters.concat(type[i].getCanonicalName() + " " + name[i]);
		}
		return parameters;
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
		else if(java.lang.reflect.Modifier.isPrivate(mod))
			accessControlModifier.add("private");
		else if(java.lang.reflect.Modifier.isProtected(mod))
			accessControlModifier.add("protected");
		else
			accessControlModifier.add("package level");
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
		recorderStorage = newStorage;
	}
	public static void start(RecorderStorage newStorage){
		determineStorage(newStorage);
		running = true;
	}
	public static void stop(){
		running = false;
	}
	public static RecorderStorage getStorage(){
		return recorderStorage;
	}
	public static boolean isRunning(){
		return running;
	}
}
