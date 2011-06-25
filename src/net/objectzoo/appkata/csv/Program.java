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
package net.objectzoo.appkata.csv;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapter;
import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapter;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.appkata.csv.flow.DisplayCommands;
import net.objectzoo.appkata.csv.flow.DivideIntoPageSize;
import net.objectzoo.appkata.csv.flow.InputPageNumber;
import net.objectzoo.appkata.csv.flow.MainBoard;
import net.objectzoo.appkata.csv.flow.PutInRecords;
import net.objectzoo.appkata.csv.flow.ReadLines;
import net.objectzoo.appkata.csv.flow.RepeatedWaitForCommand;
import net.objectzoo.appkata.csv.flow.SelectPage;
import net.objectzoo.appkata.csv.flow.SeparateHeaderAndData;
import net.objectzoo.appkata.csv.flow.SplitLines;
import net.objectzoo.appkata.csv.flow.displaypage.DetermineColumnLengths;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageBoard;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageViewModel;
import net.objectzoo.appkata.csv.flow.displaypage.MapToPageViewModel;
import net.objectzoo.appkata.csv.flow.displaypage.RenderPageViewModel;

public class Program
{
	public static void main(String... args)
	{
		TextFileAdapterContract textFileAdapter = new TextFileAdapter();
		ConsoleAdapterContract consoleAdapter = new ConsoleAdapter();
		ReadLines readLines = new ReadLines();
		DivideIntoPageSize divideIntoPageSize = new DivideIntoPageSize();
		DisplayPageViewModel displayPageTable = new DisplayPageViewModel();
		DisplayCommands displayCommands = new DisplayCommands();
		RepeatedWaitForCommand repeatedWaitForCommand = new RepeatedWaitForCommand();
		InputPageNumber inputPageNumber = new InputPageNumber();
		MainBoard mainBoard = new MainBoard(repeatedWaitForCommand, readLines, new SplitLines(),
			new SeparateHeaderAndData(), new PutInRecords(), divideIntoPageSize, new SelectPage(),
			inputPageNumber, new DisplayPageBoard(new MapToPageViewModel(),
				new DetermineColumnLengths(), new RenderPageViewModel(), displayPageTable),
			displayCommands);
		
		readLines.inject(textFileAdapter);
		displayPageTable.inject(consoleAdapter);
		displayCommands.inject(consoleAdapter);
		repeatedWaitForCommand.inject(consoleAdapter);
		inputPageNumber.inject(consoleAdapter);
		
		readLines.configure(args);
		divideIntoPageSize.configure(args);
		
		mainBoard.getStart().invoke();
	}
}
