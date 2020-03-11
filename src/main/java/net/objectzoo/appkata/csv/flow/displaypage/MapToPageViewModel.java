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
package net.objectzoo.appkata.csv.flow.displaypage;

import java.util.List;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.data.displaypage.PageViewModel;
import net.objectzoo.ebc.impl.ProcessAndResultBase;
import net.objectzoo.ebc.util.Pair;

class MapToPageViewModel extends ProcessAndResultBase<Pair<Page, Position>, PageViewModel>
{
	@Override
	protected void process(Pair<Page, Position> pageAndPosition)
	{
		Page page = pageAndPosition.getItem1();
		Position position = pageAndPosition.getItem2();
		
		String[] headers = mapCsvLine("No.", page.getHeader());
		String[][] rows = mapRecords(page.getRecords());
		
		sendResult(new PageViewModel(headers, rows, position.getCurrentPosition(),
			position.getMaxPosition(), position.isMaxCertain()));
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
