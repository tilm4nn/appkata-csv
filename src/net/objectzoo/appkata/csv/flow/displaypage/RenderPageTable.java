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

import java.util.List;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.ProcessAndResultBase;

public class RenderPageTable extends ProcessAndResultBase<Pair<Page, List<Integer>>, String>
{
	
	@Override
	protected void process(Pair<Page, List<Integer>> pageAndColumnLengths)
	{
		Page page = pageAndColumnLengths.getItem1();
		List<Integer> columnLengths = pageAndColumnLengths.getItem2();
		
		String renderedPage = renderCsvLine(page.getHeader(), columnLengths);
		
		renderedPage += renderHorizontalLine(columnLengths);
		
		for (CsvLine csvLine : page.getData())
		{
			renderedPage += renderCsvLine(csvLine, columnLengths);
		}
		
		sendResult(renderedPage);
	}
	
	static String renderHorizontalLine(List<Integer> columnLengths)
	{
		String lineFormat = "";
		for (int i = 0; i < columnLengths.size(); i++)
		{
			lineFormat += "%1$" + columnLengths.get(i) + "s+";
		}
		lineFormat += "\n";
		
		return String.format(lineFormat, " ").replace(' ', '-');
	}
	
	static String renderCsvLine(CsvLine csvLine, List<Integer> columnLengths)
	{
		String lineFormat = "";
		for (int i = 0; i < columnLengths.size(); i++)
		{
			lineFormat += "%" + (i + 1) + "$-" + columnLengths.get(i) + "s|";
		}
		lineFormat += "\n";
		
		return String.format(lineFormat, csvLine.getValues().toArray());
	}
	
}
