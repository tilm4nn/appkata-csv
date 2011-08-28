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
