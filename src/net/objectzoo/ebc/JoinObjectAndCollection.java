package net.objectzoo.ebc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class JoinObjectAndCollection<Input1, Input2Element, OutputElement> extends
	JoinBase<Input1, Collection<Input2Element>, List<OutputElement>, OutputElement>
{
	public JoinObjectAndCollection()
	{
		super(null);
	}
	
	public JoinObjectAndCollection(Class<? extends OutputElement> outputType)
	{
		super(outputType);
	}
	
	@Override
	protected List<OutputElement> createOutput(Input1 lastInput1,
											   Collection<Input2Element> lastInput2)
	{
		List<OutputElement> output = new ArrayList<OutputElement>(lastInput2.size());
		for (Input2Element input2Element : lastInput2)
		{
			output.add(createOutputElement(lastInput1, input2Element));
		}
		return output;
	}
}
