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

import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.data.Progress;
import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.impl.ProcessAndResultBase;
import net.objectzoo.events.Event;
import net.objectzoo.events.impl.EventDelegate;
import net.objectzoo.events.impl.EventDistributor;

class SelectPage extends ProcessAndResultBase<Progress, Position>
{
	int currentPage;
	
	int lastPage;
	
	boolean lastPageKnown;
	
	private final EventDelegate<Integer> selectedPageNumber = new EventDistributor<Integer>();
	
	public Event<Integer> getSelectedPageNumber()
	{
		return selectedPageNumber;
	}
	
	private final Action0 nextPageAction = new Action0()
	{
		@Override
		public void invoke()
		{
			if (currentPage + 1 <= lastPage)
			{
				currentPage++;
			}
			sendCurrentPage();
		}
	};
	
	private final Action0 previousPageAction = new Action0()
	{
		@Override
		public void invoke()
		{
			if (currentPage > 1)
			{
				currentPage--;
			}
			sendCurrentPage();
		}
	};
	
	private final Action0 firstPageAction = new Action0()
	{
		@Override
		public void invoke()
		{
			currentPage = 1;
			sendCurrentPage();
		}
	};
	
	private final Action0 lastPageAction = new Action0()
	{
		@Override
		public void invoke()
		{
			currentPage = lastPage;
			sendCurrentPage();
		}
	};
	
	private final Action<Integer> jumpToPageAction = new Action<Integer>()
	{
		@Override
		public void invoke(Integer pageNumber)
		{
			if (pageNumber >= 1 && pageNumber <= lastPage)
			{
				currentPage = pageNumber;
				sendCurrentPage();
			}
		}
	};
	
	@Override
	protected void process(Progress progress)
	{
		lastPage = progress.getFinishedPages();
		lastPageKnown = progress.isComplete();
		
		if (currentPage == 0 && lastPage > 0)
		{
			currentPage = 1;
			sendCurrentPage();
		}
	}
	
	private void sendCurrentPage()
	{
		selectedPageNumber.invoke(currentPage);
		
		sendResult(new Position(currentPage, lastPage, lastPageKnown));
	}
	
	public Action0 nextPageAction()
	{
		return nextPageAction;
	}
	
	public Action0 previousPageAction()
	{
		return previousPageAction;
	}
	
	public Action0 firstPageAction()
	{
		return firstPageAction;
	}
	
	public Action0 lastPageAction()
	{
		return lastPageAction;
	}
	
	public Action<Integer> jumpToPageAction()
	{
		return jumpToPageAction;
	}
}
