package net.objectzoo.appkata.csv.flow;

import java.util.List;

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.appkata.csv.data.Position;
import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.impl.ProcessAndResultBase;
import net.objectzoo.ebc.util.Pair;

public class SelectPage extends ProcessAndResultBase<List<Page>, Pair<Page, Position>>
{
	List<Page> pages;
	
	int currentPage;
	
	private final Action0 nextPageAction = new Action0()
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
	
	private final Action0 previousPageAction = new Action0()
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
	
	private final Action0 firstPageAction = new Action0()
	{
		@Override
		public void invoke()
		{
			currentPage = 0;
			sendCurrentPage();
		}
	};
	
	private final Action0 lastPageAction = new Action0()
	{
		@Override
		public void invoke()
		{
			currentPage = pages.size() - 1;
			sendCurrentPage();
		}
	};
	
	private final Action<Integer> jumpToPageAction = new Action<Integer>()
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
		sendResult(new Pair<Page, Position>(pages.get(currentPage), new Position(currentPage + 1, pages.size())));
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
