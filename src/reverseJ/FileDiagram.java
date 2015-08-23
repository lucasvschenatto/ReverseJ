package reverseJ;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

public class FileDiagram {
	private Diagram diagram;
	private String path;
	public FileDiagram(Diagram diagram, String path) {
		this.diagram = diagram;
		this.path = path;
	}
	public Diagram getDiagram() {
		return diagram;
	}
	public String getPath(){
		return path;
	}
	public void save() {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI outputURI = URI.createFileURI(path);
		UMLResourcesUtil.init(resourceSet);

		Resource resource = resourceSet.createResource(outputURI);
		resource.getContents().add(diagram.getModel());
		try {
			resource.save(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
