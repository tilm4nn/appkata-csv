package net.objectzoo.appkata.csv.dependencies;

import java.io.IOException;

public interface TextFileScannerContract
{
	public void openFile(String filename) throws IOException;
	
	public Long getNextPosition(int numberOfLinesToSkip) throws IOException;
	
	public void closeFile() throws IOException;
}
