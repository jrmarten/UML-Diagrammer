package util.xml;

import java.io.*;

class XmlParser
{
    String file;
    XmlTree tree;
    XmlNode cur;


    public XmlParser ( String filename ) 
    {
	file = filename;
    }

    public void parse ( )
    {
	Reader fin = new Scanner ( new File ( file ) );
	String buffer = "";

	String tag = null;
	HashMap<String, String> attr = new HashMap <String, String> ( );

	while ( fin.hasNext ( ) )
	    {
		buffer = fin.nextLine();
		buffer.strip();
		

		for ( int i = 0; i < buffer.length; i++ )
		    {

		    }

	    }
    }

    private void startTagFound ( String tag, Map<String, String> attrs )
    {
	XmlNode tmp = new XmlNode ( tag, cur );
	for ( String key : attrs.keySet ( ) )
	    {
		tmp.addAttribute ( key, attrs.get ( key ) );
	    }
	cur = tmp;
	startTag ( tag, attrs );
    }

    private void endTagFound ( String tag )
    {
	if ( cur.getTag ( ) != tag )
	    throw new XmlParseException ( "End Tag mismatch" );
	cur = cur.getParent ( );
	endTag ( tag );
    }

    private void dataFound ( String data )
    {
	cur.addData ( data );
	data ( data );
    }

    protected void startTag ( String tag, Map<String, String> attrs ) { }
    protected void endTag ( String tag ) { }
    protected void data ( String data ) { }

    public final XmlTree getTree ( ) { return tree; }
 
}
