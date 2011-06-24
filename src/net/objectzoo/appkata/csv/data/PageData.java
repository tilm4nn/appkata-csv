package net.objectzoo.appkata.csv.data;

import java.util.List;

public class PageData
{
	private final List<CsvRecord> records;
	
	public PageData(List<CsvRecord> data)
	{
		this.records = data;
	}
	
	public List<CsvRecord> getRecords()
	{
		return records;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((records == null) ? 0 : records.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PageData other = (PageData) obj;
		if (records == null)
		{
			if (other.records != null) return false;
		}
		else if (!records.equals(other.records)) return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		return "PageData [records=" + records + "]";
	}
}
