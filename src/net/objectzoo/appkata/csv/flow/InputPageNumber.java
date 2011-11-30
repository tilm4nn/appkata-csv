package net.objectzoo.appkata.csv.flow;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.impl.StartAndResultBase;

public class InputPageNumber extends StartAndResultBase<Integer> implements DependsOn<ConsoleAdapterContract>
{
	private ConsoleAdapterContract consoleAdapter;
	
	@Override
	protected void start()
	{
		consoleAdapter.output("\nPlease enter page number: ");
		String numberStr = consoleAdapter.inputStr();
		try
		{
			int number = Integer.parseInt(numberStr);
			sendResult(number);
		}
		catch (NumberFormatException e)
		{
			consoleAdapter.output(numberStr + " is not a valid number.");
		}
	}
	
	@Override
	public void inject(ConsoleAdapterContract dependency)
	{
		this.consoleAdapter = dependency;
	}
	
}
