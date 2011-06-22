package net.objectzoo.appkata.csv.dependencies;

import java.io.IOException;
import java.util.List;

public interface TextFileAdapterContract
{
	
	List<String> readLines(String filename, int numberOfLines) throws IOException;
	
}