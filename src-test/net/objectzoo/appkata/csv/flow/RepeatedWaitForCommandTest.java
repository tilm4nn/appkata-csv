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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.ebc.test.MockAction0;

public class RepeatedWaitForCommandTest
{
	private Mockery mockery;
	
	private ConsoleAdapterContract consoleAdapterMock;
	
	private RepeatedWaitForCommand sut;
	
	private MockAction0 resultAction;
	
	@Before
	public void setup()
	{
		mockery = new Mockery();
		consoleAdapterMock = mockery.mock(ConsoleAdapterContract.class);
		
		resultAction = new MockAction0();
		
		sut = new RepeatedWaitForCommand(consoleAdapterMock);
	}
	
	@Test
	public void waits10TimesForInputOfX()
	{
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("inputCommandSequence");
				
				exactly(10).of(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('Q'));
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('x'));
			}
		});
		
		sut.start();
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void signalsNextPageCommandForInputOfX()
	{
		sut.nextPageCommandEvent().subscribe(resultAction);
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("inputCommandSequence");
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('n'));
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('x'));
			}
		});
		
		sut.start();
		
		resultAction.assertInvoked();
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void signalsPreviousPageCommandForInputOfX()
	{
		sut.previousPageCommandEvent().subscribe(resultAction);
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("inputCommandSequence");
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('p'));
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('x'));
			}
		});
		
		sut.start();
		
		resultAction.assertInvoked();
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void signalsFirstPageCommandForInputOfX()
	{
		sut.firstPageCommandEvent().subscribe(resultAction);
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("inputCommandSequence");
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('f'));
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('x'));
			}
		});
		
		sut.start();
		
		resultAction.assertInvoked();
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void signalsLastPageCommandForInputOfX()
	{
		sut.lastPageCommandEvent().subscribe(resultAction);
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("inputCommandSequence");
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('l'));
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('x'));
			}
		});
		
		sut.start();
		
		resultAction.assertInvoked();
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void signalsJumpToPageCommandForInputOfJ()
	{
		sut.jumpToPageCommandEvent().subscribe(resultAction);
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("inputCommandSequence");
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('j'));
				
				oneOf(consoleAdapterMock).input();
				inSequence(sequence);
				will(returnValue('x'));
			}
		});
		
		sut.start();
		
		resultAction.assertInvoked();
		mockery.assertIsSatisfied();
	}
	
}
