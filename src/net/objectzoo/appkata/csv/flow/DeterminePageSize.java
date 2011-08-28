package net.objectzoo.appkata.csv.flow;

import net.objectzoo.ebc.Configurable;
import net.objectzoo.ebc.ResultBase;

public class DeterminePageSize extends ResultBase<Integer> implements Configurable
{
	private static final int DEFAULT_NUMBER_OF_DATA_LINES = 20;
	
	@Override
	public void configure(String... configuration)
	{
		int pageSize = determineNumberOfLines(configuration);
		sendResult(pageSize);
	}
	
	static int determineNumberOfLines(String... parameters) throws NumberFormatException
	{
		int numberOfLines = DEFAULT_NUMBER_OF_DATA_LINES;
		
		if (parameters.length > 1)
		{
			numberOfLines = Integer.parseInt(parameters[1]);
		}
		
		return numberOfLines;
	}
	
}
