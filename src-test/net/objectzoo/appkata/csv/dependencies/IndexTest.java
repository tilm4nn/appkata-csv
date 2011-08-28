package net.objectzoo.appkata.csv.dependencies;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class IndexTest
{
	private Index sut;
	
	@Before
	public void createSut()
	{
		sut = new Index();
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void retrieveOffsetThrowsIllegalArgumentExceptionForMissingPage()
	{
		sut.retrieveOffset(4);
	}
	
	@Test
	public void retrieveOffsetRetrievesCorrectOffset()
	{
		sut.offsets.put(7, 1234567890L);
		
		long actual = sut.retrieveOffset(7);
		
		assertThat(actual, is(1234567890L));
	}
	
	@Test
	public void storeOffsetStoresCorrectOffset()
	{
		sut.storeOffset(5, 1234567890L);
		
		assertThat(sut.offsets, hasEntry(5, 1234567890L));
	}
}
