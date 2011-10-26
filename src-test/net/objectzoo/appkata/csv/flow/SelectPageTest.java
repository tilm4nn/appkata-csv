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

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.data.Progress;
import net.objectzoo.ebc.TestAction;

public class SelectPageTest
{
	private TestAction<Position> resultAction;
	
	private TestAction<Integer> selectedPageNumberAction;
	
	private SelectPage sut;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<Position>();
		selectedPageNumberAction = new TestAction<Integer>();
		
		sut = new SelectPage();
		sut.getResult().subscribe(resultAction);
		sut.getSelectedPageNumber().subscribe(selectedPageNumberAction);
		
		sut.lastPage = 3;
		sut.currentPage = 2;
	}
	
	@Test
	public void processSetsLastPage()
	{
		sut.process(new Progress(5, false));
		
		assertEquals(5, sut.lastPage);
	}
	
	@Test
	public void processSeteLastPageKnown()
	{
		sut.process(new Progress(5, true));
		
		assertEquals(true, sut.lastPageKnown);
	}
	
	@Test
	public void processSendsCurrentPosition()
	{
		sut.currentPage = 0;
		
		sut.process(new Progress(5, false));
		
		assertEquals(1, resultAction.getLastResult().getCurrentPosition());
	}
	
	@Test
	public void processSendsMaxPosition()
	{
		sut.currentPage = 0;
		
		sut.process(new Progress(5, false));
		
		assertEquals(5, resultAction.getLastResult().getMaxPosition());
	}
	
	@Test
	public void processSendsMaxCertain()
	{
		sut.currentPage = 0;
		
		sut.process(new Progress(5, true));
		
		assertEquals(true, resultAction.getLastResult().isMaxCertain());
	}
	
	@Test
	public void processSendsSelectedPageNumber()
	{
		sut.currentPage = 0;
		
		sut.process(new Progress(5, true));
		
		assertEquals((Integer) 1, selectedPageNumberAction.getLastResult());
	}
	
	@Test
	public void nextSendsNextPage()
	{
		sut.getNextPage().invoke();
		
		assertEquals((Integer) 3, selectedPageNumberAction.getLastResult());
	}
	
	@Test
	public void nextSendsNextPosition()
	{
		sut.getNextPage().invoke();
		
		assertEquals(new Position(3, 3, false), resultAction.getLastResult());
	}
	
	@Test
	public void nextStopsOnLastPage()
	{
		sut.currentPage = 3;
		
		sut.getNextPage().invoke();
		
		assertEquals(new Position(3, 3, false), resultAction.getLastResult());
	}
	
	@Test
	public void previousSendsPreviousPage()
	{
		sut.getPreviousPage().invoke();
		
		assertEquals((Integer) 1, selectedPageNumberAction.getLastResult());
	}
	
	@Test
	public void previousSendsPreviousPosition()
	{
		sut.getPreviousPage().invoke();
		
		assertEquals(new Position(1, 3, false), resultAction.getLastResult());
	}
	
	@Test
	public void previousStopsOnFirstPage()
	{
		sut.currentPage = 1;
		
		sut.getPreviousPage().invoke();
		
		assertEquals(new Position(1, 3, false), resultAction.getLastResult());
	}
	
	@Test
	public void lastSendsLastPage()
	{
		sut.getLastPage().invoke();
		
		assertEquals((Integer) 3, selectedPageNumberAction.getLastResult());
	}
	
	@Test
	public void lastSendsLastPosition()
	{
		sut.getLastPage().invoke();
		
		assertEquals(new Position(3, 3, false), resultAction.getLastResult());
	}
	
	@Test
	public void firstSendsFirstPage()
	{
		sut.getFirstPage().invoke();
		
		assertEquals((Integer) 1, selectedPageNumberAction.getLastResult());
	}
	
	@Test
	public void firstSendsFirstPosition()
	{
		sut.getFirstPage().invoke();
		
		assertEquals(new Position(1, 3, false), resultAction.getLastResult());
	}
	
	@Test
	public void jumpToPageSendsLastPage()
	{
		sut.getJumpToPage().invoke(3);
		
		assertEquals((Integer) 3, selectedPageNumberAction.getLastResult());
	}
	
	@Test
	public void jumpToPageSendsLastPosition()
	{
		sut.getJumpToPage().invoke(3);
		
		assertEquals(new Position(3, 3, false), resultAction.getLastResult());
	}
}
