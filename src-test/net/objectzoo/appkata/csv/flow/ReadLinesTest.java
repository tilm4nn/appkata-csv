package net.objectzoo.appkata.csv.flow;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.delegates.Action;

public class ReadLinesTest
{
	private Mockery mockery;
	
	private TextFileAdapterContract textFileAdapterMock;
	
	private Action<List<String>> resultActionMock;
	
	private ReadLines sut;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setup()
	{
		mockery = new Mockery();
		textFileAdapterMock = mockery.mock(TextFileAdapterContract.class);
		resultActionMock = mockery.mock(Action.class);
		
		sut = new ReadLines();
	}
	
	@Test
	public void determineFilenameReturnsFirstParameter()
	{
		String filename = ReadLines.determineFilename("foo", "bar", "baz");
		
		assertEquals("foo", filename);
	}
	
	@Test
	public void determineNumberOfLinesReturnsParsedSecondParameter()
	{
		int numberOfLines = ReadLines.determineNumberOfLines("foo", "7", "baz");
		
		assertEquals(7, numberOfLines);
	}
	
	@Test
	public void readsNumberPlusOneLines() throws IOException
	{
		sut.inject(textFileAdapterMock);
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(textFileAdapterMock).readLines("filename", 11);
			}
		});
		
		sut.run("filename", "10");
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void sendsReadLines() throws IOException
	{
		sut.inject(textFileAdapterMock);
		sut.getResult().subscribe(resultActionMock);
		
		mockery.checking(new Expectations()
		{
			{
				allowing(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(Matchers.<Integer> anything()));
				will(returnValue(list("Line1", "Line2")));
				
				oneOf(resultActionMock).invoke(list("Line1", "Line2"));
			}
		});
		
		sut.run("filename", "10");
		
		mockery.assertIsSatisfied();
	}
}
