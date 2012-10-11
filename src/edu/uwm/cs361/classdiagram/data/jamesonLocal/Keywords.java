package edu.uwm.cs361.classdiagram.data.jamesonLocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import edu.uwm.cs361.classdiagram.data.*;


public class Keywords 
{
    private static ArrayList<String> blacklist;

    private static void getList ( )
    {
	Scanner fin = null;
	try {
		fin = new Scanner ( new File ( "Java.keywords" ) );
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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