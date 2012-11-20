package edu.uwm.cs361.json;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Scanner;

import org.junit.Test;

import edu.uwm.cs361.settings.json.JSONReader;
import edu.uwm.cs361.settings.json.elements.JSONObject;

public class JSONTest
{
	JSONObject root;
	
	
	public final static Object[][] key_val =
		{
				{ "mode", "CC" },
				{ "name", "java" },
				{ "static_keyword", "static" },
				{ "abstract_keyword", "abstract" },
				
				{ "base_array[0]", "apple" },
				{ "base_array[1]", "orange" },
				
				{ "file.header", "package ${package};" },
				{ "file.import_statement", "import ${filename};" },
				
				{ "class.header", "${access} ${modifiers} class ${name}" },
				
				{ "attribute.header", "${access} ${modifiers} ${name};" },
				{ "attribute.modifiers[0]", "static" },
				{ "attribute.modifiers[1]", "final" },
				
				{ "method.header", "${access} ${modifiers} ${name} (${params})" },
				{ "method.body", "\n{\n${implementation}\n}" },
				{ "method.abstract_body", ";\n" },
				{ "method.modifiers[0]", "abstract" },
				{ "method.modifiers[1]", "static" },
				{ "method.modifiers[2]", "final" },
				
		};
	
	@Test
	public void TestRead()
	{
		JSONReader reader = JSONReader.Create( "test.input/java.json" );
		
		assertNotNull ( reader );
		
		root = reader.getData() ;
		
		for ( Object[] set : key_val )
			{
				assertEquals ( root.query( (String)set[0] ), set[1] );
			}
		
	}
	
	
	@Test
	public void query_console ( )
	{
		Scanner stdin = new Scanner ( System.in );
		String input = "";
		
		System.out.println ( "Open Console? Y/N" );
		if ( stdin.nextLine().toLowerCase().charAt( 0 ) == 'n' ) return;
		
		while ( !input.equals( "quit" ) )
			{
				input = stdin.nextLine();
				if ( input.toLowerCase().equals( "quit" )||
						input.toLowerCase().equals( "exit" ) )
					break;
				
				if ( input.startsWith( "type " ) )
					{
						String name = root.query(
								input.substring( input.indexOf( ' ' ) + 1 )
								).getClass().getCanonicalName();
						name = name.substring( name.lastIndexOf( '.' ) + 1 );
						
						System.out.println ( name );
					}
				else
					System.out.println ( root.query( input ) );
			}
	}
}
