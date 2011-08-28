package net.objectzoo.appkata.csv.flow;

import net.objectzoo.ebc.Configurable;
import net.objectzoo.ebc.ResultBase;

public class DetermineFilename extends ResultBase<String> implements Configurable
{
	
	@Override
	public void configure(String... configuration)
	{
		String filename = determineFilename(configuration);
		sendResult(filename);
	}
	
	static String determineFilename(String... parameters) throws IllegalArgumentException
	{
		if (parameters.length > 0)
		{
			return parameters[0];
		}
		else
		{
			throw new IllegalArgumentException(
				"The CSV file name must be given as first command line parameter.");
		}
	}
	
}
