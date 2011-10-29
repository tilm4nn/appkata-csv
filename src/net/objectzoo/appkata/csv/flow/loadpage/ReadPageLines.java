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

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import com.google.inject.Inject;

import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.impl.ProcessAndResultBase;

public class ReadPageLines extends ProcessAndResultBase<Long, List<String>>
{
	int pageSize;
	
	String filename;
	
	private final TextFileAdapterContract textFileAdapter;
	
	@Inject
	public ReadPageLines(TextFileAdapterContract textFileAdapter)
	{
		this.textFileAdapter = textFileAdapter;
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
	
	public Action<Integer> initPageSizeAction()
	{
		return initPageSizeAction;
	}
	
	public Action<String> initFilenameAction()
	{
		return initFilenameAction;
	}
	
	@Override
	protected void process(Long pageOffset)
	{
		try
		{
			List<String> pageLines = textFileAdapter.readLines(filename, 0, 1);
			pageLines.addAll(textFileAdapter.readLines(filename, pageOffset, pageSize));
			
			sendResult(pageLines);
		}
		catch (IOException e)
		{
			logger.log(Level.WARNING, "Error reading from file " + filename, e);
		}
	}
}
