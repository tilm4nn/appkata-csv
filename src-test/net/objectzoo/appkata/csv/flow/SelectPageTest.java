package net.objectzoo.appkata.csv.flow;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.ebc.TestAction;

public class SelectPageTest
{
	private TestAction<Page> resultAction;
	
	private SelectPage sut;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<Page>();
		
		sut = new SelectPage();
		sut.getResult().subscribe(resultAction);
	}
	
	@Test
	public void processSelectsFirstPage()
	{
		sut.process(list(new Page(new CsvLine("foo"), list(new CsvLine("foo"))), new Page(
			new CsvLine("bar"), list(new CsvLine("bar")))));
		
		assertEquals(new Page(new CsvLine("foo"), list(new CsvLine("foo"))),
			resultAction.getResult());
	}
}
