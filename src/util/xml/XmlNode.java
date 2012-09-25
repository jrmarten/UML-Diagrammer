package util.xml;

class XmlNode
{
    private String tag;
    private Map<String, String> attrs;
    private String data;
    private List<XmlNode> children;
    private XmlNode parent;
    
    public XmlNode ( String tag, XmlNode parent ) 
    {
	this.tag = tag; 
	this.parent = parent;
	attrs = new HashMap<String, String> ( );
	children = new LinkedList<XmlNode> ( );
    }
    
    public void addAttribute ( String key, String value ) { attrs.put ( key, value ); }
    public void addData ( String data ) { this.data += data; }
    public void addChild ( XmlNode n ) { children.add ( n ); }
    
    public String getTag ( ) { return tag; }
    public String getAttribute ( String key ) { return attrs.get ( key ); }
    public String getData ( ) { return data; }
    public XmlNode getParent ( ) { return parent; }
    public XmlNode getChild ( int i )
    {
	return children.get ( i );
    }
}
