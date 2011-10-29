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
package net.objectzoo.appkata.csv.flow.displaypage;

import net.objectzoo.appkata.csv.data.displaypage.PageViewModel;
import net.objectzoo.ebc.impl.ProcessAndResultBase;

public class DetermineColumnLengths extends ProcessAndResultBase<PageViewModel, int[]>
{
	
	@Override
	protected void process(PageViewModel pageVm)
	{
		int[] maxColumnLengths = getColumnLengths(pageVm.getHeader());
		
		for (String[] row : pageVm.getRows())
		{
			updateMaxVaules(maxColumnLengths, getColumnLengths(row));
		}
		
		sendResult(maxColumnLengths);
	}
	
	static void updateMaxVaules(int[] maxValues, int[] currentValues)
	{
		for (int i = 0; i < currentValues.length; i++)
		{
			if (currentValues[i] > maxValues[i])
			{
				maxValues[i] = currentValues[i];
			}
		}
	}
	
	static int[] getColumnLengths(String[] values)
	{
		int[] lengths = new int[values.length];
		
		for (int i = 0; i < values.length; i++)
		{
			lengths[i] = values[i].length();
		}
		
		return lengths;
	}
	
}
