package net.objectzoo.appkata.csv.integration;

import static net.objectzoo.appkata.csv.Utils.list;

import java.io.IOException;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.appkata.csv.flow.DisplayExitCommandAndWait;
import net.objectzoo.appkata.csv.flow.MainBoard;
import net.objectzoo.appkata.csv.flow.ReadLines;
import net.objectzoo.appkata.csv.flow.SeparateHeaderAndData;
import net.objectzoo.appkata.csv.flow.SplitLines;
import net.objectzoo.appkata.csv.flow.displaypage.DetermineColumnLengths;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageBoard;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageTable;
import net.objectzoo.appkata.csv.flow.displaypage.RenderPageTable;

public class Feature11Test
{
	
	@Test
	public void rendersTableWithTwoColumnsAndTwoDataRows() throws IOException
	{
		Mockery mockery = new Mockery();
		final TextFileAdapterContract textFileAdapterMock = mockery.mock(TextFileAdapterContract.class);
		final ConsoleAdapterContract consoleAdapterMock = mockery.mock(ConsoleAdapterContract.class);
		
		ReadLines readLines = new ReadLines();
		DisplayPageTable displayPageTable = new DisplayPageTable();
		DisplayExitCommandAndWait displayExitCommandAndWait = new DisplayExitCommandAndWait();
		new MainBoard(readLines, new SplitLines(), new SeparateHeaderAndData(),
			new DisplayPageBoard(new DetermineColumnLengths(), new RenderPageTable(),
				displayPageTable), displayExitCommandAndWait);
		
		readLines.inject(textFileAdapterMock);
		displayPageTable.inject(consoleAdapterMock);
		displayExitCommandAndWait.inject(consoleAdapterMock);
		
		final String expected = "666666    |88888888|\n" + "----------+--------+\n"
			+ "88888888  |4444    |\n" + "1010101010|22      |\n";
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(textFileAdapterMock).readLines("filename", 6);
				will(returnValue(list("666666;88888888", "88888888;4444", "1010101010;22")));
				
				oneOf(consoleAdapterMock).output(expected);
				
				oneOf(consoleAdapterMock).output("\neX(it\n");
				
				oneOf(consoleAdapterMock).input();
				will(returnValue('x'));
			}
		});
		
		readLines.run("filename", "5");
		
		mockery.assertIsSatisfied();
	}
}
