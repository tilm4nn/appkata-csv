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

import com.google.inject.Inject;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.ebc.impl.StartAndSignalBase;
import net.objectzoo.events.Event0;
import net.objectzoo.events.impl.Event0Delegate;
import net.objectzoo.events.impl.Event0Distributor;

public class RepeatedWaitForCommand extends StartAndSignalBase
{
	private final ConsoleAdapterContract consoleAdapter;
	
	private final Event0Delegate nextPageCommandEvent = new Event0Distributor();
	private final Event0Delegate previousPageCommandEvent = new Event0Distributor();
	private final Event0Delegate lastPageCommandEvent = new Event0Distributor();
	private final Event0Delegate firstPageCommandEvent = new Event0Distributor();
	private final Event0Delegate jumpToPageCommandEvent = new Event0Distributor();
	
	@Inject
	public RepeatedWaitForCommand(ConsoleAdapterContract consoleAdapter)
	{
		this.consoleAdapter = consoleAdapter;
	}
	
	@Override
	public void start()
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
				nextPageCommandEvent.invoke();
				break;
			case 'p':
				previousPageCommandEvent.invoke();
				break;
			case 'f':
				firstPageCommandEvent.invoke();
				break;
			case 'l':
				lastPageCommandEvent.invoke();
				break;
			case 'j':
				jumpToPageCommandEvent.invoke();
				break;
		}
	}
	
	public Event0 nextPageCommandEvent()
	{
		return nextPageCommandEvent;
	}
	
	public Event0 previousPageCommandEvent()
	{
		return previousPageCommandEvent;
	}
	
	public Event0 firstPageCommandEvent()
	{
		return firstPageCommandEvent;
	}
	
	public Event0 lastPageCommandEvent()
	{
		return lastPageCommandEvent;
	}
	
	public Event0 jumpToPageCommandEvent()
	{
		return jumpToPageCommandEvent;
	}
	
}
