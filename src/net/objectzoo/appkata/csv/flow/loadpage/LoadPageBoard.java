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

import java.util.List;

import com.google.inject.Inject;

import net.objectzoo.appkata.csv.data.CsvLine;
import net.objectzoo.appkata.csv.data.CsvRecord;
import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.flow.SeparateHeaderAndData;
import net.objectzoo.appkata.csv.flow.SplitLines;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.Join;
import net.objectzoo.ebc.JoinToPair;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDistributor;

public class LoadPageBoard
{
	private final Action<Integer> process;
	
	private final Event<Page> result;
	
	private final Action<Integer> setPageSize;
	
	private final Action<String> setFilename;
	
	@Inject
	public LoadPageBoard(ComputeRecordNumber computeRecordNumber,
						 LookUpPageOffset lookupPageOffset, ReadPageLines readPageLines,
						 SplitLines splitLines, SeparateHeaderAndData separateHeaderAndData,
						 PutInRecords putInRecords)
	{
		EventDistributor<Integer> splitPageNumber = new EventDistributor<Integer>();
		EventDistributor<Integer> splitPageSize = new EventDistributor<Integer>();
		JoinToPair<Integer, List<CsvLine>> joinPageNumAndLines = new JoinToPair<Integer, List<CsvLine>>()
		{
		};
		Join<CsvLine, List<CsvRecord>, Page> joinToPage = new Join<CsvLine, List<CsvRecord>, Page>()
		{
		};
		
		process = splitPageNumber;
		setPageSize = splitPageSize;
		setFilename = readPageLines.getSetFilename();
		splitPageNumber.subscribe(computeRecordNumber.getProcess());
		splitPageNumber.subscribe(lookupPageOffset.getProcess());
		splitPageSize.subscribe(readPageLines.getSetPageSize());
		splitPageSize.subscribe(computeRecordNumber.getSetPageSize());
		lookupPageOffset.getResult().subscribe(readPageLines.getProcess());
		readPageLines.getResult().subscribe(splitLines.getProcess());
		splitLines.getResult().subscribe(separateHeaderAndData.getProcess());
		computeRecordNumber.getResult().subscribe(joinPageNumAndLines.getInput1());
		separateHeaderAndData.getNewData().subscribe(joinPageNumAndLines.getInput2());
		joinPageNumAndLines.getResult().subscribe(putInRecords.getProcess());
		separateHeaderAndData.getNewHeader().subscribe(joinToPage.getInput1());
		putInRecords.getResult().subscribe(joinToPage.getInput2());
		result = joinToPage.getResult();
		
	}
	
	public Action<Integer> getProcess()
	{
		return process;
	}
	
	public Event<Page> getResult()
	{
		return result;
	}
	
	public Action<Integer> getSetPageSize()
	{
		return setPageSize;
	}
	
	public Action<String> getSetFilename()
	{
		return setFilename;
	}
}
