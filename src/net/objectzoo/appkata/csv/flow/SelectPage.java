package net.objectzoo.appkata.csv.flow;

import java.util.List;

import net.objectzoo.appkata.csv.data.Page;
import net.objectzoo.ebc.ProcessAndResultBase;

public class SelectPage extends ProcessAndResultBase<List<Page>, Page>
{
	
	@Override
	protected void process(List<Page> pages)
	{
		sendResult(pages.get(0));
	}
	
}
