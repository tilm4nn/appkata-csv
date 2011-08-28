package net.objectzoo.appkata.csv.data.displaypage;

import java.util.Arrays;

public class PageViewModel
{
	private final String[] header;
	
	private final String[][] rows;
	
	private final int currentPosition;
	
	private final int maxPosition;
	
	private final boolean maxCertain;
	
	public PageViewModel(String[] headers, String[][] rows, int currentPosition, int maxPosition,
						 boolean maxCertain)
	{
		this.header = headers;
		this.rows = rows;
		this.currentPosition = currentPosition;
		this.maxPosition = maxPosition;
		this.maxCertain = maxCertain;
	}
	
	public String[] getHeader()
	{
		return header;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPosition;
		result = prime * result + Arrays.hashCode(header);
		result = prime * result + (maxCertain ? 1231 : 1237);
		result = prime * result + maxPosition;
		result = prime * result + Arrays.hashCode(rows);
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PageViewModel other = (PageViewModel) obj;
		if (currentPosition != other.currentPosition) return false;
		if (!Arrays.equals(header, other.header)) return false;
		if (maxCertain != other.maxCertain) return false;
		if (maxPosition != other.maxPosition) return false;
		if (rows.length != other.rows.length) return false;
		for (int i = 0; i < rows.length; i++)
		{
			if (!Arrays.equals(rows[i], other.rows[i])) return false;
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		return "PageViewModel [header=" + Arrays.toString(header) + ", rows="
			+ Arrays.toString(rows) + ", currentPosition=" + currentPosition + ", maxPosition="
			+ maxPosition + ", maxCertain=" + maxCertain + "]";
	}
	
	public String[][] getRows()
	{
		return rows;
	}
	
	public int getCurrentPosition()
	{
		return currentPosition;
	}
	
	public int getMaxPosition()
	{
		return maxPosition;
	}
	
	public boolean getMaxCertain()
	{
		return maxCertain;
	}
	
}
