package edu.uwm.cs361.classdiagram.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Keywords
{
	private static ArrayList<String> blacklist = new ArrayList<String>();

	private static void getList ( )
	{
		Scanner fin = null;
		try {
			fin = new Scanner ( new File ( "Java.keywords" ) );
		} catch (FileNotFoundException e) {
			return;
		}
		String line = "";
		String[] parts = null;

		while ( fin.hasNext ( ) )
			{
				line = fin.nextLine();
				parts = line.split ( " ;," );
				for ( String tmp : parts )
					{
						if ( !tmp.equals ( "" ) )
							blacklist.add ( tmp );
					}
			}
	}

	public static boolean blackListp ( String word )
	{
		if ( blacklist == null ) getList ( );
		return blacklist.contains ( word );
	}

}