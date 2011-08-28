package net.objectzoo.appkata.csv.flow;

import net.objectzoo.appkata.csv.dependencies.IndexContract;
import net.objectzoo.ebc.DependsOn;
import net.objectzoo.ebc.Pair;
import net.objectzoo.ebc.ProcessBase;

public class StoreOffsetInIndex extends ProcessBase<Pair<Integer, Long>> implements
	DependsOn<IndexContract>
{
	
	private IndexContract index;
	
	@Override
	protected void process(Pair<Integer, Long> parameter)
	{
		index.storeOffset(parameter.getItem1(), parameter.getItem2());
	}
	
	@Override
	public void inject(IndexContract dependency)
	{
		log.finer("inject");
		
		index = dependency;
	}
	
}
