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

import static net.objectzoo.ebc.builder.Flow.await;

import com.google.inject.Inject;

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageBoard;
import net.objectzoo.appkata.csv.flow.loadpage.LoadPageBoard;
import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.adapters.Action0ToAsyncAction0;
import net.objectzoo.ebc.EntryPoint;
import net.objectzoo.ebc.join.JoinToPair;

/**
 * <img src="MainBorad-IndexPageOffsetsAndDisplayFirstPage.jpg" />
 * 
 * <img src="MainBorad-NextPage.jpg" />
 * 
 * @author tilmann
 */
public class MainBoard implements EntryPoint
{
	private final Action0 startAction;
	
	@Inject
	public MainBoard(RepeatedWaitForCommand repeatedWaitForCommand,
					 DeterminePageSize determinePageSize, DetermineFilename determineFilename,
					 DeterminePageOffsets determinePageOffsets,
					 StoreOffsetInIndex storeOffsetInIndex, SelectPage selectPage,
					 LoadPageBoard loadPage, InputPageNumber inputPageNumber,
					 DisplayPageBoard displayPage, DisplayCommands displayCommands)
	{
		JoinToPair<Page, Position> join = new JoinToPair<Page, Position>(true);
		startAction = repeatedWaitForCommand.startAction();
		
		await(determineFilename).then(determinePageOffsets.initFilenameAction());
		await(determineFilename).then(loadPage.initFilenameAction());
		
		await(determinePageSize).then(determinePageOffsets.initPageSizeAction());
		await(determinePageSize).then(loadPage.initPageSizeAction());
		
		await(repeatedWaitForCommand).then(
			new Action0ToAsyncAction0(determinePageOffsets.startAction()));
		await(determinePageOffsets.newPageOffsetEvent()).then(storeOffsetInIndex);
		await(determinePageOffsets).then(selectPage).then(join.input2Action());
		
		await(selectPage.getSelectedPageNumber()).then(loadPage).then(join.input1Action());
		await(join).then(displayPage).then(displayCommands);
		
		await(repeatedWaitForCommand.nextPageCommandEvent()).then(selectPage.nextPageAction());
		await(repeatedWaitForCommand.previousPageCommandEvent()).then(
			selectPage.previousPageAction());
		await(repeatedWaitForCommand.firstPageCommandEvent()).then(selectPage.firstPageAction());
		await(repeatedWaitForCommand.lastPageCommandEvent()).then(selectPage.lastPageAction());
		await(repeatedWaitForCommand.jumpToPageCommandEvent()).then(inputPageNumber).then(
			selectPage.jumpToPageAction());
	}
	
	@Override
	public Action0 startAction()
	{
		return startAction;
	}
}
