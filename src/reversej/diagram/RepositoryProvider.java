package reversej.diagram;

import java.util.List;

import reversej.information.Information;
import reversej.information.InformationFactory;

public interface RepositoryProvider {
	public abstract List<Information> getAll(InformationFactory factory);
	public abstract Information getNext(InformationFactory factory);
}
