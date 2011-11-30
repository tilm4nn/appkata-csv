package net.objectzoo.appkata.csv.flow;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;
import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.ebc.test.MockAction;
import net.objectzoo.ebc.util.Pair;

import org.junit.Before;
import org.junit.Test;

public class SelectPageTest
{
	private Page PAGE_1 = new Page(new CsvLine("foo"), list(new CsvRecord(1, new CsvLine("foo"))));
	private Page PAGE_2 = new Page(new CsvLine("bar"), list(new CsvRecord(2, new CsvLine("bar"))));
	private Page PAGE_3 = new Page(new CsvLine("baz"), list(new CsvRecord(3, new CsvLine("baz"))));
	
	private MockAction<Pair<Page, Position>> resultAction;
	
	private SelectPage sut;
	
	@Before
	public void setup()
	{
		resultAction = new MockAction<Pair<Page, Position>>();
		
		sut = new SelectPage();
		sut.resultEvent().subscribe(resultAction);
		
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
		
		assertEquals(PAGE_1, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void processSendsFirstPosition()
	{
		sut.process(list(PAGE_1, PAGE_2));
		
		assertEquals(new Position(1, 2), resultAction.getLastResult().getItem2());
	}
	
	@Test
	public void nextSendsNextPage()
	{
		sut.nextPageAction().invoke();
		
		assertEquals(PAGE_3, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void nextSendsNextPosition()
	{
		sut.nextPageAction().invoke();
		
		assertEquals(new Position(3, 3), resultAction.getLastResult().getItem2());
	}
	
	@Test
	public void nextStopsOnLastPage()
	{
		sut.currentPage = 2;
		
		sut.nextPageAction().invoke();
		
		assertEquals(PAGE_3, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void previousSendsPreviousPage()
	{
		sut.previousPageAction().invoke();
		
		assertEquals(PAGE_1, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void previousSendsPreviousPosition()
	{
		sut.previousPageAction().invoke();
		
		assertEquals(new Position(1, 3), resultAction.getLastResult().getItem2());
	}
	
	@Test
	public void previousStopsOnFirstPage()
	{
		sut.currentPage = 0;
		
		sut.previousPageAction().invoke();
		
		assertEquals(PAGE_1, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void lastSendsLastPage()
	{
		sut.lastPageAction().invoke();
		
		assertEquals(PAGE_3, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void lastSendsLastPosition()
	{
		sut.lastPageAction().invoke();
		
		assertEquals(new Position(3, 3), resultAction.getLastResult().getItem2());
	}
	
	@Test
	public void firstSendsFirstPage()
	{
		sut.firstPageAction().invoke();
		
		assertEquals(PAGE_1, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void firstSendsFirstPosition()
	{
		sut.firstPageAction().invoke();
		
		assertEquals(new Position(1, 3), resultAction.getLastResult().getItem2());
	}
	
	@Test
	public void jumpToPageSendsLastPage()
	{
		sut.jumpToPageAction().invoke(3);
		
		assertEquals(PAGE_3, resultAction.getLastResult().getItem1());
	}
	
	@Test
	public void jumpToPageSendsLastPosition()
	{
		sut.jumpToPageAction().invoke(3);
		
		assertEquals(new Position(3, 3), resultAction.getLastResult().getItem2());
	}
}
