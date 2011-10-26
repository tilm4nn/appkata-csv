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
package net.objectzoo.appkata.csv.flow.displaypage;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.ebc.TestAction0;

public class DisplayPageTableTest
{
	private Mockery mockery;
	
	private TestAction0 signalAction;
	
	private ConsoleAdapterContract consoleAdapterMock;
	
	private DisplayPageViewModel sut;
	
	@Before
	public void setup()
	{
		mockery = new Mockery();
		consoleAdapterMock = mockery.mock(ConsoleAdapterContract.class);
		signalAction = new TestAction0();
		
		sut = new DisplayPageViewModel(consoleAdapterMock);
		sut.getSignal().subscribe(signalAction);
	}
	
	@Test
	public void outputsTableToTheConsole()
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(consoleAdapterMock).output("TableContents");
			}
		});
		
		sut.process("TableContents");
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void sendsSignal()
	{
		mockery.checking(new Expectations()
		{
			{
				allowing(consoleAdapterMock).output(with(Matchers.<String> anything()));
			}
		});
		
		sut.process("TableContents");
		
		signalAction.assertInvoked();
	}
}
