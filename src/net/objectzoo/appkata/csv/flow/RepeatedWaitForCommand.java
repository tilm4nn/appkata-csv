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
package net.objectzoo.appkata.csv.flow;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.StartAndSignalBase;
import net.objectzoo.events.Event0;
import net.objectzoo.events.impl.Event0Distributor;

public class RepeatedWaitForCommand extends StartAndSignalBase implements
	DependsOn<ConsoleAdapterContract>
{
	private ConsoleAdapterContract consoleAdapter;
	
	private final Event0Distributor nextPageCommand = new Event0Distributor();
	private final Event0Distributor previousPageCommand = new Event0Distributor();
	private final Event0Distributor lastPageCommand = new Event0Distributor();
	private final Event0Distributor firstPageCommand = new Event0Distributor();
	private final Event0Distributor jumpToPageCommand = new Event0Distributor();
	
	@Override
	public void inject(ConsoleAdapterContract dependency)
	{
		this.consoleAdapter = dependency;
	}
	
	@Override
	protected void start()
	{
		sendSignal();
		repeatedWaitForCommand();
	}
	
	private void repeatedWaitForCommand()
	{
		char input = ' ';
		while (input != 'x')
		{
			input = consoleAdapter.input();
			handleInput(input);
		}
	}
	
	private void handleInput(char input)
	{
		switch (input)
		{
			case 'n':
				nextPageCommand.invoke();
				break;
			case 'p':
				previousPageCommand.invoke();
				break;
			case 'f':
				firstPageCommand.invoke();
				break;
			case 'l':
				lastPageCommand.invoke();
				break;
			case 'j':
				jumpToPageCommand.invoke();
				break;
		}
	}
	
	public Event0 getNextPageCommand()
	{
		return nextPageCommand;
	}
	
	public Event0 getPreviousPageCommand()
	{
		return previousPageCommand;
	}
	
	public Event0 getFirstPageCommand()
	{
		return firstPageCommand;
	}
	
	public Event0 getLastPageCommand()
	{
		return lastPageCommand;
	}
	
	public Event0Distributor getJumpToPageCommand()
	{
		return jumpToPageCommand;
	}
	
}
