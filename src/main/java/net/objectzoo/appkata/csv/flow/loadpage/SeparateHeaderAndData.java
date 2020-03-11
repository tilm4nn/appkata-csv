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
package net.objectzoo.appkata.csv.flow.loadpage;

import java.util.List;
import java.util.logging.Level;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.ebc.impl.ProcessBase;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

class SeparateHeaderAndData extends ProcessBase<List<CsvLine>>
{
	private final EventDelegate<CsvLine> newHeaderEvent = new EventDistributor<CsvLine>();
	
	private final EventDelegate<List<CsvLine>> newDataEvent = new EventDistributor<List<CsvLine>>();
	
	private void sendNewHeader(CsvLine header)
	{
		logger.log(Level.FINEST, "sending newHeader: {0}", header);
		
		newHeaderEvent.invoke(header);
	}
	
	private void sendNewData(List<CsvLine> data)
	{
		logger.log(Level.FINEST, "sending newData: {0}", data);
		
		newDataEvent.invoke(data);
	}
	
	public Event<CsvLine> newHeaderEvent()
	{
		return newHeaderEvent;
	}
	
	public Event<List<CsvLine>> newDataEvent()
	{
		return newDataEvent;
	}
	
	@Override
	protected void process(List<CsvLine> csvLines)
	{
		sendNewHeader(csvLines.get(0));
		sendNewData(csvLines.subList(1, csvLines.size()));
	}
}
