package reverseJ;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.uml2.uml.Package;

public class ClassDiagram implements DiagramStrategy {
	private final String constructorCall = "<init>";
	private final String void_ = "void";
	private ClassDiagramFrameworkAdapter adapter;
	private List<String> attributeClasses;
	private List<String> attributeInterfaces;
	private List<String> attributeTypes;
	private List<String> attributeMethods;
	private List<String>[] attributeUnidirectionals;
	private List<String>[] attributeBidirectionals;
	
	public ClassDiagram(ClassDiagramFrameworkAdapter frameworkAdapter) {
		adapter = frameworkAdapter;
		attributeClasses = new LinkedList<String>();
		attributeInterfaces = new LinkedList<String>();
		attributeTypes = new LinkedList<String>();
		attributeMethods = new LinkedList<String>();
		attributeUnidirectionals = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		attributeBidirectionals = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
	}

	@Override
	public Package generate(List<Information> informations) {
		generateClasses(informations);		
		generateInterfaces(informations);
		generateImplementations(informations);
		generateAssociations(informations);
		generateDependencies(informations);
		generateTypes(informations);
		generateMethods(informations);
		
		return adapter.getPackage();
	}

	private void generateDependencies(List<Information> informations) {
		List<String>[] dependencies = getDependencyPairs(informations);
		dependencies = removeDuplicatedUniDirectionals(dependencies);
		dependencies = filterNonUnidirectionals(dependencies, attributeUnidirectionals);
		dependencies = filterNonBidirectionals(dependencies, attributeBidirectionals);
		for (int i = 0; i < dependencies[0].size(); i++) {
			String caller = dependencies[0].get(i);
			String target = dependencies[1].get(i);
			adapter.createDependency(caller, target);
		}
	}

	private List<String>[] filterNonBidirectionals(List<String>[] dependencies,
			List<String>[] bidirectionals) {
		dependencies = filterNonUnidirectionals(dependencies, bidirectionals);
		
		List<String> aux  = bidirectionals[0];
		bidirectionals[0] = bidirectionals[1];
		bidirectionals[1] = aux;
		dependencies = filterNonUnidirectionals(dependencies, bidirectionals);
		
		return dependencies;
	}

	private List<String>[] filterNonUnidirectionals(List<String>[] dependencies,List<String>[] unidirectionals) {
		for (String caller1 : dependencies[0]) {
			String target1 = dependencies[1].get(dependencies[0].indexOf(caller1));
			for(int j = 0; j < unidirectionals[0].size();j++){
				String caller2 = unidirectionals[0].get(j);
				String target2 = unidirectionals[1].get(j);
				if((!caller1.equals(caller2)) && (!target1.equals(target2))){
					dependencies[0].remove(caller1);
					dependencies[1].remove(target1);
				}
			}
		}
//		for (int i = 0; i < dependencies[0].size(); i++) {
//			String caller1 = dependencies[0].get(i);
//			String target1 = dependencies[1].get(i);
//			for(int j = 0; j < unidirectionals[0].size();j++){
//				String caller2 = unidirectionals[0].get(j);
//				String target2 = unidirectionals[1].get(j);
//				if(caller1.equals(caller2) && target1.equals(target2)){
//					dependencies[0].remove(i);
//					dependencies[1].remove(i);
//				}
//			}
//		}
		return dependencies;
	}

	private List<String>[] getDependencyPairs(List<Information> informations) {
		@SuppressWarnings("unchecked")
		List<String>[] dependencies = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		
		Stack<String> stack = new Stack<String>();
		boolean isMethod = false;
		Information last = InformationFactory.createDummy(null);
		for (Information information : informations) {
			if(information instanceof IInterface)
				stack.push(information.getValue());
			else if(information instanceof IClass && !(last instanceof IInterface))
				stack.push(information.getValue());
			else if(information instanceof IMethod)
				isMethod = (information.getValue() != constructorCall)? true:false;
			else if(information instanceof IReturn){
				String poped = stack.pop();
				if(!stack.empty()){					 
					if(isMethod){
						dependencies[1].add(poped);
						dependencies[0].add(stack.peek());
					}
				}
			}
			last = information;
		}		
		return dependencies;
	}

	private void generateAssociations(List<Information> informations) {
		List<String>[] a = getAssociationPairs(informations);
		generateUnidirectionalAssociations(a);
		generateBiDirectionalAssociations(a);
	}

