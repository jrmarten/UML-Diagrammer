package edu.uwm.cs361.settings.format;


public abstract class AbstractFormatter implements Formatter
{


	/*
	 * Escaped
	 * 
	 * determines if a character has been escaped.
	 * 
	 * @param str				String search through
	 * @param index			position of character to check if escaped
	 * 	
	 * @return 					whether if the number of \ chars before the index is odd.
	 */
	protected boolean escaped ( String str, int index )
	{
		int c = 0;
		index = index - 1;
		for ( ; index >= 0; index-- )
			{
				if ( str.charAt( index ) != '\\' ) break;
				c++; //lolz
			}
		
		return (c & 1) == 1;
	}
}
