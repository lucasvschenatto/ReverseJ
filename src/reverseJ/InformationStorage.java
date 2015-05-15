package reverseJ;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class InformationStorage implements RecorderStorage, InformationProvider {
	private List <Information> list;
	private Iterator <Information> iterator;
	public InformationStorage(){
		list = new LinkedList<Information>();
	}
	@Override
	public void addInformation(Information info) {
		list.add(info);
	}
	@Override
	public List<Information> getAll() {
		return list;
	}
	@Override
	public List<String> describeAll(){
		List<String> allInformations = new LinkedList<String>();
		for (Information toConvert : list)
			allInformations.add(toConvert.describe());
		return allInformations;		
	}
	@Override
	public int size() {
		return list.size();
	}
	@Override
	public Information getNext() {
		if (iterator == null)
			iterator = list.iterator();
		return iterator.hasNext()? iterator.next() : null;
	}
	@Override
	public boolean isEmpty() {
		if (list.isEmpty())
			return true;
		return false;
	}
	@Override
	public Information getInfo(String value) {
		for (Information information : list)
			if(information.getValue() == value)
				return information;
		return null;
	}
	
	
	
	
}