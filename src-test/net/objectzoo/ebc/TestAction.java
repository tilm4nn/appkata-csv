package net.objectzoo.ebc;

import static junit.framework.Assert.fail;

import net.objectzoo.delegates.Action;

public class TestAction<ResultType> implements Action<ResultType>
{
	private boolean invoked;
	
	private ResultType result;
	
	@Override
	public synchronized void invoke(ResultType result)
	{
		if (invoked)
		{
			fail("Unexpected invocation with " + result
				+ ". This result action has already been invoked with " + result + ".");
		}
		this.result = result;
		this.invoked = true;
	}
	
	public boolean isInvoked()
	{
		return invoked;
	}
	
	public ResultType getResult()
	{
		assertInvoked();
		return result;
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
