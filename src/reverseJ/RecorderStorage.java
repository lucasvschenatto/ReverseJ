package reverseJ;

import reverseJ.Log.NotFoundInformationException;

public interface RecorderStorage {

	public abstract void addInformation(RecorderInfo name, String value);

	public abstract String describe(RecorderInfo informationName)
			throws NotFoundInformationException;
	
	public abstract boolean hasInformation(RecorderInfo name);

	public abstract String[] describeAll();

	public abstract int size();

}