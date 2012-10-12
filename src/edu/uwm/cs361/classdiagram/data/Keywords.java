package edu.uwm.cs361.classdiagram.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Keywords
{
	private static ArrayList <String> blacklist;
	private static ArrayList <String> primitives;
	
	public static void print ( )
	{
		for ( String tmp : blacklist )
			{
				System.out.println ( tmp );
			}
	}
	
	private static void getList ( )
	{
		blacklist= new ArrayList<String>();
		Scanner fin = null;
		try {
			File keywords = new File ( "Java.keywords");
			fin = new Scanner ( keywords );
		} catch (FileNotFoundException e) {
			
			return;
		}
		String line = "";
		String[] parts = null;

		while ( fin.hasNext ( ) )
			{
				line = fin.nextLine();
				parts = line.split ( " ;," );
				for ( String part : parts )
					{
						if ( !part.equals ( "" ) )
							{
								blacklist.add ( part );
							}
					}
			}
	}

	private static void getPrim ( )
	{
		primitives = new ArrayList<String>();
		Scanner fin = null;
		try {
			File keywords = new File ( "Java.primitives");
			fin = new Scanner ( keywords );
		} catch (FileNotFoundException e) {
			
			return;
		}
		String line = "";
		String[] parts = null;

		while ( fin.hasNext ( ) )
			{
				line = fin.nextLine();
				parts = line.split ( " ;," );
				for ( String part : parts )
					{
						if ( !part.equals ( "" ) )
							{
								primitives.add ( part );
							}
					}
			}
	}
	
	public static boolean reservedp ( String word )
	{
		if ( blacklist == null ) getList ( );
		if ( primitives == null ) getPrim ( );
		
		for ( String tmp : blacklist )
			{
				if ( word.equals ( tmp ) )
					{
						System.out.println ( word + ": " + tmp );
						return true;
					}
			}
			
		return false;
	}
	
	public static boolean keywordp ( String word )
	{
	if ( blacklist == null ) getList ( );
		
		for ( String tmp : blacklist )
			{
				if ( word.equals ( tmp ) )
					{
						System.out.println ( word + ": " + tmp );
						return true;
					}
			}
			
		return false;
	}
}