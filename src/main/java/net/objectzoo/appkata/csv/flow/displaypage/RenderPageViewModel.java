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
import net.objectzoo.ebc.util.Pair;

class RenderPageViewModel extends ProcessAndResultBase<Pair<PageViewModel, int[]>, String>
{
	@Override
	protected void process(Pair<PageViewModel, int[]> pageViewModelAndColumnLengths)
	{
		PageViewModel pageVm = pageViewModelAndColumnLengths.getItem1();
		int[] columnLengths = pageViewModelAndColumnLengths.getItem2();
		
		StringBuilder renderedPage = new StringBuilder(renderRow(pageVm.getHeader(), columnLengths));
		
		renderedPage.append(renderHorizontalLine(columnLengths));
		
		for (String[] row : pageVm.getRows())
		{
			renderedPage.append(renderRow(row, columnLengths));
		}
		
		renderedPage.append(renderPosition(pageVm.getCurrentPosition(), pageVm.getMaxPosition(),
			pageVm.getMaxCertain()));
		
		sendResult(renderedPage.toString());
	}
	
	static String renderPosition(int currentPosition, int maxPosition, boolean maxCertain)
	{
		return "Page " + currentPosition + " of " + maxPosition + (maxCertain ? "\n" : "?\n");
	}
	
	static String renderHorizontalLine(int[] columnLengths)
	{
		StringBuilder lineFormat = new StringBuilder();
		for (int i = 0; i < columnLengths.length; i++)
		{
			lineFormat.append("%1$").append(columnLengths[i]).append("s+");
		}
		lineFormat.append("\n");
		
		return String.format(lineFormat.toString(), " ").replace(' ', '-');
	}
	
	static String renderRow(String[] row, int[] columnLengths)
	{
		StringBuilder lineFormat = new StringBuilder();
		for (int i = 0; i < columnLengths.length; i++)
		{
			lineFormat.append("%").append((i + 1)).append("$-").append(columnLengths[i]).append(
				"s|");
		}
		lineFormat.append("\n");
		
		return String.format(lineFormat.toString(), (Object[]) row);
	}
}
