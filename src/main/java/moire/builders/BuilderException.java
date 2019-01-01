package moire.builders;


public class BuilderException extends Exception
{
	private static final long serialVersionUID = 1L;

	public BuilderException ( String message )
	{
		super ( message );
	}

	public BuilderException ( String message, Throwable cause )
	{
		super ( message, cause );
	}
}
