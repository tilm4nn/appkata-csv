package net.objectzoo.appkata.csv.flow;

import static net.objectzoo.appkata.csv.Utils.list;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.Utils;
import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.delegates.Action;

public class SplitLinesTest
{
	private Mockery mockery;
	
	private Action<List<CsvLine>> resultActionMock;
	
	private SplitLines sut;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup()
	{
		mockery = new Mockery();
		resultActionMock = mockery.mock(Action.class);
		
		sut = new SplitLines();
		sut.getResult().subscribe(resultActionMock);
	}
	
	@Test
	public void splitsTwoValues()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(list(new CsvLine("Value1", "Value2")));
			}
		});
		
		sut.process(list("Value1;Value2"));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void splitsOneValue()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(list(new CsvLine("Value1")));
			}
		});
		
		sut.process(list("Value1"));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void splitsNoValue()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(list(new CsvLine("")));
			}
		});
		
		sut.process(list(""));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void splitsTwoLines()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(
					list(new CsvLine("Value1", "Value2"), new CsvLine("Value3", "Value4")));
			}
		});
		
		sut.process(list("Value1;Value2", "Value3;Value4"));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void splitsNoLine()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(resultActionMock).invoke(Utils.<CsvLine> list());
			}
		});
		
		sut.process(Utils.<String> list());
		
		mockery.assertIsSatisfied();
	}
}
