package reverseJ;
import java.util.LinkedList;
import java.util.List;

public class Log implements RecorderStorage {
	private List <Information> list;
	static final String emptyLogInfo = "**Empty log**";
	public Log(){
		list = new LinkedList<Information>();
	}
	@Override
	public void addInformation(RecorderInfo name, String value) {
		Information info = new Information();
		info.setName(name);
		info.setValue(value);	
		list.add(info);
	}

	@Override
	public String describe (RecorderInfo informationName) throws NotFoundInformationException{
		for (Information information : list)
			if (information.getName() == informationName)
				return information.getValue();
		throw new NotFoundInformationException();
	}
	class Information {
		public RecorderInfo getName() {
			return name;
		}
		public void setName(RecorderInfo name) {
			this.name = name;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		private RecorderInfo name;
		private String value;
	}
	@Override
	public String[] describeAll(){
		String [] allInformations = new String [list.size()];
		Information[] toConvert = list.toArray(new Information[0]);
		for (int count = 0; count < toConvert.length; count++)
			allInformations [count] = toConvert[count].getName() + " : "
									+ toConvert[count].getValue();
		if (allInformations.length == 0)
			allInformations = new String[] {emptyLogInfo};
		return allInformations;
		
	}
	class NotFoundInformationException extends Exception{
		private static final long serialVersionUID = 1L;}
	class EmptyLogException extends Exception{
		private static final long serialVersionUID = 1L;}
	@Override
	public boolean hasInformation(RecorderInfo name) {
		try {
			describe(name);
			return true;
		} catch (NotFoundInformationException e) {
			return false;
		}
	}
	@Override
	public int size() {
		return list.size();
	}
}