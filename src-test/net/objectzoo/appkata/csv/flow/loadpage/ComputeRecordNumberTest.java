package net.objectzoo.appkata.csv.flow.loadpage;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import net.objectzoo.ebc.TestAction;

public class ComputeRecordNumberTest
{
	ComputeRecordNumber sut;
	
	TestAction<Integer> resultAction;
	
	@Before
	public void setup()
	{
		resultAction = new TestAction<Integer>();
		sut = new ComputeRecordNumber();
		sut.getResult().subscribe(resultAction);
	}
	
	@Test
	public void computesCorrectRecordNumberForFirstPage()
	{
		sut.pageSize = 25;
		
		sut.getProcess().invoke(1);
		
		assertThat(resultAction.getLastResult(), is(1));
	}
	
	@Test
	public void computesCorrectRecordNumberForThirdPage()
	{
		sut.pageSize = 25;
		
		sut.getProcess().invoke(3);
		
		assertThat(resultAction.getLastResult(), is(51));
	}
	
}
