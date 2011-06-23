package net.objectzoo.ebc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class JoinCollections<Input1Element, Input2Element, OutputElement>
	extends
	JoinBase<Collection<Input1Element>, Collection<Input2Element>, List<OutputElement>, OutputElement>
{
	public JoinCollections()
	{
		super(null);
	}
	
	public JoinCollections(Class<? extends OutputElement> outputType)
	{
		super(outputType);
	}
	
	@Override
	protected List<OutputElement> createOutput(Collection<Input1Element> lastInput1,
											   Collection<Input2Element> lastInput2)
	{
		List<OutputElement> output = new ArrayList<OutputElement>(Math.max(lastInput1.size(),
			lastInput2.size()));
		
		Iterator<Input1Element> input1Iter = lastInput1.iterator();
		Iterator<Input2Element> input2Iter = lastInput2.iterator();
		
		while (input1Iter.hasNext() && input2Iter.hasNext())
		{
			output.add(createOutputElement(input1Iter.next(), input2Iter.next()));
		}
		
		return output;
	}
}
