package moire.paths;

import moire.boundaries.Boundary;


public class StationaryPath extends Path
{

	public StationaryPath ( Boundary boundary )
	{
		super ( boundary );
	}

	@Override
	public void advance ()
	{
		// Do Nothing!  We ain't goin' nowhere!
	}

	@Override
	public String toString ()
	{
		return "StationaryPath []";
	}
}
