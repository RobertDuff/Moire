package moire.paths;

import moire.boundaries.Boundary;
import utility.geometry.PointBuilder;


public class MidPointPath extends Path
{

	public MidPointPath ( Boundary boundary, Path a, Path b )
	{
		super ( boundary );
		
		a.locationProperty.addListener ( ( path, oldVal, newVal ) ->
		{
			locationProperty.setValue ( PointBuilder.midpoint ( newVal, b.locationProperty ().get () ) );
		} );
		
		b.locationProperty.addListener ( ( path, oldVal, newVal ) ->
		{
			locationProperty.setValue ( PointBuilder.midpoint ( a.locationProperty().get (), newVal ) );
		} );
	}

	@Override
	public void advance ()
	{
		// Do Nothing.  The binding handles all the calculations
	}

}
