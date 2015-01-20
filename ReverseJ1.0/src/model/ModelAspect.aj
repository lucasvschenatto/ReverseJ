package model;

import org.aspectj.lang.Signature;
import teste.*;

public aspect ModelAspect {
	private static StringBuffer str = new StringBuffer();
	private static boolean running;
	private static Log traced;


	public static boolean isRunning() {
		return running;
	}
	
	pointcut traceConstructor():
		call( *.new(..));
	
	before( Object c):
		traceConstructor() &&
		this(c){
		traced.classType = c.toString();
		TracerTests.setResult(traced);
	}
//	after(C1 c, Object o):
////		(call (void C1.*()) ||
//		call( *.new())&&
////		execution(*.new())&&
//		this(o) &&
//		target(c){
//			System.out.println(o.getClass() +" called "+c.getClass());
//		}

//	pointcut traceMethods()               
//	    : (execution(* *.*(..))
//	        || execution(*.new(..))) 
//	      && 
//	    	!execution(* *.*$*(..)) 
//	      && !within(ModelAspect)
//	      && if(running)
//	      
//	      // omit problematic inner classes.
//	      && !within(Pointcuts.*)
//	      && !within(ProxyFactory.*)
//	      && !within(ClassProxyCreator.*)
//	      // new conditions
//	      ;
//
//	before() : traceMethods() {
//		Signature s = thisJoinPointStaticPart.getSignature();
//		String methodName = s.getName();
//
//		// use "new" for constructors.
//		if (methodName.equals(""))
//			methodName = "new";
//
//		if (!s.getDeclaringType().getSimpleName().equalsIgnoreCase("")){
//			str.append("<class name=\"" + s.getDeclaringType().getSimpleName()
//					+ "\">\n");
//			
//			System.out.println("'"+s.getDeclaringType().getSimpleName()+"'");
//			System.out.println("getModifiers --> "+s.getModifiers());
//			System.out.println("getName --> "+s.getName());
//			System.out.println("getDeclaringType --> "+s.getDeclaringType());
//		}
//
//
//	}
//	
//	after() : traceMethods() {
//		Signature s = thisJoinPointStaticPart.getSignature();
//		if (!s.getDeclaringType().getSimpleName().equalsIgnoreCase("")) {
//			s.getName();
//			str.append("\n</class>");
//		}
//		
//	}

	public static void start() {
		running = true;
		traced = new Log();
		str.delete(0, str.length());
	}

	public static void stop() {
		running = false;
	}

	  
	

}
