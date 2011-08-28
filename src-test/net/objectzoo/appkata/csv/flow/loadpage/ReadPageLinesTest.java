package net.objectzoo.appkata.csv.flow.loadpage;

import static net.objectzoo.appkata.csv.Utils.list;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.ebc.TestAction;

public class ReadPageLinesTest
{
	
	private ReadPageLines sut;
	
	private TestAction<List<String>> resultAction;
	
	private TextFileAdapterContract textFileAdapterMock;
	
	private Mockery context;
	
	@Before
	public void setup()
	{
		context = new Mockery();
		textFileAdapterMock = context.mock(TextFileAdapterContract.class);
		resultAction = new TestAction<List<String>>();
		
		sut = new ReadPageLines();
		sut.getResult().subscribe(resultAction);
		sut.inject(textFileAdapterMock);
	}
	
	@Test
	public void readsFromCorrectOffsets() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(equal(0L)), with(Matchers.<Integer> anything()));
				one(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(equal(1234567890L)), with(Matchers.<Integer> anything()));
			}
		});
		
		sut.getProcess().invoke(1234567890L);
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void readsFromCorrectLineCount() throws IOException
	{
		sut.pageSize = 34;
		
		context.checking(new Expectations()
		{
			{
				one(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(Matchers.<Long> anything()), with(equal(1)));
				one(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(Matchers.<Long> anything()), with(equal(34)));
			}
		});
		
		sut.getProcess().invoke(1234567890L);
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void returnsCorrectResults() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(Matchers.<Long> anything()), with(Matchers.<Integer> anything()));
				will(returnValue(list("1")));
				one(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(Matchers.<Long> anything()), with(Matchers.<Integer> anything()));
				will(returnValue(list("2", "3")));
			}
		});
		
		sut.getProcess().invoke(1234567890L);
		
		assertThat(resultAction.getLastResult(), is(list("1", "2", "3")));
		
		context.assertIsSatisfied();
	}
}
