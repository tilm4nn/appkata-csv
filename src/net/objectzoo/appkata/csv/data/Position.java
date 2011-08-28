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
