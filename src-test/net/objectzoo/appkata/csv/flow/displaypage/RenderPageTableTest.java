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

import static net.objectzoo.appkata.csv.Utils.list;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.Pair;

public class RenderPageTableTest
{
	private Mockery mockery;
	
	private Action<String> resultActionMock;
	
	private RenderPageTable sut;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup()
	{
		mockery = new Mockery();
		resultActionMock = mockery.mock(Action.class);
		
		sut = new RenderPageTable();
		sut.getResult().subscribe(resultActionMock);
	}
	
	@Test
	public void rendersTableWithTwoColumnsAndTwoDataRows()
	{
		final String expected = "666666    |88888888|\n" + "----------+--------+\n"
			+ "88888888  |4444    |\n" + "1010101010|22      |\n";
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(expected);
			}
		});
		
		sut.process(new Pair<Page, List<Integer>>(new Page(new CsvLine("666666", "88888888"), list(
			new CsvLine("88888888", "4444"), new CsvLine("1010101010", "22"))), list(10, 8)));
		
		mockery.assertIsSatisfied();
	}
}
