package net.objectzoo.appkata.csv.dependencies;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.objectzoo.logging.LoggerFactory;

public class ConsoleAdapter implements ConsoleAdapterContract
{
	private static final Logger log = LoggerFactory.getLogger(ConsoleAdapter.class);
	
	private Reader consoleReader = new InputStreamReader(System.in);
	
	@Override
	public void output(String output)
	{
		System.out.print(output);
	}
	
	@Override
	public char input()
	{
		while (true)
		{
			try
			{
				return (char) consoleReader.read();
			}
			catch (IOException e)
			{
				log.log(Level.SEVERE, "Error while reading input.", e);
			}
		}
	}
	
}
