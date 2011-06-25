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
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.ProcessAndResultBase;

public class RenderPageViewModel extends ProcessAndResultBase<Pair<PageViewModel, int[]>, String>
{
	
	@Override
	protected void process(Pair<PageViewModel, int[]> pageViewModelAndColumnLengths)
	{
		PageViewModel pageVm = pageViewModelAndColumnLengths.getItem1();
		int[] columnLengths = pageViewModelAndColumnLengths.getItem2();
		
		String renderedPage = renderRow(pageVm.getHeader(), columnLengths);
		
		renderedPage += renderHorizontalLine(columnLengths);
		
		for (String[] row : pageVm.getRows())
		{
			renderedPage += renderRow(row, columnLengths);
		}
		
		renderedPage += renderPosition(pageVm.getCurrentPosition(), pageVm.getMaxPosition());
		
		sendResult(renderedPage);
	}
	
	static String renderPosition(int currentPosition, int maxPosition)
	{
		return String.format("Page " + currentPosition + " of " + maxPosition + "\n");
	}
	
	static String renderHorizontalLine(int[] columnLengths)
	{
		String lineFormat = "";
		for (int i = 0; i < columnLengths.length; i++)
		{
			lineFormat += "%1$" + columnLengths[i] + "s+";
		}
		lineFormat += "\n";
		
		return String.format(lineFormat, " ").replace(' ', '-');
	}
	
	static String renderRow(String[] row, int[] columnLengths)
	{
		String lineFormat = "";
		for (int i = 0; i < columnLengths.length; i++)
		{
			lineFormat += "%" + (i + 1) + "$-" + columnLengths[i] + "s|";
		}
		lineFormat += "\n";
		
		return String.format(lineFormat, (Object[]) row);
	}
	
}
