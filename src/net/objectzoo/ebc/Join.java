/*
 * The MIT License
 * 
 * Copyright (C) 2011 Tilmann Kuhn
 * 
 * http://www.object-zoo.net
 * 
 * mailto:ebc4j@object-zoo.net
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import net.objectzoo.delegates.Action;

public abstract class Join<Input1, Input2, Output> extends ResultBase<Output>
{
	private Input1 lastInput1;
	
	private Input2 lastInput2;
	
	private final Constructor<? extends Output> outputConstructor;
	
	private final Action<Input1> input1 = new Action<Input1>()
	{
		@Override
		public void invoke(Input1 input)
		{
			log.finer("receiving input1");
			
			lastInput1 = input;
			sendResultIfComplete();
		}
	};
	
	private final Action<Input2> input2 = new Action<Input2>()
	{
		@Override
		public void invoke(Input2 input)
		{
			log.finer("receiving input2");
			
			lastInput2 = input;
			sendResultIfComplete();
		}
	};
	
	public Join()
	{
		this(null);
	}
	
	public Join(Class<?> outputType)
	{
		outputConstructor = findOutputConstructor(outputType);
	}
	
	private void sendResultIfComplete()
	{
		if (lastInput1 != null && lastInput2 != null)
		{
			createAndSendOutput();
		}
	}
	
	private void createAndSendOutput()
	{
		Output output = createOutput();
		sendResult(output);
	}
	
	private Output createOutput()
	{
		try
		{
			return outputConstructor.newInstance(lastInput1, lastInput2);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
		catch (InstantiationException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
		catch (InvocationTargetException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputConstructor, e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private Constructor<? extends Output> findOutputConstructor(Class<?> outputType)
	{
		ParameterizedType genericType = getGenericType();
		
		Class<?> input1Type = getTypeArgument(genericType, 0);
		Class<?> input2Type = getTypeArgument(genericType, 1);
		
		if (outputType == null)
		{
			outputType = (Class<? extends Output>) getTypeArgument(genericType, 2);
		}
		else
		{
			if (!getTypeArgument(genericType, 2).isAssignableFrom(outputType))
			{
				throw new IllegalArgumentException("The selected output type "
					+ outputType.getName()
					+ " is not assignable to the type of the ouput type parameter.");
			}
		}
		
		for (Constructor<?> constructor : outputType.getConstructors())
		{
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			if (parameterTypes.length == 2 && parameterTypes[0].isAssignableFrom(input1Type)
				&& parameterTypes[1].isAssignableFrom(input2Type))
			{
				constructor.setAccessible(true);
				return (Constructor<? extends Output>) constructor;
			}
		}
		throw new IllegalArgumentException("The output type " + outputType.getName()
			+ " does not have a constructor taking " + input1Type.getName() + " and "
			+ input2Type.getName() + " as arguments.");
	}
	
	private ParameterizedType getGenericType()
	{
		Class<?> clazz = getClass();
		while (!Join.class.equals(clazz.getSuperclass()))
		{
			clazz = clazz.getSuperclass();
		}
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (!(genericSuperclass instanceof ParameterizedType))
		{
			throw new IllegalArgumentException(
				"The join cannot be used as raw type. Please give the type parameters.");
		}
		return (ParameterizedType) genericSuperclass;
	}
	
	private static Class<?> getTypeArgument(ParameterizedType genericType, int numberOfArgument)
	{
		Type typeArgument = genericType.getActualTypeArguments()[numberOfArgument];
		if (typeArgument instanceof TypeVariable)
		{
			typeArgument = ((TypeVariable<?>) typeArgument).getBounds()[0];
		}
		if (typeArgument instanceof Class)
		{
			return (Class<?>) typeArgument;
		}
		if (typeArgument instanceof ParameterizedType)
		{
			return (Class<?>) ((ParameterizedType) typeArgument).getRawType();
		}
		throw new IllegalArgumentException("The type " + typeArgument
			+ " given as type parameter is not a valid Java Class.");
	}
	
	public Action<Input1> getInput1()
	{
		return input1;
	}
	
	public Action<Input2> getInput2()
	{
		return input2;
	}
	
}
