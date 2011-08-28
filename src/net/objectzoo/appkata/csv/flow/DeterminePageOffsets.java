package net.objectzoo.appkata.csv.flow;

import static java.util.logging.Level.WARNING;

import java.io.IOException;

import net.objectzoo.appkata.csv.data.Progress;
import net.objectzoo.appkata.csv.dependencies.TextFileScannerContract;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.StartAndResultBase;
import net.objectzoo.events.impl.EventDistributor;

public class DeterminePageOffsets extends StartAndResultBase<Progress> implements
	DependsOn<TextFileScannerContract>
{
	
	int pageSize;
	
	String filename;
	
	TextFileScannerContract textFileScanner;
	
	private final EventDistributor<Pair<Integer, Long>> newPageOffset = new EventDistributor<Pair<Integer, Long>>();
	
	public EventDistributor<Pair<Integer, Long>> getNewPageOffset()
	{
		return newPageOffset;
	}
	
	public Action<Integer> getSetPageSize()
	{
		return setPageSize;
	}
	
	public Action<String> getSetFilename()
	{
		return setFilename;
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
					newPageOffset.invoke(new Pair<Integer, Long>(pageNr, pageOffset));
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
			log.log(WARNING, "Problem while accessing file " + filename, e);
		}
	}
	
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
	
	@Override
	public void inject(TextFileScannerContract dependency)
	{
		log.finer("inject");
		
		textFileScanner = dependency;
	}
	
}
