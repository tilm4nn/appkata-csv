package net.objectzoo.appkata.csv.flow;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.TestAction;

public class SelectPageTest
{
	private Page PAGE_1 = new Page(new CsvLine("foo"), list(new CsvRecord(1, new CsvLine("foo"))));
	private Page PAGE_2 = new Page(new CsvLine("bar"), list(new CsvRecord(2, new CsvLine("bar"))));
	private Page PAGE_3 = new Page(new CsvLine("baz"), list(new CsvRecord(3, new CsvLine("baz"))));
	
	private TestAction<Pair<Page, Position>> resultAction;
	
	private SelectPage sut;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<Pair<Page, Position>>();
		
		sut = new SelectPage();
		sut.getResult().subscribe(resultAction);
		
		sut.pages = list(PAGE_1, PAGE_2, PAGE_3);
		sut.currentPage = 1;
	}
	
	@Test
	public void processSetsPages()
	{
		sut.process(list(PAGE_1, PAGE_3));
		
		assertEquals(list(PAGE_1, PAGE_3), sut.pages);
	}
	
	@Test
	public void processResetsCurrentPage()
	{
		sut.process(list(PAGE_1, PAGE_2));
		
		assertEquals(0, sut.currentPage);
	}
	
	@Test
	public void processSendsFirstPage()
	{
		sut.process(list(PAGE_1, PAGE_2));
		
		assertEquals(PAGE_1, resultAction.getResult().getItem1());
	}
	
	@Test
	public void processSendsFirstPosition()
	{
		sut.process(list(PAGE_1, PAGE_2));
		
		assertEquals(new Position(1, 2), resultAction.getResult().getItem2());
	}
	
	@Test
	public void nextSendsNextPage()
	{
		sut.getNextPage().invoke();
		
		assertEquals(PAGE_3, resultAction.getResult().getItem1());
	}
	
	@Test
	public void nextSendsNextPosition()
	{
		sut.getNextPage().invoke();
		
		assertEquals(new Position(3, 3), resultAction.getResult().getItem2());
	}
	
	@Test
	public void nextStopsOnLastPage()
	{
		sut.currentPage = 2;
		
		sut.getNextPage().invoke();
		
		assertEquals(PAGE_3, resultAction.getResult().getItem1());
	}
	
	@Test
	public void previousSendsPreviousPage()
	{
		sut.getPreviousPage().invoke();
		
		assertEquals(PAGE_1, resultAction.getResult().getItem1());
	}
	
	@Test
	public void previousSendsPreviousPosition()
	{
		sut.getPreviousPage().invoke();
		
		assertEquals(new Position(1, 3), resultAction.getResult().getItem2());
	}
	
	@Test
	public void previousStopsOnFirstPage()
	{
		sut.currentPage = 0;
		
		sut.getPreviousPage().invoke();
		
		assertEquals(PAGE_1, resultAction.getResult().getItem1());
	}
	
	@Test
	public void lastSendsLastPage()
	{
		sut.getLastPage().invoke();
		
		assertEquals(PAGE_3, resultAction.getResult().getItem1());
	}
	
	@Test
	public void lastSendsLastPosition()
	{
		sut.getLastPage().invoke();
		
		assertEquals(new Position(3, 3), resultAction.getResult().getItem2());
	}
	
	@Test
	public void firstSendsFirstPage()
	{
		sut.getFirstPage().invoke();
		
		assertEquals(PAGE_1, resultAction.getResult().getItem1());
	}
	
	@Test
	public void firstSendsFirstPosition()
	{
		sut.getFirstPage().invoke();
		
		assertEquals(new Position(1, 3), resultAction.getResult().getItem2());
	}
	
	@Test
	public void jumpToPageSendsLastPage()
	{
		sut.getJumpToPage().invoke(3);
		
		assertEquals(PAGE_3, resultAction.getResult().getItem1());
	}
	
	@Test
	public void jumpToPageSendsLastPosition()
	{
		sut.getJumpToPage().invoke(3);
		
		assertEquals(new Position(3, 3), resultAction.getResult().getItem2());
	}
}
