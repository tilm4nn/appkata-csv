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

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.data.displaypage.PageViewModel;
import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.join.JoinToPair;
import net.objectzoo.ebc.util.Pair;
import net.objectzoo.events.Event0;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

public class DisplayPageBoard
{
	private Action<Pair<Page, Position>> process;
	
	private Event0 signal;
	
	public DisplayPageBoard(MapToPageViewModel mapToPageViewModel, DetermineColumnLengths determineColumnLengths,
							RenderPageViewModel renderPageViewModel, DisplayPageViewModel displayPageViewModel)
	{
		EventDelegate<PageViewModel> split = new EventDistributor<PageViewModel>();
		JoinToPair<PageViewModel, int[]> join = new JoinToPair<PageViewModel, int[]>(true);
		
		process = mapToPageViewModel.processAction();
		mapToPageViewModel.resultEvent().subscribe(split);
		split.subscribe(determineColumnLengths.processAction());
		split.subscribe(join.input1Action());
		determineColumnLengths.resultEvent().subscribe(join.input2Action());
		join.resultEvent().subscribe(renderPageViewModel.processAction());
		renderPageViewModel.resultEvent().subscribe(displayPageViewModel.processAction());
		signal = displayPageViewModel.signalEvent();
	}
	
	public Action<Pair<Page, Position>> getProcess()
	{
		return process;
	}
	
	public Event0 getSingal()
	{
		return signal;
	}
}
