package net.objectzoo.appkata.csv.dependencies;

public interface IndexContract
{
	void storeOffset(int pageNr, long offset);
	
	long retrieveOffset(int pageNr);
}
