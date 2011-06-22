package net.objectzoo.appkata.csv.flow;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;

public class DisplayExitCommandAndWaitTest
{
	private Mockery mockery;
	
	private ConsoleAdapterContract consoleAdapterMock;
	
	private DisplayExitCommandAndWait sut;
	
	@Before
	public void setup()
	{
		mockery = new Mockery();
		consoleAdapterMock = mockery.mock(ConsoleAdapterContract.class);
		
		sut = new DisplayExitCommandAndWait();
	}
	
	@Test
	public void outputsExitCommandToTheConsole()
	{
		sut.inject(consoleAdapterMock);
		
		mockery.checking(new Expectations()
		{
			{
				oneOf(consoleAdapterMock).output("\neX(it\n");
				
				allowing(consoleAdapterMock).input();
				will(returnValue('x'));
			}
		});
		
		sut.start();
		
		mockery.assertIsSatisfied();
	}
	
	@Test
	public void waits10TimesForInputOfX()
	{
		sut.inject(consoleAdapterMock);
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("inputCommandSequence");
				
				allowing(consoleAdapterMock).output(with(Matchers.<String> anything()));
				inSequence(sequence);
				
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
	
}
