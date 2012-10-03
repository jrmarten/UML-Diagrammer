
public enum Access
{
    PRIVATE ( "private" ),
        PUBLIC ( "public" ),
        DEFAULT ( "default" );

    private Access ( String str )
        {
            name = str;
        }

    private String name = "";
    public String toString()
    {
        return name;
    }
}