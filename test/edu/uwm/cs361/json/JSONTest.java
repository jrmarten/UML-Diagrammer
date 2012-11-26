package edu.uwm.cs361.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.cs361.Util;
import edu.uwm.cs361.settings.format.CCScopeFormatter;
import edu.uwm.cs361.settings.format.Expansion;
import edu.uwm.cs361.settings.format.Formatter;
import edu.uwm.cs361.settings.format.FormatterPipe;
import edu.uwm.cs361.settings.json.JSONQuerier;
import edu.uwm.cs361.settings.json.JSONReader;

public class JSONTest
{
	JSONQuerier search;
	
	
	public final static Object[][] key_val =
		{
				{ "mode", "CC" },
				{ "name", "java" },
				{ "static_keyword", "static" },
				{ "abstract_keyword", "abstract" },
				
				{ "num", 12.5},
				
				{ "base_array[0]", "apple" },
				{ "base_array[1]", "orange" },
				
				{ "file.header", "package ${package};" },
				{ "file.import_statement", "import ${filename};" },
				
				{ "class.header", "${access} ${modifiers} class ${name}" },
				
				{ "attribute.header", "${access} ${modifiers} ${name};" },
				{ "attribute.modifiers[0]", "static" },
				{ "attribute.modifiers[1]", "final" },
				
				{ "method.header", "${access} ${modifiers} ${name} (${params})" },
				{ "method.body", "\\n{\\n${implementation}\\n}" },
				{ "method.abstract_body", ";\\n" },
				{ "method.modifiers[0]", "abstract" },
				{ "method.modifiers[1]", "static" },
				{ "method.modifiers[2]", "final" },
				
		};
	
	@Before
	public void setUp ( )
	{
		JSONReader reader =  JSONReader.Create( "test.input/test.json" );
		
		assertNotNull ( reader );
		search = reader.query();
	}
	
	
	@Test
	public void TestRead()
	{
		assertNotNull ( search );
		
		for ( Object[] set : key_val )
			{
				boolean test = search.query( (String) set[0] ).equals( set[1] );
				if ( !test )
					{
						System.out.println ( "Assertion Faild:\t" + set[0]+":"+set[1] );
						System.out.println ( "Returned:\t" + search.query( (String) set[0] ) );
					}
				assertTrue  ( test );
			}
	}
	
	
	@Test
	public void query_console ( )
	{
		assertNotNull ( search );
		
		Expansion expan = new Expansion ( );
		expan.set( "access", "private" );
		expan.set( "name", "nameX" );
		expan.set( "implementation" , "_");
		
		Formatter form = new FormatterPipe ( 
				new CCScopeFormatter(), 
				//new StringFormatter(), //better used for non-developer parts.
				expan );
		
		Util.dprint( expan.dir( true ) ) ;
		
		Scanner stdin = new Scanner ( System.in );
		String input = "";
		
		System.out.println ( "Open Console? Y/N" );
		
		input = stdin.nextLine();
		if ( !input.isEmpty() && input.toLowerCase().charAt( 0 ) == 'n' ) return;
		
		while ( true )
			{
				input = stdin.nextLine();
				if ( input.toLowerCase().equals( "quit" )||
						input.toLowerCase().equals( "exit" ) )
					break;
				
				if ( input.startsWith( "type " ) )
					{
						String name = search.query(
								input.substring( input.indexOf( ' ' ) + 1 )
								).getClass().getCanonicalName();
						name = name.substring( name.lastIndexOf( '.' ) + 1 );
						
						
						
						System.out.println (  name  );
					}
				else
					System.out.println ( form.format ( search.query( input ).toString() ) );
			}
		
		Util.dprint ( expan.dir ( true ) );
	}
}
