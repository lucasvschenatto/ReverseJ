package reverseJ;

import reverseJ.Log.NotFoundInformationException;

public interface RecorderStorage {

	public abstract void addInformation(String name, String value);

	public abstract String describe(String informationName)
			throws NotFoundInformationException;
	
	public abstract boolean hasInformation(String name);

	public abstract String[] describeAll();

	public abstract int size();

}