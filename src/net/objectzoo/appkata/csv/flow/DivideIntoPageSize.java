package net.objectzoo.appkata.csv.flow;

import java.util.ArrayList;
import java.util.List;

import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.PageData;
import net.objectzoo.ebc.Configurable;
import net.objectzoo.ebc.ProcessAndResultBase;

public class DivideIntoPageSize extends ProcessAndResultBase<List<CsvRecord>, List<PageData>>
	implements Configurable
{
	private static final int DEFAULT_NUMBER_OF_DATA_LINES = 20;
	
	int pageSize;
	
	@Override
	public void configure(String... configuration)
	{
		pageSize = determineNumberOfLines(configuration);
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
	
	@Override
	protected void process(List<CsvRecord> dataLines)
	{
		int pageCount = dataLines.size() / pageSize;
		int remainder = dataLines.size() % pageSize;
		
		List<PageData> pageData = new ArrayList<PageData>(pageCount + 1);
		
		for (int i = 0; i < pageCount; i++)
		{
			pageData.add(new PageData(dataLines.subList(i * pageSize, (i + 1) * pageSize)));
		}
		if (remainder > 0)
		{
			pageData.add(new PageData(dataLines.subList(pageCount * pageSize, dataLines.size())));
		}
		
		sendResult(pageData);
	}
}
