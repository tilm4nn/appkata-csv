package net.objectzoo.appkata.csv;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils
{
	
	public static <T> List<T> list(T... values)
	{
		List<T> result = new ArrayList<T>(values.length);
		Collections.addAll(result, values);
		return result;
	}
	
}
