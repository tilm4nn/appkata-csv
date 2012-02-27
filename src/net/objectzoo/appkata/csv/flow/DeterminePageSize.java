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

import net.objectzoo.ebc.Configurable;
import net.objectzoo.ebc.impl.ResultBase;

public class DeterminePageSize extends ResultBase<Integer> implements Configurable
{
	private static final int DEFAULT_NUMBER_OF_DATA_LINES = 20;
	
	public DeterminePageSize()
	{
		super(true);
	}
	
	@Override
	public void configure(String... configuration)
	{
		int pageSize = determineNumberOfLines(configuration);
		sendResult(pageSize);
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
}
