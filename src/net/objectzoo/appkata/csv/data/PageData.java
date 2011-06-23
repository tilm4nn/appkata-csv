package net.objectzoo.appkata.csv.data;

import java.util.List;

public class PageData
{
	private final List<CsvLine> data;
	
	public PageData(List<CsvLine> data)
	{
		this.data = data;
	}
	
	public List<CsvLine> getData()
	{
		return data;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PageData other = (PageData) obj;
		if (data == null)
		{
			if (other.data != null) return false;
		}
		else if (!data.equals(other.data)) return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "PageData [data=" + data + "]";
	}
}
