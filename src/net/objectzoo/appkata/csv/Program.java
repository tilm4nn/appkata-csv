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

import net.objectzoo.appkata.csv.dependencies.TextFileAdapter;
import net.objectzoo.appkata.csv.flow.DisplayExitCommandAndWait;
import net.objectzoo.appkata.csv.flow.MainBoard;
import net.objectzoo.appkata.csv.flow.ReadLines;
import net.objectzoo.appkata.csv.flow.SeparateHeaderAndData;
import net.objectzoo.appkata.csv.flow.SplitLines;
import net.objectzoo.appkata.csv.flow.displaypage.Board;
import net.objectzoo.appkata.csv.flow.displaypage.DetermineColumnLengths;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageTable;
import net.objectzoo.appkata.csv.flow.displaypage.RenderPageTable;

public class Program
{
	public static void main(String... args)
	{
		TextFileAdapter textFileAdapter = new TextFileAdapter();
		ReadLines readLines = new ReadLines();
		new MainBoard(readLines, new SplitLines(), new SeparateHeaderAndData(), new Board(
			new DetermineColumnLengths(), new RenderPageTable(), new DisplayPageTable()),
			new DisplayExitCommandAndWait());
		
		readLines.inject(textFileAdapter);
		
		readLines.run(args);
	}
}
