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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdapterToUml2 getUtil() {
		return adapter;
	}

}
