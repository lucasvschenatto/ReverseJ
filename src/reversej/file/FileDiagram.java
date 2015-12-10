package reversej.file;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;

import reversej.diagram.Diagram;

public class FileDiagram{
	private Diagram diagram;
	private File fileToSave;
	public FileDiagram(Diagram diagram, File fileToSave) {
		this.diagram = diagram;
		this.fileToSave = fileToSave;
	}
	public Diagram getDiagram() {
		return diagram;
	}
	public File getFileName(){
		return fileToSave;
	}
	public void save() {
		ResourceSet resourceSet = new ResourceSetImpl();
		URI outputURI = URI.createFileURI(fileToSave.getAbsolutePath()).appendFileExtension(UMLResource.FILE_EXTENSION);
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
