package reverseJ;

import java.util.List;

import reverseJ.Log.Information;

public interface InfoProvider {
	public abstract List<Information> getAll();
	public abstract Information getNext();
}
