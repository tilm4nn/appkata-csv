package net.objectzoo.appkata.csv.flow;

import java.util.List;

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.ProcessAndResultBase;

public class SelectPage extends ProcessAndResultBase<List<Page>, Pair<Page, Position>>
{
	
	List<Page> pages;
	
	int currentPage;
	
	private final Action0 nextPage = new Action0()
	{
		@Override
		public void invoke()
		{
			if (currentPage + 1 < pages.size())
			{
				currentPage++;
			}
			sendCurrentPage();
		}
	};
	
	private final Action0 previousPage = new Action0()
	{
		@Override
		public void invoke()
		{
			if (currentPage > 0)
			{
				currentPage--;
			}
			sendCurrentPage();
		}
	};
	
	private final Action0 firstPage = new Action0()
	{
		@Override
		public void invoke()
		{
			currentPage = 0;
			sendCurrentPage();
		}
	};
	
	private final Action0 lastPage = new Action0()
	{
		@Override
		public void invoke()
		{
			currentPage = pages.size() - 1;
			sendCurrentPage();
		}
	};
	
	private final Action<Integer> jumpToPage = new Action<Integer>()
	{
		@Override
		public void invoke(Integer pageNumber)
		{
			if (pageNumber >= 1 && pageNumber <= pages.size())
			{
				currentPage = pageNumber - 1;
				sendCurrentPage();
			}
		}
	};
	
	@Override
	protected void process(List<Page> pages)
	{
		this.pages = pages;
		this.currentPage = 0;
		
		sendCurrentPage();
	}
	
	private void sendCurrentPage()
	{
		sendResult(new Pair<Page, Position>(pages.get(currentPage), new Position(currentPage + 1,
			pages.size())));
	}
	
	public Action0 getNextPage()
	{
		return nextPage;
	}
	
	public Action0 getPreviousPage()
	{
		return previousPage;
	}
	
	public Action0 getFirstPage()
	{
		return firstPage;
	}
	
	public Action0 getLastPage()
	{
		return lastPage;
	}
	
	public Action<Integer> getJumpToPage()
	{
		return jumpToPage;
	}
	
}
