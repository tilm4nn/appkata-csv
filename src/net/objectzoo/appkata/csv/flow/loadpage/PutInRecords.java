package net.objectzoo.appkata.csv.flow.loadpage;

import java.util.ArrayList;
import java.util.List;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.ProcessAndResultBase;

public class PutInRecords extends
	ProcessAndResultBase<Pair<Integer, List<CsvLine>>, List<CsvRecord>>
{
	
	@Override
	protected void process(Pair<Integer, List<CsvLine>> firstRecordNrAndLines)
	{
		int firstRecordNumber = firstRecordNrAndLines.getItem1();
		List<CsvLine> lines = firstRecordNrAndLines.getItem2();
		
		List<CsvRecord> records = new ArrayList<CsvRecord>(lines.size());
		for (CsvLine line : lines)
		{
			records.add(new CsvRecord(firstRecordNumber++, line));
		}
		sendResult(records);
	}
	
}
