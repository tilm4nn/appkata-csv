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
package net.objectzoo.appkata.csv;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapter;
import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.appkata.csv.dependencies.Index;
import net.objectzoo.appkata.csv.dependencies.IndexContract;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapter;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.appkata.csv.dependencies.TextFileScanner;
import net.objectzoo.appkata.csv.dependencies.TextFileScannerContract;
import net.objectzoo.appkata.csv.flow.DetermineFilename;
import net.objectzoo.appkata.csv.flow.DeterminePageSize;
import net.objectzoo.appkata.csv.flow.MainBoard;

public class Program
{
	static class GuiceModule extends AbstractModule
	{
		@Override
		protected void configure()
		{
			bind(IndexContract.class).to(Index.class).in(Singleton.class);
			bind(ConsoleAdapterContract.class).to(ConsoleAdapter.class).in(Singleton.class);
			bind(TextFileScannerContract.class).to(TextFileScanner.class).in(Singleton.class);
			bind(TextFileAdapterContract.class).to(TextFileAdapter.class).in(Singleton.class);
			
			bind(DeterminePageSize.class).in(Singleton.class);
			bind(DetermineFilename.class).in(Singleton.class);
		}
	}
	
	public static void main(String... args)
	{
		Injector injector = Guice.createInjector(new GuiceModule());
		
		MainBoard mainBoard = injector.getInstance(MainBoard.class);
		DeterminePageSize determinePageSize = injector.getInstance(DeterminePageSize.class);
		DetermineFilename determineFilename = injector.getInstance(DetermineFilename.class);
		
		determinePageSize.configure(args);
		determineFilename.configure(args);
		
		mainBoard.getStart().invoke();
	}
}
