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

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.displaypage.PageViewModel;
import net.objectzoo.ebc.TestAction;

public class DetermineColumnLengthsTest
{
	private TestAction<int[]> resultAction;
	
	private DetermineColumnLengths sut;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<int[]>();
		
		sut = new DetermineColumnLengths();
		sut.getResult().subscribe(resultAction);
	}
	
	@Test
	public void takesMaxLengthFromHeader()
	{
		sut.process(new PageViewModel(new String[] { "VeryLong", "EvenLonger" }, new String[][] { {
				"Short", "Values" } }, 0, 0));
		
		assertArrayEquals(new int[] { 8, 10 }, resultAction.getResult());
	}
	
	@Test
	public void takesMaxLengthFromData()
	{
		sut.process(new PageViewModel(new String[] { "Short", "Values" }, new String[][] {
				{ "VeryLong", "Values" }, { "Short", "EvenLonger" } }, 0, 0));
		
		assertArrayEquals(new int[] { 8, 10 }, resultAction.getResult());
		
	}
}