	private void generateUnidirectionalAssociations(List<String>[] associations) {
		associations = removeBiDirectionals(associations);
		associations = removeDuplicatedUniDirectionals(associations);
		for (int i = 0; i < associations[0].size(); i++) {
			String caller = associations[0].get(i);
			String target = associations[1].get(i);
			adapter.createUnidirectionalAssociation(caller, target);
		}
		attributeUnidirectionals = associations;
	}

	private void generateBiDirectionalAssociations(List<String>[] associations) {
		List<String>[] bidirectionals = removeUniDirectionals(associations);
		bidirectionals = removeDuplicatedBiDirectionals(bidirectionals);
		for (int i = 0; i < bidirectionals[0].size(); i++) {
			String caller = bidirectionals[0].get(i);
			String target = bidirectionals[1].get(i);
			adapter.createBidirectionalAssociation(caller, target);
		}
		attributeBidirectionals = bidirectionals;
	}
	
	private List<String>[] removeUniDirectionals(List<String>[] associations) {
		@SuppressWarnings("unchecked")
		List<String>[] bidirectionals = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		
		for (int i = 0; i < associations[0].size(); i++) {
			String caller1 = associations[0].get(i);
			String target1 = associations[1].get(i);
			for(int j = i+1; j < associations[0].size();j++){
				String caller2 = associations[0].get(j);
				String target2 = associations[1].get(j);
				if(caller1.equals(target2) && target1.equals(caller2)){
					bidirectionals[0].add(caller1);
					bidirectionals[1].add(target1);
				}
			}
		}
		return bidirectionals;
	}

	private List<String>[] removeBiDirectionals(List<String>[] associations) {
		List<String>[] unidirectionals = cloneAssociations(associations);
		
		for (int i = 0; i < unidirectionals[0].size(); i++) {
			String caller1 = unidirectionals[0].get(i);
			String target1 = unidirectionals[1].get(i);
			boolean isBidirectional = false;
			for(int j = i+1; j < unidirectionals[0].size();j++){
				String caller2 = unidirectionals[0].get(j);
				String target2 = unidirectionals[1].get(j);
				if(caller1.equals(target2) && target1.equals(caller2)){
					unidirectionals[0].remove(j);
					unidirectionals[1].remove(j);
					isBidirectional = true;
				}
			}
			if(isBidirectional){
				unidirectionals[0].remove(caller1);
				unidirectionals[1].remove(target1);
			}
		}
		return unidirectionals;
	}

	private List<String>[] cloneAssociations(List<String>[] associations) {
		@SuppressWarnings("unchecked")
		List<String>[] cloned = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		for(String caller: associations[0])
			cloned[0].add(caller);
		for(String target: associations[1])
			cloned[1].add(target);
		return cloned;
	}

	private List<String>[] getAssociationPairs(List<Information> informations){	
		@SuppressWarnings("unchecked")
		List<String>[] associations = (List<String>[])new List[]{
			new LinkedList<String>(), new LinkedList<String>()};
		
		Stack<String> stack = new Stack<String>();
		boolean isConstructor = false;
		
		for (Information information : informations) {
			if(information instanceof IClass)
				stack.push(information.getValue());
			else if(information instanceof IMethod)
				isConstructor = (information.getValue() == constructorCall)? true:false;
			else if(information instanceof IReturn){
				String poped = stack.pop();
				if(!stack.empty()){					 
					if(isConstructor){
						associations[1].add(poped);
						associations[0].add(stack.peek());
					}
				}
			}				
		}		
		return associations;
	}

	private void generateImplementations(List<Information> informations) {
		Information last = null;
		for (Information information : informations) {
			if(information instanceof IClass && last instanceof IInterface)
				adapter.createImplementation(last.getValue(),information.getValue());
			last = information;
		}
		
	}

	private void generateTypes(List<Information> informations) {
		List<String> types = new LinkedList<String>();
		for (Information information : informations){
			if(information instanceof IParameters){
				String params = information.getValue();				
				List<String> parametersList = Arrays.asList(params.split(","));
				for (String parameter : parametersList){
					if(!parameter.isEmpty()){
						String type = (parameter.trim().split(" "))[0];
						if(!types.contains(type))
							types.add(type);
					}
				}
			}else if(information instanceof IReturn){
				String type = information.getValue();
				if(!types.contains(type) && !type.equals(void_))
					types.add(type);
			}
		}
		types = removeDuplicated(types);
		types.removeAll(attributeClasses);
		types.removeAll(attributeInterfaces);
		for (String type : types) {
			adapter.createType(type);
			attributeTypes.add(type);
		}
	}

