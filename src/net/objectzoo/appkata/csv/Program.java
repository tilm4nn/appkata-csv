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

import net.objectzoo.appkata.csv.dependencies.ConsoleAdapter;
import net.objectzoo.appkata.csv.dependencies.ConsoleAdapterContract;
import net.objectzoo.appkata.csv.dependencies.Index;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapter;
import net.objectzoo.appkata.csv.dependencies.TextFileAdapterContract;
import net.objectzoo.appkata.csv.dependencies.TextFileScanner;
import net.objectzoo.appkata.csv.dependencies.TextFileScannerContract;
import net.objectzoo.appkata.csv.flow.DetermineFilename;
import net.objectzoo.appkata.csv.flow.DeterminePageOffsets;
import net.objectzoo.appkata.csv.flow.DeterminePageSize;
import net.objectzoo.appkata.csv.flow.DisplayCommands;
import net.objectzoo.appkata.csv.flow.InputPageNumber;
import net.objectzoo.appkata.csv.flow.MainBoard;
import net.objectzoo.appkata.csv.flow.RepeatedWaitForCommand;
import net.objectzoo.appkata.csv.flow.SelectPage;
import net.objectzoo.appkata.csv.flow.SeparateHeaderAndData;
import net.objectzoo.appkata.csv.flow.SplitLines;
import net.objectzoo.appkata.csv.flow.StoreOffsetInIndex;
import net.objectzoo.appkata.csv.flow.displaypage.DetermineColumnLengths;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageBoard;
import net.objectzoo.appkata.csv.flow.displaypage.DisplayPageViewModel;
import net.objectzoo.appkata.csv.flow.displaypage.MapToPageViewModel;
import net.objectzoo.appkata.csv.flow.displaypage.RenderPageViewModel;
import net.objectzoo.appkata.csv.flow.loadpage.ComputeRecordNumber;
import net.objectzoo.appkata.csv.flow.loadpage.LoadPageBoard;
import net.objectzoo.appkata.csv.flow.loadpage.LookUpPageOffset;
import net.objectzoo.appkata.csv.flow.loadpage.PutInRecords;
import net.objectzoo.appkata.csv.flow.loadpage.ReadPageLines;

public class Program
{
	public static void main(String... args)
	{
		TextFileAdapterContract textFileAdapter = new TextFileAdapter();
		TextFileScannerContract textFileScanner = new TextFileScanner();
		ConsoleAdapterContract consoleAdapter = new ConsoleAdapter();
		Index index = new Index();
		
		DeterminePageSize determinePageSize = new DeterminePageSize();
		DetermineFilename determineFilename = new DetermineFilename();
		DeterminePageOffsets determinePageOffsets = new DeterminePageOffsets();
		LookUpPageOffset lookupPageOffset = new LookUpPageOffset();
		ReadPageLines readPageLines = new ReadPageLines();
		StoreOffsetInIndex storeOffsetInIndex = new StoreOffsetInIndex();
		DisplayPageViewModel displayPageViewModel = new DisplayPageViewModel();
		DisplayCommands displayCommands = new DisplayCommands();
		RepeatedWaitForCommand repeatedWaitForCommand = new RepeatedWaitForCommand();
		InputPageNumber inputPageNumber = new InputPageNumber();
		SplitLines splitLines = new SplitLines();
		SeparateHeaderAndData separateHeaderAndData = new SeparateHeaderAndData();
		PutInRecords putInRecords = new PutInRecords();
		SelectPage selectPage = new SelectPage();
		MapToPageViewModel mapToPageViewModel = new MapToPageViewModel();
		DetermineColumnLengths determineColumnLengths = new DetermineColumnLengths();
		RenderPageViewModel renderPageViewModel = new RenderPageViewModel();
		ComputeRecordNumber computeRecordNumber = new ComputeRecordNumber();
		
		DisplayPageBoard displayPage = new DisplayPageBoard(mapToPageViewModel,
			determineColumnLengths, renderPageViewModel, displayPageViewModel);
		
		LoadPageBoard loadPage = new LoadPageBoard(computeRecordNumber, lookupPageOffset,
			readPageLines, splitLines, separateHeaderAndData, putInRecords);
		
		MainBoard mainBoard = new MainBoard(repeatedWaitForCommand, determinePageSize,
			determineFilename, determinePageOffsets, storeOffsetInIndex, selectPage, loadPage,
			inputPageNumber, displayPage, displayCommands);
		
		readPageLines.inject(textFileAdapter);
		determinePageOffsets.inject(textFileScanner);
		storeOffsetInIndex.inject(index);
		lookupPageOffset.inject(index);
		displayPageViewModel.inject(consoleAdapter);
		displayCommands.inject(consoleAdapter);
		repeatedWaitForCommand.inject(consoleAdapter);
		inputPageNumber.inject(consoleAdapter);
		
		determinePageSize.configure(args);
		determineFilename.configure(args);
		
		mainBoard.getStart().invoke();
	}
}
