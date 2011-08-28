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

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.Utils;
import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.ebc.TestAction;

public class SplitLinesTest
{
	private TestAction<List<CsvLine>> resultAction;
	
	private SplitLines sut;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<List<CsvLine>>();
		
		sut = new SplitLines();
		sut.getResult().subscribe(resultAction);
	}
	
	@Test
	public void splitsTwoValues()
	{
		sut.process(list("Value1;Value2"));
		
		assertEquals(list(new CsvLine("Value1", "Value2")), resultAction.getLastResult());
	}
	
	@Test
	public void splitsOneValue()
	{
		sut.process(list("Value1"));
		
		assertEquals(list(new CsvLine("Value1")), resultAction.getLastResult());
	}
	
	@Test
	public void splitsNoValue()
	{
		sut.process(list(""));
		
		assertEquals(list(new CsvLine("")), resultAction.getLastResult());
	}
	
	@Test
	public void splitsTwoLines()
	{
		sut.process(list("Value1;Value2", "Value3;Value4"));
		
		assertEquals(list(new CsvLine("Value1", "Value2"), new CsvLine("Value3", "Value4")),
			resultAction.getLastResult());
	}
	
	@Test
	public void splitsNoLine()
	{
		sut.process(Utils.<String> list());
		
		assertEquals(Utils.<CsvLine> list(), resultAction.getLastResult());
	}
}
