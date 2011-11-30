package net.objectzoo.appkata.csv.flow;

import java.util.ArrayList;
import java.util.List;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.ebc.impl.ProcessAndResultBase;

public class PutInRecords extends ProcessAndResultBase<List<CsvLine>, List<CsvRecord>>
{
	
	@Override
	protected void process(List<CsvLine> csvLines)
	{
		List<CsvRecord> records = new ArrayList<CsvRecord>(csvLines.size());
		for (int i = 0; i < csvLines.size(); i++)
		{
			records.add(new CsvRecord(i + 1, csvLines.get(i)));
		}
		sendResult(records);
	}
	
}
