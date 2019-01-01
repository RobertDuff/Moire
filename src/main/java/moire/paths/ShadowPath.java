package moire.paths;

import moire.boundaries.Boundary;
import utility.geometry.PointBuilder;


public class ShadowPath extends Path
{
	public ShadowPath ( Boundary boundary, Path a, double xOffset, double yOffset )
	{
		super ( boundary );
		
		a.locationProperty ().addListener ( ( path, oldVal, newVal ) -> 
		{
			locationProperty.setValue ( PointBuilder.offset ( newVal, xOffset, yOffset ) );
		} );
	}

	@Override
	public void advance ()
	{
		// Do Nothing.  The binding handles all the calculations
	}
}
