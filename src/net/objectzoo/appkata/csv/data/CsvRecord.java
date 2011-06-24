package net.objectzoo.appkata.csv.data;

public class CsvRecord
{
	private final int number;
	
	private final CsvLine data;
	
	public CsvRecord(int number, CsvLine data)
	{
		this.number = number;
		this.data = data;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public CsvLine getData()
	{
		return data;
	}
	
	@Override
	public String toString()
	{
		return "CsvRecord [number=" + number + ", data=" + data + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + number;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		CsvRecord other = (CsvRecord) obj;
		if (data == null)
		{
			if (other.data != null) return false;
		}
		else if (!data.equals(other.data)) return false;
		if (number != other.number) return false;
		return true;
	}
}
