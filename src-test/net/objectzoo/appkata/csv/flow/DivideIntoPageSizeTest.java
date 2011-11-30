package net.objectzoo.appkata.csv.flow;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import java.util.List;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.PageData;
import net.objectzoo.ebc.test.MockAction;

import org.junit.Before;
import org.junit.Test;

public class DivideIntoPageSizeTest
{
	private MockAction<List<PageData>> resultAction;
	
	private DivideIntoPageSize sut;
	
	@Before
	public void setup()
	{
		resultAction = new MockAction<List<PageData>>();
		
		sut = new DivideIntoPageSize();
		sut.resultEvent().subscribe(resultAction);
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
		
		sut.process(list(new CsvRecord(1, new CsvLine("1")), new CsvRecord(2, new CsvLine("2")), new CsvRecord(3,
			new CsvLine("3")), new CsvRecord(4, new CsvLine("4")), new CsvRecord(5, new CsvLine("5"))));
		
		assertEquals(
			list(new PageData(list(new CsvRecord(1, new CsvLine("1")), new CsvRecord(2, new CsvLine("2")))),
				new PageData(list(new CsvRecord(3, new CsvLine("3")), new CsvRecord(4, new CsvLine("4")))),
				new PageData(list(new CsvRecord(5, new CsvLine("5"))))), resultAction.getLastResult());
	}
}
