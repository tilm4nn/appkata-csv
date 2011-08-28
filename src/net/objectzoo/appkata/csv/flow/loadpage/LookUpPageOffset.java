package net.objectzoo.appkata.csv.flow.loadpage;

import net.objectzoo.appkata.csv.dependencies.IndexContract;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.ProcessAndResultBase;

public class LookUpPageOffset extends ProcessAndResultBase<Integer, Long> implements
	DependsOn<IndexContract>
{
	private IndexContract index;
	
	@Override
	public void inject(IndexContract dependency)
	{
		index = dependency;
	}
	
	@Override
	protected void process(Integer pageNr)
	{
		sendResult(index.retrieveOffset(pageNr));
	}
	
}
