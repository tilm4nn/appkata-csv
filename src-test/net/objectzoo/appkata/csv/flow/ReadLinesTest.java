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

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import java.io.IOException;
import java.util.List;

import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.ebc.test.MockAction;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class ReadLinesTest
{
	private Mockery mockery;
	
	private TextFileAdapterContract textFileAdapterMock;
	
	private MockAction<List<String>> resultAction;
	
	private ReadLines sut;
	
	@Before
	public void setup()
	{
		mockery = new Mockery();
		textFileAdapterMock = mockery.mock(TextFileAdapterContract.class);
		resultAction = new MockAction<List<String>>();
		
		sut = new ReadLines();
		sut.resultEvent().subscribe(resultAction);
		sut.inject(textFileAdapterMock);
	}
	
	@Test
	public void determineFilenameReturnsFirstParameter()
	{
		String filename = ReadLines.determineFilename("foo", "bar", "baz");
		
		assertEquals("foo", filename);
	}
	
	@Test
	public void readsCorrectNumberOfLines() throws IOException
	{
		mockery.checking(new Expectations()
		{
			{
				oneOf(textFileAdapterMock).readLines("filename", Integer.MAX_VALUE);
			}
		});
		
		sut.configure("filename");
		sut.start();
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void sendsReadLines() throws IOException
	{
		mockery.checking(new Expectations()
		{
			{
				allowing(textFileAdapterMock).readLines(with(Matchers.<String> anything()),
					with(Matchers.<Integer> anything()));
				will(returnValue(list("Line1", "Line2")));
			}
		});
		
		sut.configure("filename");
		sut.start();
		
		assertEquals(list("Line1", "Line2"), resultAction.getLastResult());
		
		mockery.assertIsSatisfied();
	}
}
