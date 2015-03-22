package reverseJ;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

public aspect Recorder {
	private static Log log = new Log();
	
	pointcut immune():!within(Log)&&!call(* Log.*(..))&& !within(Recorder)
						&&!call(* Recorder.*(..));
	pointcut method():
		call(* *.*(..))&& immune();
	pointcut constructor():
		execution(*.new(..))&& immune();
	
	before(Object t, Object c):constructor()&& target(t)&&this(c){
//		String callerName = c.getClass().getCanonicalName();
		String value = t.getClass().getCanonicalName();
//		log.addInformation("caller", callerName);
		log.addInformation("constructor", value);
	}
	
	before(Object caller):method()&& this(caller){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String targetName = s.getDeclaringType().getCanonicalName();
		String callerName = caller.getClass().getCanonicalName();
		String signature = generateSignature(thisJoinPoint);
		log.addInformation("caller", callerName);
		log.addInformation("target", targetName);
		log.addInformation("method", methodName);
		log.addInformation("signature", signature);
		}
	after() returning (Object r):method(){
		if(r != null)
			log.addInformation("return", r.getClass().getCanonicalName());
		else
			log.addInformation("return", "void");
	}
	
	@SuppressWarnings("all")
	private String generateSignature(JoinPoint j) {
		String signature = "";
		String[] name = ((CodeSignature)j.getSignature()).getParameterNames();
		Class[] type = ((CodeSignature)j.getSignature()).getParameterTypes();
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
