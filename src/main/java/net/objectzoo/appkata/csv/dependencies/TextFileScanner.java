/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:info@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
