package net.objectzoo.appkata.csv.flow.loadpage;

import net.objectzoo.delegates.Action;
import net.objectzoo.ebc.ProcessAndResultBase;

public class ComputeRecordNumber extends ProcessAndResultBase<Integer, Integer>
{
	
	int pageSize;
	
	private final Action<Integer> setPageSize = new Action<Integer>()
	{
		@Override
		public void invoke(Integer input)
		{
			pageSize = input;
		}
	};
	
	public Action<Integer> getSetPageSize()
	{
		return setPageSize;
	}
	
	@Override
	protected void process(Integer pageNr)
	{
		sendResult(pageSize * (pageNr - 1) + 1);
	}
	
}
