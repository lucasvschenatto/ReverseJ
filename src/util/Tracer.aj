package util;

//import org.aspectj.lang.Signature;
import java.lang.reflect.*;

import org.aspectj.lang.Signature;

public aspect Tracer {
	private static StringBuffer str = new StringBuffer();
	private static boolean running;
	private static Log result;
	
	public static Log getLastLog(){
		return result;
	}


	public static boolean isRunning() {
		return running;
	}
	
	pointcut pickUpPublicConstructor():
		(execution(*.new(..)) ||
		call(* *.*(..))) &&
		!within(Tracer);
	pointcut pickUpPrivateConstructor():
		!within(Tracer);
	pointcut pickUpPrivateVoidMethod():
		!within(Tracer);
	pointcut pickUpPublicVoidMethod():
		!within(Tracer);
	pointcut pickUpPrivateReturnMethod():
		!within(Tracer);
	pointcut pickUpPublicReturnMethod():
		!within(Tracer);
	
	before():
		pickUpPublicConstructor(){
		Class<?> caller = thisJoinPoint.getSignature().getDeclaringType();
		Method[] allMethods = caller.getDeclaredMethods();
		System.out.println(caller.getName());
//		for (Method method : allMethods) {
//			System.out.println("**");
//			System.out.println(method.getName());
//			Class<?>[] params = method.getParameterTypes();
//			for (Class<?> class1 : params) {
//				System.out.println(class1.getName());
//			}
//		}
		Signature s = thisJoinPointStaticPart.getSignature();
		String methodName = s.getName();
		if (methodName.equals(""))
			methodName = "new";
		System.out.println(methodName);
		System.out.println("-----------------------");
		
		
		
		
		
//		try {
//			Method m = caller.getMethod(caller.getName(), );
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			System.out.println(caller.getName());
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
////				thisJoinPoint.getSignature().getName();
//		thisJoinPoint.getArgs();
//		result.classType = thisJoinPoint.getSignature().getDeclaringType();
//		System.out.println(thisJoinPoint.getSourceLocation());
//		System.out.println(thisJoinPointStaticPart.getSignature().getDeclaringTypeName());
		
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
		result = new Log();
		str.delete(0, str.length());
	}

	public static void stop() {
		running = false;
	}

	  
	

}
