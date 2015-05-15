package reverseJ;

import java.util.List;

public interface InformationProvider {
	public abstract List<Information> getAll();
	public abstract Information getNext();
}
