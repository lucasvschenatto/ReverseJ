package reversej.tracer;

import static reversej.tracer.Context.*;

import java.util.LinkedList;
import java.util.List;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

public aspect Tracer {
	
	pointcut immune():if(RUNNING)
		&&(!within(RepositoryRecorder+))
		&&!call(* TracerImmunity+.*(..))
		&&!execution(* TracerImmunity+.*(..))
		&&!within(TracerImmunity+)
		&&!call(java*..new(..))
		&&!execution(java*..new(..))
		&&!initialization(java*..new(..))
		&&!preinitialization(java*..new(..))
		&&!call(* java*..*(..))
		&&!execution(* java*..*(..))
		&&!within(java*..*)
		&&(!call(* RepositoryRecorder+.*(..)))
		&&(!call(RepositoryRecorder+.new(..)))
		&&(!within(reversej.diagram.Information+))
		&&(!within(reversej.diagram.InformationFactory+))
		&& !within(Tracer+)
		&& !within(TracerController+)
		&& !within(Context+)
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
	
	pointcut initialization_():
		initialization(*.new(..))&&immune();
	
	pointcut exceptionHandle():
		handler(Exception)&&immune();
	
	before():constructorExecution()||methodExecution(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String className = s.getDeclaringType().getCanonicalName();
		String modifiers = generateModifiers(s);
		String methodName = s.getName();
		String parameters = generateParameters(s);
		REPOSITORY.addInformation("Class",className);
		REPOSITORY.addInformation("Modifiers",modifiers);
		REPOSITORY.addInformation("Method",methodName);
		REPOSITORY.addInformation("Parameters",parameters);
	}
	
	before():methodCall(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String declaredTargetName = s.getDeclaringType().getCanonicalName();		
		if(s.getDeclaringType().isInterface()){
			REPOSITORY.addInformation("Interface",declaredTargetName);
		}	
	}
	before():exceptionHandle(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String handlerName = s.getDeclaringType().getCanonicalName();
		REPOSITORY.addInformation("Handler",handlerName);
	}
	
	after() returning (Object r):constructorCall()||methodExecution(){
		if(r != null){
			if(isSubSuper(r.getClass()))
				REPOSITORY.addInformation("SubReturn",r.getClass().getCanonicalName());
			else
			REPOSITORY.addInformation("Return",r.getClass().getCanonicalName());
		}	
		else
			REPOSITORY.addInformation("Return","void");
	}
	
	after() throwing (Exception e):methodExecution(){
		String exceptionName = e.getClass().getCanonicalName();
		REPOSITORY.addInformation("Throw",exceptionName);
	}

	after(Object currentObject):initialization_()&& this(currentObject){
		Class<?> constructorClass = thisJoinPointStaticPart.getSignature().getDeclaringType();
		Class<?> currentClass = currentObject.getClass();
		if(!constructorClass.isInterface() && constructorClass != currentClass){
			SUB_SUPERS.add(new SubSuper(currentClass,constructorClass));
			REPOSITORY.addInformation("SuperReturn", constructorClass.getCanonicalName());
		}		
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
	private boolean isSubSuper(Class<?> sub) {
		boolean result = false;
		for(SubSuper s : SUB_SUPERS)
			if(s.sub == sub){
				result = true;	
			}
		return result;
	}


}
