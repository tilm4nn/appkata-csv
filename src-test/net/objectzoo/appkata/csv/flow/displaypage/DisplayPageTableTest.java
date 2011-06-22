package net.objectzoo.appkata.csv.flow.displaypage;

import org.hamcrest.Matchers;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.junit.Before;
import org.junit.Test;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.delegates.Action0;

public class DisplayPageTableTest
{
	private Mockery mockery;
	
	private Action0 signalActionMock;
	
	private ConsoleAdapterContract consoleAdapterMock;
	
	private DisplayPageTable sut;
	
	@Before
	public void setup()
	{
		mockery = new Mockery();
		signalActionMock = mockery.mock(Action0.class);
		consoleAdapterMock = mockery.mock(ConsoleAdapterContract.class);
		
		sut = new DisplayPageTable();
	}
	
	@Test
	public void outputsTableToTheConsole()
	{
		sut.inject(consoleAdapterMock);
		
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
	public void sendsSignalAfterOutput()
	{
		sut.inject(consoleAdapterMock);
		sut.getSignal().subscribe(signalActionMock);
		
		mockery.checking(new Expectations()
		{
			{
				Sequence sequence = mockery.sequence("singalAfterOutput");
				
				oneOf(consoleAdapterMock).output(with(Matchers.<String> anything()));
				inSequence(sequence);
				
				oneOf(signalActionMock).invoke();
				inSequence(sequence);
			}
		});
		
		sut.process("TableContents");
		
		mockery.assertIsSatisfied();
	}
}
