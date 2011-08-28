package net.objectzoo.appkata.csv.data;

public class Progress
{
	private final int finishedPages;
	
	private final boolean complete;
	
	public Progress(int finishedPages, boolean complete)
	{
		this.finishedPages = finishedPages;
		this.complete = complete;
	}
	
	public int getFinishedPages()
	{
		return finishedPages;
	}
	
	public boolean isComplete()
	{
		return complete;
	}
	
	@Override
	public String toString()
	{
		return "Progress [finishedPages=" + finishedPages + ", complete=" + complete + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result + finishedPages;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Progress other = (Progress) obj;
		if (complete != other.complete) return false;
		if (finishedPages != other.finishedPages) return false;
		return true;
	}
	
}
