package reverseJ;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.emf.common.util.URI;

public class MakerAndSaver extends DiagramMaker {
	public MakerAndSaver(InformationProvider infoProvider,
			List<DiagramStrategy> diagrams) {
		super(infoProvider, diagrams);
	}

	@Override
	public void make() {
		List<Package> packages = new LinkedList<Package>();
		for (DiagramStrategy diagram : diagrams)
			packages.add(diagram.generate(infoProvider.getAll()));

		ResourceSet resourceSet = new ResourceSetImpl();
		URI outputURI = URI.createFileURI("../ReverseJ/files/diagramaDeClasse")
				.appendFileExtension(UMLResource.FILE_EXTENSION);
		UMLResourcesUtil.init(resourceSet);

		// Create the output resource and add our model package to it.

		Resource resource = resourceSet.createResource(outputURI);
		for (Package package_ : packages) {
			resource.getContents().add(package_);
		}

		// And save
		try {
			resource.save(null); // no save options needed
			System.out.println("successfully saved.");
		} catch (IOException ioe) {
			System.out.println(ioe.toString());
		}

	}

}
