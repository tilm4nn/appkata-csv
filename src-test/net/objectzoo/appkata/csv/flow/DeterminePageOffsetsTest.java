package net.objectzoo.appkata.csv.flow;

import static net.objectzoo.appkata.csv.Utils.list;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.data.Progress;
import net.objectzoo.appkata.csv.dependencies.TextFileScannerContract;
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.TestAction;

public class DeterminePageOffsetsTest
{
	private DeterminePageOffsets sut;
	
	private TestAction<Pair<Integer, Long>> newPageOffsetAction;
	
	private TestAction<Progress> resultAction;
	
	private TextFileScannerContract textFileScannerMock;
	
	private Mockery context;
	
	@Before
	public void setup()
	{
		context = new Mockery();
		
		newPageOffsetAction = new TestAction<Pair<Integer, Long>>(true);
		resultAction = new TestAction<Progress>(true);
		textFileScannerMock = context.mock(TextFileScannerContract.class);
		
		sut = new DeterminePageOffsets();
		sut.getNewPageOffset().subscribe(newPageOffsetAction);
		sut.getResult().subscribe(resultAction);
		sut.inject(textFileScannerMock);
	}
	
	@Test
	public void startOpensFile() throws IOException
	{
		sut.filename = "filename";
		
		context.checking(new Expectations()
		{
			{
				one(textFileScannerMock).openFile("filename");
				
				allowing(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(null));
				
				ignoring(textFileScannerMock);
			}
		});
		
		sut.getStart().invoke();
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void startClosesFile() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileScannerMock).closeFile();
				
				allowing(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(null));
				
				ignoring(textFileScannerMock);
			}
		});
		
		sut.getStart().invoke();
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void startSkipsFirstLine() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileScannerMock).getNextPosition(1);
				
				allowing(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(null));
				
				ignoring(textFileScannerMock);
			}
		});
		
		sut.getStart().invoke();
		
		context.assertIsSatisfied();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void startSendsNewPageOffsetWithCorrectOffsets() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(1111111111L));
				
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(2222222222L));
				
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(3333333333L));
				
				allowing(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(null));
				
				ignoring(textFileScannerMock);
			}
		});
		
		sut.getStart().invoke();
		
		assertThat(
			newPageOffsetAction.getResults(),
			is(list(new Pair<Integer, Long>(1, 1111111111L),
				new Pair<Integer, Long>(2, 2222222222L), new Pair<Integer, Long>(3, 3333333333L))));
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void startSendsProgressWithCorrectValuesAfterSuccessfulScan() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(1234567890L));
				
				allowing(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(null));
				
				ignoring(textFileScannerMock);
			}
		});
		
		sut.getStart().invoke();
		
		assertThat(resultAction.getFirstResult(), is(new Progress(1, false)));
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void startSendsProgressWithCorrectValuesAfterUnsuccessfulScan() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(1234567890L));
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(1234567890L));
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(1234567890L));
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(1234567890L));
				one(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(1234567890L));
				
				allowing(textFileScannerMock).getNextPosition(with(any(Integer.class)));
				will(returnValue(null));
				
				ignoring(textFileScannerMock);
			}
		});
		
		sut.getStart().invoke();
		
		assertThat(resultAction.getLastResult(), is(new Progress(5, true)));
		
		context.assertIsSatisfied();
	}
}
