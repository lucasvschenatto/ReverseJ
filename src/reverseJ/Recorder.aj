package reverseJ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MemberSignature;
import org.aspectj.lang.reflect.MethodSignature;

public aspect Recorder {
	private static Log log = new Log();
	
	pointcut immune():!within(Log)&&!call(* Log.*(..))&& !within(Recorder)
						&&!call(* Recorder.*(..));
	pointcut method():
		call(* *.*(..))&& immune();
	pointcut constructor():
		call(*.new(..))&& immune();
	
	before(Object caller):constructor()&&this(caller){
		Signature s = thisJoinPoint.getSignature();
		String callerName = caller.getClass().getCanonicalName();
		String targetName = s.getDeclaringType().getCanonicalName();
		String methodName = s.getName();
		String signature = generateSignature(s);
		log.addInformation("caller", callerName);
		log.addInformation("target", targetName);
		log.addInformation("method", methodName);
		log.addInformation("signature", signature);
	}
	after() returning (Object r):constructor(){
		if(r != null)
			log.addInformation("return", r.getClass().getCanonicalName());
		else
			log.addInformation("return", "void");
	}
	
	before(Object caller):method()&& this(caller){
		Signature s = thisJoinPoint.getSignature();
		String callerName = caller.getClass().getCanonicalName();
		String targetName = s.getDeclaringType().getCanonicalName();
		String methodName = s.getName();
		String signature = generateSignature(s);
		log.addInformation("caller", callerName);
		log.addInformation("target", targetName);
		log.addInformation("method", methodName);
		log.addInformation("signature", signature);
		}
	after() returning (Object r):method()&&!call(private * *.*(..)){
		if(r != null)
			log.addInformation("return", r.getClass().getCanonicalName());
		else
			log.addInformation("return", "void");
	}
	
	@SuppressWarnings("all")
	private String generateSignature(Signature sig) {
		String signature = "";
		String[] name = ((CodeSignature)sig).getParameterNames();
		Class[] type = ((CodeSignature)sig).getParameterTypes();
		signature = signature.concat("(");
		for (int i = 0; i < type.length; i++) {
			if(i != 0)
				signature = signature.concat(", ");
			signature = signature.concat(type[i].getCanonicalName() + " " + name[i]);
		}
		signature = signature.concat(")");
		return signature;
	}
//	private String generateParameters(JoinPoint j) {
//		Object[] paramValues = j.getArgs();
//		return null;
//	}
	
	public static void determineLog(Log newLog){
		log = newLog;
	}
}
