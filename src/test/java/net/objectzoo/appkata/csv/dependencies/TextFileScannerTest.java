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
package net.objectzoo.appkata.csv.dependencies;

import static net.objectzoo.appkata.csv.dependencies.TextFileAdapterTest.TEST_FILES_DIR;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TextFileScannerTest
{
	
	TextFileScanner sut = new TextFileScanner();
	
	@Before
	public void openFile() throws IOException
	{
		sut.openFile(TEST_FILES_DIR + "TenLines.txt");
	}
	
	@After
	public void closeFile() throws IOException
	{
		sut.closeFile();
	}
	
	@Test
	public void returns0After0Lines() throws IOException
	{
		Long result = sut.getNextPosition(0);
		
		assertThat(result, is(0L));
	}
	
	@Test
	public void returns6After1Line() throws IOException
	{
		Long result = sut.getNextPosition(1);
		
		assertThat(result, is(6L));
	}
	
	@Test
	public void returns18After3Lines() throws IOException
	{
		Long result = sut.getNextPosition(3);
		
		assertThat(result, is(18L));
	}
	
	@Test
	public void returnsNullAfter10Lines() throws IOException
	{
		Long result = sut.getNextPosition(10);
		
		assertThat(result, is((Long) null));
	}
	
	@Test
	public void returnsNullAfter15Lines() throws IOException
	{
		Long result = sut.getNextPosition(15);
		
		assertThat(result, is((Long) null));
	}
	
}
