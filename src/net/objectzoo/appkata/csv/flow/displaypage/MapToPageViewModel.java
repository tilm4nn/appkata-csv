package net.objectzoo.appkata.csv.flow.displaypage;

import java.util.List;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.displaypage.PageViewModel;
import net.objectzoo.ebc.ProcessAndResultBase;

public class MapToPageViewModel extends ProcessAndResultBase<Page, PageViewModel>
{
	
	@Override
	protected void process(Page page)
	{
		String[] headers = mapCsvLine("No.", page.getHeader());
		String[][] rows = mapRecords(page.getRecords());
		
		sendResult(new PageViewModel(headers, rows));
	}
	
	static String[][] mapRecords(List<CsvRecord> records)
	{
		String[][] rows = new String[records.size()][];
		for (int i = 0; i < records.size(); i++)
		{
			CsvRecord record = records.get(i);
			rows[i] = mapCsvLine(Integer.toString(record.getNumber()), record.getData());
		}
		return rows;
	}
	
	static String[] mapCsvLine(String firstElement, CsvLine csvLine)
	{
		List<String> values = csvLine.getValues();
		
		String[] row = new String[values.size() + 1];
		row[0] = firstElement;
		for (int i = 0; i < values.size(); i++)
		{
			row[i + 1] = values.get(i);
		}
		return row;
	}
	
}
