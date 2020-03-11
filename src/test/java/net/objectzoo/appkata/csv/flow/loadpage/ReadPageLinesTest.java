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
import net.objectzoo.ebc.test.MockAction;

public class ReadPageLinesTest
{
	
	private ReadPageLines sut;
	
	private MockAction<List<String>> resultAction;
	
	private TextFileAdapterContract textFileAdapterMock;
	
	private Mockery context;
	
	@Before
	public void setup()
	{
		context = new Mockery();
		textFileAdapterMock = context.mock(TextFileAdapterContract.class);
		resultAction = new MockAction<List<String>>();
		
		sut = new ReadPageLines(textFileAdapterMock);
		sut.resultEvent().subscribe(resultAction);
	}
	
	@Test
	public void readsFromCorrectOffsets() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileAdapterMock).readLines(with(any(String.class)),
					with(equal(0L)), with(any(Integer.class)));
				one(textFileAdapterMock).readLines(with(any(String.class)),
					with(equal(1234567890L)), with(any(Integer.class)));
			}
		});
		
		sut.processAction().invoke(1234567890L);
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void readsFromCorrectLineCount() throws IOException
	{
		sut.pageSize = 34;
		
		context.checking(new Expectations()
		{
			{
				one(textFileAdapterMock).readLines(with(any(String.class)),
					with(any(Long.class)), with(equal(1)));
				one(textFileAdapterMock).readLines(with(any(String.class)),
					with(any(Long.class)), with(equal(34)));
			}
		});
		
		sut.processAction().invoke(1234567890L);
		
		context.assertIsSatisfied();
	}
	
	@Test
	public void returnsCorrectResults() throws IOException
	{
		context.checking(new Expectations()
		{
			{
				one(textFileAdapterMock).readLines(with(any(String.class)),
					with(any(Long.class)), with(any(Integer.class)));
				will(returnValue(list("1")));
				one(textFileAdapterMock).readLines(with(any(String.class)),
					with(any(Long.class)), with(any(Integer.class)));
				will(returnValue(list("2", "3")));
			}
		});
		
		sut.processAction().invoke(1234567890L);
		
		assertThat(resultAction.getLastResult(), is(list("1", "2", "3")));
		
		context.assertIsSatisfied();
	}
}
