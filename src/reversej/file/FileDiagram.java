package reversej.file;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

import reversej.diagram.Diagram;

public class FileDiagram {
	private final String PROJECT_PATH = System.getProperty("user.dir");
	private final String FOLDER = "\\files\\";
	private Diagram diagram;
	private String fileName;
	public FileDiagram(Diagram diagram, String fileName) {
		this.diagram = diagram;
		this.fileName = fileName;
	}
	public String getSavingFolderPath() {
		return PROJECT_PATH+FOLDER;
	}
	public Diagram getDiagram() {
		return diagram;
	}
	public String getFileName(){
		return fileName;
	}
	public void save() {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI outputURI = URI.createFileURI(PROJECT_PATH +FOLDER+ fileName)
				.appendFileExtension(UMLResource.FILE_EXTENSION);
		UMLResourcesUtil.init(resourceSet);

		Resource resource = resourceSet.createResource(outputURI);
		resource.getContents().add(diagram.getModel());
		try {
			resource.save(null);// no save options needed
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
