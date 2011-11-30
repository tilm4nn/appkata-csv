package net.objectzoo.appkata.csv.flow;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.impl.StartBase;

public class DisplayCommands extends StartBase implements DependsOn<ConsoleAdapterContract>
{
	private ConsoleAdapterContract consoleAdapter;
	
	@Override
	protected void start()
	{
		consoleAdapter.output("\nN(ext page, P(revious page, F(irst page, L(ast page, J(ump to page, eX(it\n");
	}
	
	@Override
	public void inject(ConsoleAdapterContract dependency)
	{
		consoleAdapter = dependency;
	}
	
}
