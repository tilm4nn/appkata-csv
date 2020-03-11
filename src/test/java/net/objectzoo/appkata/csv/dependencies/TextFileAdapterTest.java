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

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class TextFileAdapterTest
{

	public static final String TEST_FILES_DIR = "src/test/resources/net/objectzoo/appkata/csv/";

	@Test
	public void readFromEmptyFile() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines(TEST_FILES_DIR + "ZeroLines.txt", 0, 1);
		
		assertEquals(list(), actual);
	}
	
	@Test
	public void readOneLineFromBeginning() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines(TEST_FILES_DIR + "ThreeLines.txt", 0, 1);
		
		assertEquals(list("Line1"), actual);
	}
	
	@Test
	public void readThreeLinesFromBeginning() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines(TEST_FILES_DIR + "ThreeLines.txt", 0, 3);
		
		assertEquals(list("Line1", "Line2", "Line3"), actual);
	}
	
	@Test
	public void readFiveLinesFromBeginning() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines(TEST_FILES_DIR + "ThreeLines.txt", 0, 5);
		
		assertEquals(list("Line1", "Line2", "Line3"), actual);
	}
	
	@Test
	public void readOneLineFromMiddle() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines(TEST_FILES_DIR + "ThreeLines.txt", 6, 1);
		
		assertEquals(list("Line2"), actual);
	}
	
	@Test
	public void readTwooLinesFromMiddle() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines(TEST_FILES_DIR + "ThreeLines.txt", 6, 2);
		
		assertEquals(list("Line2", "Line3"), actual);
	}
	
	@Test
	public void readFiveLinesFromMiddle() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines(TEST_FILES_DIR + "ThreeLines.txt", 6, 5);
		
		assertEquals(list("Line2", "Line3"), actual);
	}
}
