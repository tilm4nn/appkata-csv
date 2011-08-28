package net.objectzoo.appkata.csv.dependencies;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Index implements IndexContract
{
	final Map<Integer, Long> offsets = new ConcurrentHashMap<Integer, Long>();
	
	@Override
	public void storeOffset(int pageNr, long offset)
	{
		offsets.put(pageNr, offset);
	}
	
	@Override
	public long retrieveOffset(int pageNr)
	{
		Long result = offsets.get(pageNr);
		
		if (result == null)
		{
			throw new IllegalArgumentException("The offset of page " + pageNr
				+ " is not stored in the index");
		}
		
		return result;
	}
	
}
