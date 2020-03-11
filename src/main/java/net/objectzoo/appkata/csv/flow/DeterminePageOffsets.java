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

import static java.util.logging.Level.WARNING;

import java.io.IOException;

import com.google.inject.Inject;

import net.objectzoo.appkata.csv.data.Progress;
import net.objectzoo.appkata.csv.dependencies.TextFileScannerContract;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.impl.StartAndResultBase;
import net.objectzoo.ebc.util.Pair;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

class DeterminePageOffsets extends StartAndResultBase<Progress>
{
	int pageSize;
	
	String filename;
	
	private final TextFileScannerContract textFileScanner;
	
	private final EventDelegate<Pair<Integer, Long>> newPageOffsetEvent = new EventDistributor<Pair<Integer, Long>>();
	
	@Inject
	public DeterminePageOffsets(TextFileScannerContract textFileScanner)
	{
		this.textFileScanner = textFileScanner;
	}
	
	public Event<Pair<Integer, Long>> newPageOffsetEvent()
	{
		return newPageOffsetEvent;
	}
	
	public Action<Integer> initPageSizeAction()
	{
		return initPageSizeAction;
	}
	
	public Action<String> initFilenameAction()
	{
		return initFilenameAction;
	}
	
	@Override
	protected void start()
	{
		try
		{
			textFileScanner.openFile(filename);
			try
			{
				int pageNr = 0;
				Long pageOffset = textFileScanner.getNextPosition(1);
				while (pageOffset != null)
				{
					pageNr += 1;
					newPageOffsetEvent.invoke(new Pair<Integer, Long>(pageNr, pageOffset));
					sendResult(new Progress(pageNr, false));
					pageOffset = textFileScanner.getNextPosition(pageSize);
				}
				sendResult(new Progress(pageNr, true));
			}
			finally
			{
				textFileScanner.closeFile();
			}
		}
		catch (IOException e)
		{
			logger.log(WARNING, "Problem while accessing file " + filename, e);
		}
	}
	
	private final Action<Integer> initPageSizeAction = new Action<Integer>()
	{
		@Override
		public void invoke(Integer input)
		{
			pageSize = input;
		}
	};
	
	private final Action<String> initFilenameAction = new Action<String>()
	{
		@Override
		public void invoke(String input)
		{
			filename = input;
		}
	};
}
