package net.objectzoo.appkata.csv.dependencies;

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
		sut.openFile("files-test/TenLines.txt");
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
		
		assertThat(result, is(7L));
	}
	
	@Test
	public void returns21After3Lines() throws IOException
	{
		Long result = sut.getNextPosition(3);
		
		assertThat(result, is(21L));
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
