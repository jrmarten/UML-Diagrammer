package edu.uwm.cs361.json;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.json.JSONReader;

public class JSONTest
{

	@Test
	public void TestRead()
	{
		JSONReader reader = JSONReader.Create( "test.input/java.json" );
		
		assertNotNull ( reader );
		
		Util.dprint( reader.getData() );
	}
}
