package net.objectzoo.appkata.csv.flow;

import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.appkata.csv.data.Progress;
import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.ProcessAndResultBase;
import net.objectzoo.events.impl.EventDistributor;

public class SelectPage extends ProcessAndResultBase<Progress, Position>
{
	int currentPage;
	
	int lastPage;
	
	boolean lastPageKnown;
	
	private final EventDistributor<Integer> selectedPageNumber = new EventDistributor<Integer>();
	
	public EventDistributor<Integer> getSelectedPageNumber()
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
	
	public Action0 getNextPage()
	{
		return nextPageAction;
	}
	
	public Action0 getPreviousPage()
	{
		return previousPageAction;
	}
	
	public Action0 getFirstPage()
	{
		return firstPageAction;
	}
	
	public Action0 getLastPage()
	{
		return lastPageAction;
	}
	
	public Action<Integer> getJumpToPage()
	{
		return jumpToPageAction;
	}
	
}
