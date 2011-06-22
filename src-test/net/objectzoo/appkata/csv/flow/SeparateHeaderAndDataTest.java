package net.objectzoo.appkata.csv.flow;

import static net.objectzoo.appkata.csv.Utils.list;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.delegates.Action;

public class SeparateHeaderAndDataTest
{
	private Mockery mockery;
	
	private Action<CsvLine> newHeaderActionMock;
	
	private Action<List<CsvLine>> newDataActionMock;
	
	private SeparateHeaderAndData sut;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup()
	{
		mockery = new Mockery();
		newDataActionMock = mockery.mock(Action.class, "newDataActionMock");
		newHeaderActionMock = mockery.mock(Action.class, "newHeaderActionMock");
		
		sut = new SeparateHeaderAndData();
	}
	
	@Test
	public void sendNewHeader()
	{
		sut.getNewHeader().subscribe(newHeaderActionMock);
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(newHeaderActionMock).invoke(new CsvLine("Header"));
			}
		});
		
		sut.process(list(new CsvLine("Header"), new CsvLine("Data")));
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void sendNewData()
	{
		sut.getNewData().subscribe(newDataActionMock);
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(newDataActionMock).invoke(list(new CsvLine("Data1"), new CsvLine("Data2")));
			}
		});
		
		sut.process(list(new CsvLine("Header"), new CsvLine("Data1"), new CsvLine("Data2")));
		
		mockery.assertIsSatisfied();
	}
}
