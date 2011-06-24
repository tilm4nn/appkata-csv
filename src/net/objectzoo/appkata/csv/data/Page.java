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

import java.util.List;

public class Page
{
	private final CsvLine header;
	
	private final List<CsvRecord> records;
	
	@Override
	public String toString()
	{
		return "Page [header=" + header + ", records=" + records + "]";
	}
	
	public CsvLine getHeader()
	{
		return header;
	}
	
	public List<CsvRecord> getRecords()
	{
		return records;
	}
	
	public Page(CsvLine header, PageData pageData)
	{
		this(header, pageData.getRecords());
	}
	
	public Page(CsvLine header, List<CsvRecord> data)
	{
		this.header = header;
		this.records = data;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((records == null) ? 0 : records.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Page other = (Page) obj;
		if (records == null)
		{
			if (other.records != null) return false;
		}
		else if (!records.equals(other.records)) return false;
		if (header == null)
		{
			if (other.header != null) return false;
		}
		else if (!header.equals(other.header)) return false;
		return true;
	}
}
