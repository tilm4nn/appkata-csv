package net.objectzoo.appkata.csv.dependencies;

import static junit.framework.Assert.assertEquals;
import static net.objectzoo.appkata.csv.Utils.list;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class TextFileAdapterTest
{
	
	@Test
	public void readFromEmptyFile() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines("files-test/ZeroLines.txt", 1);
		
		assertEquals(list(), actual);
	}
	
	@Test
	public void readOneLine() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines("files-test/ThreeLines.txt", 1);
		
		assertEquals(list("Line1"), actual);
	}
	
	@Test
	public void readThreeLines() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines("files-test/ThreeLines.txt", 3);
		
		assertEquals(list("Line1", "Line2", "Line3"), actual);
	}
	
	@Test
	public void readFiveLines() throws IOException
	{
		TextFileAdapterContract sut = new TextFileAdapter();
		
		List<String> actual = sut.readLines("files-test/ThreeLines.txt", 5);
		
		assertEquals(list("Line1", "Line2", "Line3"), actual);
	}
}
