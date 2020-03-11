/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:info@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
import net.objectzoo.ebc.test.MockAction;
import net.objectzoo.ebc.util.Pair;

public class DeterminePageOffsetsTest
{
	private DeterminePageOffsets sut;
	
	private MockAction<Pair<Integer, Long>> newPageOffsetAction;
	
	private MockAction<Progress> resultAction;
	
	private TextFileScannerContract textFileScannerMock;
	
	private Mockery context;
	
	@Before
	public void setup()
	{
		context = new Mockery();
		
		newPageOffsetAction = new MockAction<Pair<Integer, Long>>(Integer.MAX_VALUE);
		resultAction = new MockAction<Progress>(Integer.MAX_VALUE);
		textFileScannerMock = context.mock(TextFileScannerContract.class);
		
		sut = new DeterminePageOffsets(textFileScannerMock);
		sut.newPageOffsetEvent().subscribe(newPageOffsetAction);
		sut.resultEvent().subscribe(resultAction);
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
		
		sut.startAction().invoke();
		
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
		
		sut.startAction().invoke();
		
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
		
		sut.startAction().invoke();
		
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
		
		sut.startAction().invoke();
		
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
		
		sut.startAction().invoke();
		
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
		
		sut.startAction().invoke();
		
		assertThat(resultAction.getLastResult(), is(new Progress(5, true)));
		
		context.assertIsSatisfied();
	}
}
