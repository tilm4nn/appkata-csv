package net.objectzoo.ebc;

import static junit.framework.Assert.fail;

import net.objectzoo.delegates.Action0;

public class TestAction0 implements Action0
{
	private boolean invoked;
	
	@Override
	public synchronized void invoke()
	{
		if (invoked)
		{
			fail("Unexpected invocation. This result action has already been invoked.");
		}
		this.invoked = true;
	}
	
	public boolean isInvoked()
	{
		return invoked;
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
