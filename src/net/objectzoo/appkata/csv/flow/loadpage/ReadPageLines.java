package net.objectzoo.appkata.csv.flow.loadpage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.ProcessAndResultBase;

public class ReadPageLines extends ProcessAndResultBase<Long, List<String>> implements
	DependsOn<TextFileAdapterContract>
{
	int pageSize;
	
	String filename;
	
	private TextFileAdapterContract textFileAdapter;
	
	private final Action<Integer> setPageSize = new Action<Integer>()
	{
		@Override
		public void invoke(Integer input)
		{
			pageSize = input;
		}
	};
	
	private final Action<String> setFilename = new Action<String>()
	{
		@Override
		public void invoke(String input)
		{
			filename = input;
		}
	};
	
	public Action<Integer> getSetPageSize()
	{
		return setPageSize;
	}
	
	public Action<String> getSetFilename()
	{
		return setFilename;
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
			log.log(Level.WARNING, "Error reading from file " + filename, e);
		}
	}
	
	@Override
	public void inject(TextFileAdapterContract dependency)
	{
		log.finer("inject");
		
		textFileAdapter = dependency;
	}
	
}
