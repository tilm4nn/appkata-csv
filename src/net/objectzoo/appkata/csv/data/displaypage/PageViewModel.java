package net.objectzoo.appkata.csv.data.displaypage;

import java.util.Arrays;

public class PageViewModel
{
	private final String[] header;
	
	private final String[][] rows;
	
	public PageViewModel(String[] header, String[][] rows)
	{
		this.header = header;
		this.rows = rows;
	}
	
	public String[] getHeader()
	{
		return header;
	}
	
	public String[][] getRows()
	{
		return rows;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(header);
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
		if (!Arrays.equals(header, other.header)) return false;
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
			+ Arrays.toString(rows) + "]";
	}
	
}
