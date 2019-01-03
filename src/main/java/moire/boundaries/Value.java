package moire.boundaries;

public class Value
{
	public double value;
	public boolean isProportional = false;
	
	public Value ( double value )
	{
		this ( value, false );
	}
	
	public Value ( double value, boolean isProportional )
	{
		this.value = value;
		this.isProportional = isProportional;
	}

    @Override
    public String toString ()
    {
        return "Value [value=" + value + ", isProportional=" + isProportional + "]";
    }
    
    public Value negate()
    {
        return new Value ( -value, isProportional );
    }
}
