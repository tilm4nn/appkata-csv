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

public class Pair<T1, T2>
{
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item1 == null) ? 0 : item1.hashCode());
		result = prime * result + ((item2 == null) ? 0 : item2.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (item1 == null)
		{
			if (other.item1 != null) return false;
		}
		else if (!item1.equals(other.item1)) return false;
		if (item2 == null)
		{
			if (other.item2 != null) return false;
		}
		else if (!item2.equals(other.item2)) return false;
		return true;
	}
	
	private T1 item1;
	
	private T2 item2;
	
	public Pair()
	{
		this(null, null);
	}
	
	public Pair(T1 item1, T2 item2)
	{
		this.item1 = item1;
		this.item2 = item2;
	}
	
	public T1 getItem1()
	{
		return item1;
	}
	
	public void setItem1(T1 item1)
	{
		this.item1 = item1;
	}
	
	public T2 getItem2()
	{
		return item2;
	}
	
	public void setItem2(T2 item2)
	{
		this.item2 = item2;
	}
	
	@Override
	public String toString()
	{
		return "Pair [item1=" + item1 + ", item2=" + item2 + "]";
	}
}
