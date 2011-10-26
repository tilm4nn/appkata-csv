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

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

import net.objectzoo.delegates.Action;

abstract class JoinBase<Input1, Input2, Output, OutputElement> extends ResultBase<Output>
{
	private final Constructor<? extends OutputElement> outputElementConstructor;
	
	protected JoinBase()
	{
		this(null);
	}
	
	protected JoinBase(Class<?> outputElementType)
	{
		outputElementConstructor = findOutputElementConstructor(outputElementType);
	}
	
	protected OutputElement createOutputElement(Object input1, Object input2)
	{
		try
		{
			return outputElementConstructor.newInstance(input1, input2);
		}
		catch (IllegalArgumentException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
		catch (InstantiationException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
		catch (InvocationTargetException e)
		{
			throw new IllegalArgumentException("Could not create output object using constructor "
				+ outputElementConstructor, e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private Constructor<? extends OutputElement> findOutputElementConstructor(Class<?> outputElementType)
	{
		ParameterizedType genericType = getGenericType();
		
		Class<?> input1Type = getTypeArgument(genericType, 0);
		Class<?> input2Type = getTypeArgument(genericType, 1);
		
		if (outputElementType == null)
		{
			outputElementType = (Class<? extends OutputElement>) getTypeArgument(genericType, 2);
		}
		
		for (Constructor<?> constructor : outputElementType.getConstructors())
		{
			Class<?>[] parameterTypes = constructor.getParameterTypes();
			if (parameterTypes.length == 2 && parameterTypes[0].isAssignableFrom(input1Type)
				&& parameterTypes[1].isAssignableFrom(input2Type))
			{
				constructor.setAccessible(true);
				return (Constructor<? extends OutputElement>) constructor;
			}
		}
		throw new IllegalArgumentException("The output type " + outputElementType.getName()
			+ " does not have a constructor taking " + input1Type.getName() + " and "
			+ input2Type.getName() + " as arguments.");
	}
	
	private ParameterizedType getGenericType()
	{
		Class<?> clazz = getClass();
		Type genericSuperclass = clazz.getGenericSuperclass();
		if (!(genericSuperclass instanceof ParameterizedType))
		{
			throw new IllegalArgumentException(
				"The join cannot be used as raw type. Please give the type parameters.");
		}
		return (ParameterizedType) genericSuperclass;
	}
	
	public static Class<?> getRawType(Type type)
	{
		if (type instanceof Class<?>)
		{
			return (Class<?>) type;
		}
		if (type instanceof ParameterizedType)
		{
			return (Class<?>) ((ParameterizedType) type).getRawType();
		}
		if (type instanceof GenericArrayType)
		{
			Class<?> elementType = getRawType(((GenericArrayType) type).getGenericComponentType());
			return Array.newInstance(elementType, 0).getClass();
		}
		if (type instanceof TypeVariable)
		{
			return getRawType(((TypeVariable<?>) type).getBounds()[0]);
		}
		if (type instanceof WildcardType)
		{
			return getRawType(((WildcardType) type).getUpperBounds()[0]);
		}
		throw new IllegalArgumentException("The type " + type
			+ " given as type parameter cannot be converted into a valid Java Class.");
	}
	
	private static Class<?> getTypeArgument(ParameterizedType genericType, int numberOfArgument)
	{
		Type typeArgument = genericType.getActualTypeArguments()[numberOfArgument];
		
		return getRawType(typeArgument);
	}
	
	private Input1 lastInput1;
	
	private Input2 lastInput2;
	
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
	
	public Action<Input1> getInput1()
	{
		return input1;
	}
	
	public Action<Input2> getInput2()
	{
		return input2;
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
		Output output = createOutput(lastInput1, lastInput2);
		sendResult(output);
	}
	
	protected abstract Output createOutput(Input1 lastInput1, Input2 lastInput2);
}