	private void generateMethods(List<Information> informations) {
		informations = moveNestedMethodsToFirstLayer(informations);
		List<String> methodNames       = new LinkedList<String>();
		List<String> containingClasses = new LinkedList<String>();
		List<String> signatures        = new LinkedList<String>();
		List<String> returnTypes       = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof IClass)
				containingClasses.add(information.getValue());
			else if(information instanceof IMethod)
				methodNames.add(information.getValue());
			else if(information instanceof IParameters)
				signatures.add(information.getValue());
			else if(information instanceof IReturn)
				returnTypes.add(information.getValue());
		}
		Iterator<String> cIterator = containingClasses.iterator();
		Iterator<String> mIterator = methodNames.iterator();
		Iterator<String> sIterator = signatures.iterator();
		Iterator<String> rIterator = returnTypes.iterator();
		while (cIterator.hasNext() && mIterator.hasNext() && sIterator.hasNext() && rIterator.hasNext()) {
			String returnType = rIterator.next();
			if(returnType.equals(void_))
				adapter.createMethod(cIterator.next(),mIterator.next(),sIterator.next());
			else
				adapter.createMethodWithReturn(cIterator.next(),mIterator.next(),sIterator.next(), returnType);
		}
	}

	private List<Information> moveNestedMethodsToFirstLayer(
			List<Information> informations) {
		TreeNode tree = new TreeNode();
		Information last = null;
		for(Information information :informations){
			if(information instanceof IClass){
				if(last == null || last instanceof IInterface){
					tree.addInfo(information);
				}else
					tree.addChild(information);
			}
			else if(information instanceof IInterface)
				tree.addChild(information);
			else if(information instanceof IReturn){
				tree.addInfo(information);
				tree = (tree.getParent() == null)? tree:tree.getParent();
			}
			else
				tree.addInfo(information);
			last = information;
		}
		return tree.getAllInfoInTree();
	}

	private void generateInterfaces(List<Information> informations) {
		List<String> interfaceNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof IInterface)
				interfaceNames.add(information.getValue());
		}
		interfaceNames = removeDuplicated(interfaceNames);
		for (String interfaceName : interfaceNames){
			adapter.createInterface(interfaceName);
			attributeInterfaces.add(interfaceName);
		}
	}

	private void generateClasses(List<Information> informations) {
		List<String> classNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof IClass
				|| information instanceof IHandler)
				classNames.add(information.getValue());
		}
		classNames = removeDuplicated(classNames);
		for (String className : classNames){
			adapter.createConcreteClass(className);
			attributeClasses.add(className);
		}
	}
	
	private List<String> removeDuplicated(List<String> classesNames) {
		String previous = null;
		classesNames.sort(null);
		for (Iterator<String> iterator = classesNames.iterator(); iterator.hasNext();) {
			String current = iterator.next();
			if( current.equals(previous))
				iterator.remove();
			previous = current;
		}
		return classesNames;
	}
	private List<String>[] removeDuplicatedBiDirectionals(List<String>[] pairs) {		
		pairs = removeDuplicatedUniDirectionals(pairs);
		for (int i = 0; i < pairs[0].size(); i++) {
			String caller1 = pairs[0].get(i);
			String target1 = pairs[1].get(i);
			for(int j = i+1; j < pairs[0].size();j++){
				String caller2 = pairs[0].get(j);
				String target2 = pairs[1].get(j);
				if(caller1.equals(target2) && target1.equals(caller2)){
					pairs[0].remove(j);
					pairs[1].remove(j);
				}
			}
		}
		return pairs;
	}
	private List<String>[] removeDuplicatedUniDirectionals(List<String>[] pairs) {		
		for (int i = 0; i < pairs[0].size(); i++) {
			String caller1 = pairs[0].get(i);
			String target1 = pairs[1].get(i);
			for(int j = i+1; j < pairs[0].size();j++){
				String caller2 = pairs[0].get(j);
				String target2 = pairs[1].get(j);
				if(caller1.equals(caller2) && target1.equals(target2)){
					pairs[0].remove(j);
					pairs[1].remove(j);
				}
			}
		}
		return pairs;
	}

	@Override
	public ClassDiagramFrameworkAdapter getUtil() {
		return adapter;
	}
}
