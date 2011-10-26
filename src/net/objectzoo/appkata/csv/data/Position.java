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
package net.objectzoo.appkata.csv.data;

public class Position
{
	private final int currentPosition;
	
	private final int maxPosition;
	
	private final boolean maxCertain;
	
	public int getCurrentPosition()
	{
		return currentPosition;
	}
	
	public int getMaxPosition()
	{
		return maxPosition;
	}
	
	public boolean isMaxCertain()
	{
		return maxCertain;
	}
	
	public Position(int currentPosition, int maxPosition, boolean maxCertain)
	{
		super();
		this.currentPosition = currentPosition;
		this.maxPosition = maxPosition;
		this.maxCertain = maxCertain;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPosition;
		result = prime * result + (maxCertain ? 1231 : 1237);
		result = prime * result + maxPosition;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Position other = (Position) obj;
		if (currentPosition != other.currentPosition) return false;
		if (maxCertain != other.maxCertain) return false;
		if (maxPosition != other.maxPosition) return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "Position [currentPosition=" + currentPosition + ", maxPosition=" + maxPosition
			+ ", maxCertain=" + maxCertain + "]";
	}
}
