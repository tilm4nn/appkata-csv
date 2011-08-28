package net.objectzoo.ebc;

import java.util.concurrent.Executor;

import net.objectzoo.delegates.Action;
import net.objectzoo.delegates.Action0;
import net.objectzoo.delegates.ActionAsync;
import net.objectzoo.delegates.adapters.ActionToActionAsync;
import net.objectzoo.delegates.helpers.AsyncExecutor;

/**
 * An adapter that converts an {@link Action} to a new {@link Action}, that makes an asynchronous
 * calls to the original Action0 upon invocation. This adapter provides no possibility to check for
 * the completion of the original action nor to retrieve thrown exceptions. Thus it is recommended
 * to use {@link ActionToActionAsync} adapter instead to achieve a similar result.
 * 
 * All asynchronous calls are executed in another thread and forwarded to the
 * {@link Action0#invoke()} method.
 * 
 * The {@link Executor} to use for the asynchronous invocations can be chosen during creation of
 * this adapter. If no explicit executor is given the a default executor is used. The default
 * executor can be set using the {@link AsyncExecutor#setDefaultExecutor(Executor)} property or is
 * created automatically by the {@link AsyncExecutor}.
 * 
 * @author tilmann
 * 
 * @param <T>
 *        The type of the {@code Action}'s parameter
 */
public class ActionToAsyncAction<T> implements Action<T>
{
	private final ActionAsync<T> actionAsync;
	
	/**
	 * Makes the given {@link Action} asynchronous using the default executor
	 * 
	 * @param action
	 *        the action to be called asynchronously
	 */
	public ActionToAsyncAction(Action<T> action)
	{
		this(action, null);
	}
	
	/**
	 * Makes the given {@link Action} asynchronous using the given executor
	 * 
	 * @param action
	 *        the action to be called asynchronously
	 * @param executor
	 *        the executor used for the asynchronous calls
	 */
	public ActionToAsyncAction(Action<T> action, Executor executor)
	{
		actionAsync = new ActionToActionAsync<T>(action, executor);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void invoke(T parameter)
	{
		actionAsync.beginInvoke(null, null, parameter);
	}
}
