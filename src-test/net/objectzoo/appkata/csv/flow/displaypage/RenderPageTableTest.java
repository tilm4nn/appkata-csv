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

import junit.framework.Assert;
import net.objectzoo.appkata.csv.data.displaypage.PageViewModel;
import net.objectzoo.ebc.test.MockAction;
import net.objectzoo.ebc.util.Pair;

import org.junit.Before;
import org.junit.Test;

public class RenderPageTableTest
{
	private MockAction<String> resultAction;
	
	private RenderPageViewModel sut;
	
	@Before
	public void setup()
	{
		resultAction = new MockAction<String>();
		
		sut = new RenderPageViewModel();
		sut.resultEvent().subscribe(resultAction);
	}
	
	@Test
	public void rendersTableWithTwoColumnsAndTwoDataRows()
	{
		final String expected = "666666    |88888888|\n" + "----------+--------+\n" + "88888888  |4444    |\n"
			+ "1010101010|22      |\nPage 0 of 0\n";
		
		sut.process(new Pair<PageViewModel, int[]>(new PageViewModel(new String[] { "666666", "88888888" },
			new String[][] { { "88888888", "4444" }, { "1010101010", "22" } }, 0, 0), new int[] { 10, 8 }));
		
		Assert.assertEquals(expected, resultAction.getLastResult());
	}
	
	@Test
	public void rendersPosition()
	{
		final String expected = "\n\nPage 12 of 34\n";
		
		sut.process(new Pair<PageViewModel, int[]>(new PageViewModel(new String[] {}, new String[][] {}, 12, 34),
			new int[] {}));
		
		Assert.assertEquals(expected, resultAction.getLastResult());
	}
}
