package reverseJ;

import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.CodeSignature;

public aspect Recorder {
	private static Log log = new Log();
	private static InfoOrder[] order;
	
	pointcut immune():!within(Log)&&!call(* Log.*(..))&& !within(Recorder)
						&&!call(* Recorder.*(..));
	pointcut methodAll():
		call(* *.*(..))&&immune();
	pointcut methodPublic():
		call(public * *.*(..))&&immune();
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
		log.addInformation("caller", callerName);
		log.addInformation("target", targetName);
	}
	before():constructorPrivate(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		log.addInformation("method", methodName);
		log.addInformation("signature", signature);
	}
	
	before():constructorPublic(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		log.addInformation("method", methodName);
		log.addInformation("signature", signature);
	}
	after() returning (Object r):constructorAll(){
		if(r != null)
			log.addInformation("return", r.getClass().getCanonicalName());
		else
			log.addInformation("return", "void");
	}
	
	before(Object caller):methodAll()&&this(caller){
		Signature s = thisJoinPointStaticPart.getSignature();
		String callerName = caller.getClass().getCanonicalName();
		String targetName = s.getDeclaringType().getCanonicalName();
		log.addInformation("caller", callerName);
		log.addInformation("target", targetName);
	}
	
	before():methodPrivate(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		log.addInformation("method", methodName);
		log.addInformation("signature", signature);
	}
	after() returning (Object r):methodPrivate()&&!call(private * *.*(..)){
		if(r != null)
			log.addInformation("return", r.getClass().getCanonicalName());
		else
			log.addInformation("return", "void");
	}
	
	before():methodPublic(){
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		String signature = generateSignature(s);
		log.addInformation("method", methodName);
		log.addInformation("signature", signature);
		}
	after() returning (Object r):methodPublic()&&!call(private * *.*(..)){
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
	
	public static void determineLog(Log newLog){
		log = newLog;
	}
	public static void determineOrder(InfoOrder[] newOrder){
		order = newOrder;
	}
	public static InfoOrder[] getOrder(){
		return order;
	}
}
