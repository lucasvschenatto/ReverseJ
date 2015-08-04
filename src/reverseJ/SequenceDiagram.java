package reverseJ;

import java.util.List;

import org.eclipse.uml2.uml.Package;

public class SequenceDiagram implements DiagramStrategy {
	private AdapterSequenceToUml2 adapter;

	public SequenceDiagram(AdapterSequenceToUml2 adapter) {
		this.adapter = adapter;
	}

	@Override
	public Package generate(List<Information> informations) {
		if(informations != null && !informations.isEmpty())
			adapter.createLifeline(informations.get(0).getValue());
		return adapter.getPackage();
	}

	@Override
	public AdapterToUml2 getUtil() {
		return adapter;
	}

}
