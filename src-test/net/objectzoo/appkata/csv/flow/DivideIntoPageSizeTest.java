package net.objectzoo.appkata.csv.flow;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.PageData;
import net.objectzoo.ebc.TestAction;

public class DivideIntoPageSizeTest
{
	private TestAction<List<PageData>> resultAction;
	
	private DivideIntoPageSize sut;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<List<PageData>>();
		
		sut = new DivideIntoPageSize();
		sut.getResult().subscribe(resultAction);
	}
	
	@Test
	public void determineNumberOfLinesReturnsParsedSecondParameter()
	{
		int numberOfLines = DivideIntoPageSize.determineNumberOfLines("foo", "7", "baz");
		
		assertEquals(7, numberOfLines);
	}
	
	@Test
	public void dividesIntoThreePageData()
	{
		sut.configure("", "2");
		
		sut.process(list(new CsvLine("1"), new CsvLine("2"), new CsvLine("3"), new CsvLine("4"),
			new CsvLine("5")));
		
		assertEquals(
			list(new PageData(list(new CsvLine("1"), new CsvLine("2"))),
				new PageData(list(new CsvLine("3"), new CsvLine("4"))), new PageData(
					list(new CsvLine("5")))), resultAction.getResult());
	}
}
