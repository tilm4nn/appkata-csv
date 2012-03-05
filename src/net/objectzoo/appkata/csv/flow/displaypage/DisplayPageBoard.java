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

import static net.objectzoo.ebc.builder.Flow.await;

import com.google.inject.Inject;

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.data.displaypage.PageViewModel;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.ProcessAndSignalFlow;
import net.objectzoo.ebc.join.JoinToPair;
import net.objectzoo.ebc.split.SplitProcess;
import net.objectzoo.ebc.util.Pair;
import net.objectzoo.events.Event0;

/**
 * <img src="DisplayPageBoard.jpg" />
 * 
 * @author tilmann
 */
public class DisplayPageBoard implements ProcessAndSignalFlow<Pair<Page, Position>>
{
	private final Action<Pair<Page, Position>> processAction;
	
	private final Event0 signalEvent;
	
	@Inject
	public DisplayPageBoard(MapToPageViewModel mapToPageViewModel,
							DetermineColumnLengths determineColumnLengths,
							RenderPageViewModel renderPageViewModel,
							DisplayPageViewModel displayPageViewModel)
	{
		JoinToPair<PageViewModel, int[]> join = new JoinToPair<PageViewModel, int[]>(true);
		processAction = mapToPageViewModel.processAction();
		
		SplitProcess<PageViewModel> split = await(mapToPageViewModel).thenSplit();
		
		await(split).then(join.input1Action());
		await(split).then(determineColumnLengths).then(join.input2Action());
		
		await(join).then(renderPageViewModel).then(displayPageViewModel);
		
		signalEvent = displayPageViewModel.signalEvent();
	}
	
	@Override
	public Action<Pair<Page, Position>> processAction()
	{
		return processAction;
	}
	
	@Override
	public Event0 signalEvent()
	{
		return signalEvent;
	}
}
