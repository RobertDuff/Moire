package moire.paths;

import moire.boundaries.Boundary;
import utility.geometry.Circle;
import utility.geometry.CircleBuilder;
import utility.geometry.Point;


public class OutscribedCircleCenterPath extends Path
{

	public OutscribedCircleCenterPath ( Boundary boundary, Path a, Path b, Path c )
	{
		super ( boundary );
		
		a.locationProperty ().addListener ( ( path, oldVal, newVal ) -> 
		{
			try
			{
				Circle circle = CircleBuilder.outscribedCircle ( newVal, b.locationProperty ().get (), c.locationProperty ().get () );
				locationProperty.setValue ( circle.center () );
			}
			catch ( IllegalArgumentException e )
			{
				// At least 2 of the points are the same, so the center cannot be calculated, so just set to the origin
				locationProperty.setValue ( new Point ( 0, 0 ) );
			}
		} ) ;
		
		b.locationProperty ().addListener ( ( path, oldVal, newVal ) -> 
		{
			try
			{
				Circle circle = CircleBuilder.outscribedCircle ( a.locationProperty ().get (), newVal, c.locationProperty ().get () );
				locationProperty.setValue ( circle.center () );
			}
			catch ( IllegalArgumentException e )
			{
				// At least 2 of the points are the same, so the center cannot be calculated, so just set to the origin
				locationProperty.setValue ( new Point ( 0, 0 ) );
			}
		} ) ;
		
		c.locationProperty ().addListener ( ( path, oldVal, newVal ) -> 
		{
			try
			{
				Circle circle = CircleBuilder.outscribedCircle ( a.locationProperty ().get (), b.locationProperty ().get (), newVal );
				locationProperty.setValue ( circle.center () );
			}
			catch ( IllegalArgumentException e )
			{
				// At least 2 of the points are the same, so the center cannot be calculated, so just set to the origin
				locationProperty.setValue ( new Point ( 0, 0 ) );
			}
		} ) ;
	}

	@Override
	public void advance ()
	{
		// Do Nothing.  The bindings perform all the calculations.
	}

}
