package net.objectzoo.appkata.csv.dependencies;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TextFileScanner implements TextFileScannerContract
{
	RandomAccessFile file;
	
	@Override
	public void openFile(String filename) throws IOException
	{
		closeFile();
		file = new RandomAccessFile(filename, "r");
	}
	
	@Override
	public Long getNextPosition(int numberOfLinesToSkip) throws IOException
	{
		if (file == null)
		{
			throw new IllegalStateException("File has not been opened yet.");
		}
		
		for (int i = 0; i < numberOfLinesToSkip; i++)
		{
			String line = file.readLine();
			if (line == null)
			{
				return null;
			}
		}
		
		long position = file.getFilePointer();
		
		if (position == file.length())
		{
			return null;
		}
		else
		{
			return position;
		}
	}
	
	@Override
	public void closeFile() throws IOException
	{
		if (file != null)
		{
			file.close();
		}
	}
	
}
