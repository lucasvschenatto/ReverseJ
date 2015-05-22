package reverseJ;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ClassDiagram implements DiagramStrategy {
	private ClassDiagramUtilities diagramUtilities;
	
	public ClassDiagram(ClassDiagramUtilities utilities) {
		diagramUtilities = utilities;
	}

	public ClassDiagram() {
		diagramUtilities = ClassDiagramUtilities.make();
	}

	@Override
	public DiagramObject generate(List<Information> informations) {
		List<String> classNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof ICaller || information instanceof ITarget
				|| information instanceof IHandler)
				classNames.add(information.getValue());
		}
		classNames = removeDuplicated(classNames);
		for (String className : classNames){
			diagramUtilities.createConcreteClass(className);
		}
		
		List<String> interfaceNames = new LinkedList<String>();
		for (Information information : informations) {
			if(information instanceof IInterface)
				interfaceNames.add(information.getValue());
		}
		interfaceNames = removeDuplicated(interfaceNames);
		for (String interfaceName : interfaceNames){
			diagramUtilities.createInterface(interfaceName);
		}
		return null;
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

	@Override
	public ClassDiagramUtilities getUtil() {
		return diagramUtilities;
	}

}
