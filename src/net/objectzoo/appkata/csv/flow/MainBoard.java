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

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.PageData;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageBoard;
import net.objectzoo.ebc.JoinObjectAndCollection;

public class MainBoard
{
	public MainBoard(RepeatedWaitForCommand repeatedWaitForCommand, ReadLines readLines,
					 SplitLines splitLines, SeparateHeaderAndData separateHeaderAndData,
					 DivideIntoPageSize divideIntoPageSize, SelectPage selectPage,
					 DisplayPageBoard displayPage, DisplayCommands displayCommands)
	{
		JoinObjectAndCollection<CsvLine, PageData, Page> join = new JoinObjectAndCollection<CsvLine, PageData, Page>()
		{
		};
		
		repeatedWaitForCommand.getSignal().subscribe(readLines.getStart());
		readLines.getResult().subscribe(splitLines.getProcess());
		splitLines.getResult().subscribe(separateHeaderAndData.getProcess());
		separateHeaderAndData.getNewHeader().subscribe(join.getInput1());
		separateHeaderAndData.getNewData().subscribe(divideIntoPageSize.getProcess());
		divideIntoPageSize.getResult().subscribe(join.getInput2());
		join.getResult().subscribe(selectPage.getProcess());
		selectPage.getResult().subscribe(displayPage.getProcess());
		displayPage.getSingal().subscribe(displayCommands.getStart());
	}
}
