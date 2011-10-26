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

import static junit.framework.Assert.fail;

import java.util.LinkedList;
import java.util.List;

import net.objectzoo.delegates.Action;

public class TestAction<ResultType> implements Action<ResultType>
{
	private boolean invoked;
	
	private boolean multipleInvokationAllowed;
	
	private List<ResultType> results = new LinkedList<ResultType>();
	
	public TestAction()
	{
		this(false);
	}
	
	public TestAction(boolean multipleInvokationAllowed)
	{
		this.multipleInvokationAllowed = multipleInvokationAllowed;
	}
	
	@Override
	public synchronized void invoke(ResultType result)
	{
		if (invoked && !multipleInvokationAllowed)
		{
			fail("Unexpected invocation with " + result
				+ ". This result action has already been invoked with " + result + ".");
		}
		
		invoked = true;
		results.add(result);
	}
	
	public boolean isInvoked()
	{
		return invoked;
	}
	
	public ResultType getLastResult()
	{
		assertInvoked();
		return results.get(results.size() - 1);
	}
	
	public ResultType getFirstResult()
	{
		assertInvoked();
		return results.get(0);
	}
	
	public List<ResultType> getResults()
	{
		return results;
	}
	
	public void assertInvoked()
	{
		assertInvoked("This result action has not been invoked like expected.");
	}
	
	public void assertInvoked(String message)
	{
		if (!invoked)
		{
			fail(message);
		}
	}
}
