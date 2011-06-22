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
package net.objectzoo.appkata.csv.flow;

import java.io.IOException;
import java.util.List;

import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.EntryPoint;
import net.objectzoo.ebc.ResultBase;

public class ReadLines extends ResultBase<List<String>> implements EntryPoint,
	DependsOn<TextFileAdapterContract>
{
	private static final int DEFAULT_NUMBER_OF_DATA_LINES = 20;
	
	private TextFileAdapterContract textFileAdapter;
	
	@Override
	public void inject(TextFileAdapterContract dependency)
	{
		log.finer("inject");
		
		this.textFileAdapter = dependency;
	}
	
	@Override
	public void run(String... parameters)
	{
		log.finer("run");
		
		String filename = determineFilename(parameters);
		int numberOfLinesToRead = determineNumberOfLines(parameters) + 1;
		
		try
		{
			List<String> lines = textFileAdapter.readLines(filename, numberOfLinesToRead);
			
			sendResult(lines);
		}
		catch (IOException e)
		{
			throw new IllegalArgumentException("Problem while reading input file.", e);
		}
		
	}
	
	static int determineNumberOfLines(String... parameters) throws NumberFormatException
	{
		int numberOfLines = DEFAULT_NUMBER_OF_DATA_LINES;
		
		if (parameters.length > 1)
		{
			numberOfLines = Integer.parseInt(parameters[1]);
		}
		
		return numberOfLines;
	}
	
	static String determineFilename(String... parameters) throws IllegalArgumentException
	{
		if (parameters.length > 0)
		{
			return parameters[0];
		}
		else
		{
			throw new IllegalArgumentException(
				"The CSV file name must be given as first command line parameter.");
		}
	}
	
}
