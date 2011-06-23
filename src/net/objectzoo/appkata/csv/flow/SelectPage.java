package net.objectzoo.appkata.csv.flow;

import java.util.List;

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.delegates.Action0;
import net.objectzoo.ebc.ProcessAndResultBase;

public class SelectPage extends ProcessAndResultBase<List<Page>, Page>
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
	
	@Override
	protected void process(List<Page> pages)
	{
		this.pages = pages;
		this.currentPage = 0;
		
		sendCurrentPage();
	}
	
	private void sendCurrentPage()
	{
		sendResult(pages.get(currentPage));
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
	
}
