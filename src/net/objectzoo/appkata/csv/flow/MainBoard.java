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
package net.objectzoo.appkata.csv.flow;

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageBoard;
import net.objectzoo.appkata.csv.flow.loadpage.LoadPageBoard;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.Action0ToAsyncAction0;
import net.objectzoo.ebc.EntryPoint;
import net.objectzoo.ebc.JoinToPair;

public class MainBoard implements EntryPoint
{
	private final Action0 start;
	
	public MainBoard(RepeatedWaitForCommand repeatedWaitForCommand,
					 DeterminePageSize determinePageSize, DetermineFilename determineFilename,
					 DeterminePageOffsets determinePageOffsets,
					 StoreOffsetInIndex storeOffsetInIndex, SelectPage selectPage,
					 LoadPageBoard loadPage, InputPageNumber inputPageNumber,
					 DisplayPageBoard displayPage, DisplayCommands displayCommands)
	{
		JoinToPair<Page, Position> join = new JoinToPair<Page, Position>()
		{
		};
		
		start = repeatedWaitForCommand.getStart();
		repeatedWaitForCommand.getSignal().subscribe(
			new Action0ToAsyncAction0(determinePageOffsets.getStart()));
		determineFilename.getResult().subscribe(determinePageOffsets.getSetFilename());
		determineFilename.getResult().subscribe(loadPage.getSetFilename());
		determinePageSize.getResult().subscribe(determinePageOffsets.getSetPageSize());
		determinePageSize.getResult().subscribe(loadPage.getSetPageSize());
		determinePageOffsets.getNewPageOffset().subscribe(storeOffsetInIndex.getProcess());
		determinePageOffsets.getResult().subscribe(selectPage.getProcess());
		selectPage.getSelectedPageNumber().subscribe(loadPage.getProcess());
		loadPage.getResult().subscribe(join.getInput1());
		selectPage.getResult().subscribe(join.getInput2());
		join.getResult().subscribe(displayPage.getProcess());
		displayPage.getSingal().subscribe(displayCommands.getStart());
		
		repeatedWaitForCommand.getNextPageCommand().subscribe(selectPage.getNextPage());
		repeatedWaitForCommand.getPreviousPageCommand().subscribe(selectPage.getPreviousPage());
		repeatedWaitForCommand.getFirstPageCommand().subscribe(selectPage.getFirstPage());
		repeatedWaitForCommand.getLastPageCommand().subscribe(selectPage.getLastPage());
		repeatedWaitForCommand.getJumpToPageCommand().subscribe(inputPageNumber.getStart());
		inputPageNumber.getResult().subscribe(selectPage.getJumpToPage());
	}
	
	@Override
	public Action0 getStart()
	{
		return start;
	}
}
