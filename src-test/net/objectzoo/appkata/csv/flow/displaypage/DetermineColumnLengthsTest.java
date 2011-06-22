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

public class DetermineColumnLengthsTest
{
	private Mockery mockery;
	
	private Action<List<Integer>> resultActionMock;
	
	private DetermineColumnLengths sut;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup()
	{
		mockery = new Mockery();
		resultActionMock = mockery.mock(Action.class);
		
		sut = new DetermineColumnLengths();
		sut.getResult().subscribe(resultActionMock);
	}
	
	@Test
	public void takesMaxLengthFromHeader()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(list(8, 10));
			}
		});
		
		sut.process(new Page(new CsvLine("VeryLong", "EvenLonger"), list(new CsvLine("Short",
			"Values"))));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void takesMaxLengthFromData()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(list(8, 10));
			}
		});
		
		sut.process(new Page(new CsvLine("Short", "Values"), list(
			new CsvLine("VeryLong", "Values"), new CsvLine("Short", "EvenLonger"))));
		
		mockery.assertIsSatisfied();
	}
}
