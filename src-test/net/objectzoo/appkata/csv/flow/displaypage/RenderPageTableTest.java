package net.objectzoo.appkata.csv.flow.displaypage;

import static net.objectzoo.appkata.csv.Utils.list;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.Pair;

public class RenderPageTableTest
{
	private Mockery mockery;
	
	private Action<String> resultActionMock;
	
	private RenderPageTable sut;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup()
	{
		mockery = new Mockery();
		resultActionMock = mockery.mock(Action.class);
		
		sut = new RenderPageTable();
		sut.getResult().subscribe(resultActionMock);
	}
	
	@Test
	public void rendersTableWithTwoColumnsAndTwoDataRows()
	{
		final String expected = "666666    |88888888|\n" + "----------+--------+\n"
			+ "88888888  |4444    |\n" + "1010101010|22      |\n";
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(expected);
			}
		});
		
		sut.process(new Pair<Page, List<Integer>>(new Page(new CsvLine("666666", "88888888"), list(
			new CsvLine("88888888", "4444"), new CsvLine("1010101010", "22"))), list(10, 8)));
		
		mockery.assertIsSatisfied();
	}
}
