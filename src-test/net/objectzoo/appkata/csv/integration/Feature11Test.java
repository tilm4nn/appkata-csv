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
package net.objectzoo.appkata.csv.integration;

import static net.objectzoo.appkata.csv.Utils.list;

import java.io.IOException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.appkata.csv.flow.DisplayCommands;
import net.objectzoo.appkata.csv.flow.DivideIntoPageSize;
import net.objectzoo.appkata.csv.flow.MainBoard;
import net.objectzoo.appkata.csv.flow.ReadLines;
import net.objectzoo.appkata.csv.flow.RepeatedWaitForCommand;
import net.objectzoo.appkata.csv.flow.SelectPage;
import net.objectzoo.appkata.csv.flow.SeparateHeaderAndData;
import net.objectzoo.appkata.csv.flow.SplitLines;
import net.objectzoo.appkata.csv.flow.displaypage.DetermineColumnLengths;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageBoard;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageTable;
import net.objectzoo.appkata.csv.flow.displaypage.RenderPageTable;

public class Feature11Test
{
	
	@Test
	public void rendersTableWithTwoColumnsAndTwoDataRows() throws IOException
	{
		Mockery mockery = new Mockery();
		final TextFileAdapterContract textFileAdapterMock = mockery.mock(TextFileAdapterContract.class);
		final ConsoleAdapterContract consoleAdapterMock = mockery.mock(ConsoleAdapterContract.class);
		
		ReadLines readLines = new ReadLines();
		DivideIntoPageSize divideIntoPageSize = new DivideIntoPageSize();
		DisplayPageTable displayPageTable = new DisplayPageTable();
		DisplayCommands displayCommands = new DisplayCommands();
		RepeatedWaitForCommand repeatedWaitForCommand = new RepeatedWaitForCommand();
		new MainBoard(repeatedWaitForCommand, readLines, new SplitLines(),
			new SeparateHeaderAndData(), divideIntoPageSize, new SelectPage(),
			new DisplayPageBoard(new DetermineColumnLengths(), new RenderPageTable(),
				displayPageTable), displayCommands);
		
		readLines.inject(textFileAdapterMock);
		displayPageTable.inject(consoleAdapterMock);
		displayCommands.inject(consoleAdapterMock);
		repeatedWaitForCommand.inject(consoleAdapterMock);
		
		readLines.configure("filename");
		divideIntoPageSize.configure("", "5");
		
		final String expected = "666666    |88888888|\n" + "----------+--------+\n"
			+ "88888888  |4444    |\n" + "1010101010|22      |\n";
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(textFileAdapterMock).readLines("filename", Integer.MAX_VALUE);
				will(returnValue(list("666666;88888888", "88888888;4444", "1010101010;22")));
				
				oneOf(consoleAdapterMock).output(expected);
				
				oneOf(consoleAdapterMock).output(with(any(String.class)));
				
				oneOf(consoleAdapterMock).input();
				will(returnValue('x'));
			}
		});
		
		repeatedWaitForCommand.run();
		
		mockery.assertIsSatisfied();
	}
}
