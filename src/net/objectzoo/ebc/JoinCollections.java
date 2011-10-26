/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:info@object-zoo.net
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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
