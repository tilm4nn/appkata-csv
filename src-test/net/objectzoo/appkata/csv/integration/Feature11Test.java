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
import net.objectzoo.appkata.csv.flow.DisplayExitCommandAndWait;
import net.objectzoo.appkata.csv.flow.MainBoard;
import net.objectzoo.appkata.csv.flow.ReadLines;
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
		DisplayPageTable displayPageTable = new DisplayPageTable();
		DisplayExitCommandAndWait displayExitCommandAndWait = new DisplayExitCommandAndWait();
		new MainBoard(readLines, new SplitLines(), new SeparateHeaderAndData(),
			new DisplayPageBoard(new DetermineColumnLengths(), new RenderPageTable(),
				displayPageTable), displayExitCommandAndWait);
		
		readLines.inject(textFileAdapterMock);
		displayPageTable.inject(consoleAdapterMock);
		displayExitCommandAndWait.inject(consoleAdapterMock);
		
		final String expected = "666666    |88888888|\n" + "----------+--------+\n"
			+ "88888888  |4444    |\n" + "1010101010|22      |\n";
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(textFileAdapterMock).readLines("filename", 6);
				will(returnValue(list("666666;88888888", "88888888;4444", "1010101010;22")));
				
				oneOf(consoleAdapterMock).output(expected);
				
				oneOf(consoleAdapterMock).output("\neX(it\n");
				
				oneOf(consoleAdapterMock).input();
				will(returnValue('x'));
			}
		});
		
		readLines.run("filename", "5");
		
		mockery.assertIsSatisfied();
	}
}
