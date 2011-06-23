package net.objectzoo.ebc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

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
