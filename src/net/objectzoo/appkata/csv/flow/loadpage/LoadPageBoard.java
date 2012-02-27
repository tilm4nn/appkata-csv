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
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.CanProcess;
import net.objectzoo.ebc.SendsResult;
import net.objectzoo.ebc.join.GenericJoin;
import net.objectzoo.ebc.join.Join;
import net.objectzoo.ebc.join.JoinToPair;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

/**
 * <img src="LoadPageBoard.jpg" />
 * 
 * @author tilmann
 */
public class LoadPageBoard implements CanProcess<Integer>, SendsResult<Page>
{
	private final EventDelegate<Integer> processDelegate;
	
	private final Event<Page> resultEvent;
	
	private final EventDelegate<Integer> initPageSizeDelegate;
	
	private final Action<String> initFilenameAction;
	
	@Inject
	public LoadPageBoard(ComputeRecordNumber computeRecordNumber,
						 LookUpPageOffset lookupPageOffset, ReadPageLines readPageLines,
						 SplitLines splitLines, SeparateHeaderAndData separateHeaderAndData,
						 PutInRecords putInRecords)
	{
		JoinToPair<Integer, List<CsvLine>> joinPageNumAndLines = new JoinToPair<Integer, List<CsvLine>>(
			true);
		Join<CsvLine, List<CsvRecord>, Page> joinToPage = new GenericJoin<CsvLine, List<CsvRecord>, Page>(
			true)
		{
		};
		processDelegate = new EventDistributor<Integer>();
		initPageSizeDelegate = new EventDistributor<Integer>();
		initFilenameAction = readPageLines.initFilenameAction();
		
		processDelegate.subscribe(computeRecordNumber.processAction());
		processDelegate.subscribe(lookupPageOffset.processAction());
		initPageSizeDelegate.subscribe(readPageLines.initPageSizeAction());
		initPageSizeDelegate.subscribe(computeRecordNumber.initPageSizeAction());
		lookupPageOffset.resultEvent().subscribe(readPageLines.processAction());
		readPageLines.resultEvent().subscribe(splitLines.processAction());
		splitLines.resultEvent().subscribe(separateHeaderAndData.processAction());
		computeRecordNumber.resultEvent().subscribe(joinPageNumAndLines.input1Action());
		separateHeaderAndData.newDataEvent().subscribe(joinPageNumAndLines.input2Action());
		joinPageNumAndLines.resultEvent().subscribe(putInRecords.processAction());
		separateHeaderAndData.newHeaderEvent().subscribe(joinToPage.input1Action());
		putInRecords.resultEvent().subscribe(joinToPage.input2Action());
		
		resultEvent = joinToPage.resultEvent();
	}
	
	@Override
	public Action<Integer> processAction()
	{
		return processDelegate;
	}
	
	@Override
	public Event<Page> resultEvent()
	{
		return resultEvent;
	}
	
	public Action<Integer> initPageSizeAction()
	{
		return initPageSizeDelegate;
	}
	
	public Action<String> initFilenameAction()
	{
		return initFilenameAction;
	}
}
