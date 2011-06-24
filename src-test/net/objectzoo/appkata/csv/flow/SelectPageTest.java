package net.objectzoo.appkata.csv.flow;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.ebc.TestAction;

public class SelectPageTest
{
	private Page PAGE_1 = new Page(new CsvLine("foo"), list(new CsvRecord(1, new CsvLine("foo"))));
	private Page PAGE_2 = new Page(new CsvLine("bar"), list(new CsvRecord(2, new CsvLine("bar"))));
	private Page PAGE_3 = new Page(new CsvLine("baz"), list(new CsvRecord(3, new CsvLine("baz"))));
	
	private TestAction<Page> resultAction;
	
	private SelectPage sut;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<Page>();
		
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
	public void processSelectsFirstPage()
	{
		sut.process(list(PAGE_1, PAGE_2));
		
		assertEquals(PAGE_1, resultAction.getResult());
	}
	
	@Test
	public void nextSelectsNextPage()
	{
		sut.getNextPage().invoke();
		
		assertEquals(PAGE_3, resultAction.getResult());
	}
	
	@Test
	public void nextStopsOnLastPage()
	{
		sut.currentPage = 2;
		
		sut.getNextPage().invoke();
		
		assertEquals(PAGE_3, resultAction.getResult());
	}
	
	@Test
	public void previousSelectsPreviousPage()
	{
		sut.getPreviousPage().invoke();
		
		assertEquals(PAGE_1, resultAction.getResult());
	}
	
	@Test
	public void previousStopsOnFirstPage()
	{
		sut.currentPage = 0;
		
		sut.getPreviousPage().invoke();
		
		assertEquals(PAGE_1, resultAction.getResult());
	}
	
	@Test
	public void lastReturnsLastPage()
	{
		sut.getLastPage().invoke();
		
		assertEquals(PAGE_3, resultAction.getResult());
	}
	
	@Test
	public void firstReturnsFirstPage()
	{
		sut.getFirstPage().invoke();
		
		assertEquals(PAGE_1, resultAction.getResult());
	}
}
