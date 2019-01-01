package moire.paths;

import moire.boundaries.Boundary;
import utility.geometry.Circle;
import utility.geometry.CircleBuilder;


public class InscribedCircleCenterPath extends Path
{

	public InscribedCircleCenterPath ( Boundary boundary, Path a, Path b, Path c )
	{
		super ( boundary );
		
		a.locationProperty ().addListener ( ( path, oldVal, newVal ) -> 
		{
			Circle circle = CircleBuilder.inscribedCircle ( newVal, b.locationProperty ().get (), c.locationProperty ().get () );
			locationProperty.setValue ( circle.center () );
		} ) ;
		
		b.locationProperty ().addListener ( ( path, oldVal, newVal ) -> 
		{
			Circle circle = CircleBuilder.inscribedCircle ( a.locationProperty ().get (), newVal, c.locationProperty ().get () );
			locationProperty.setValue ( circle.center () );
		} ) ;
		
		c.locationProperty ().addListener ( ( path, oldVal, newVal ) -> 
		{
			Circle circle = CircleBuilder.inscribedCircle ( a.locationProperty ().get (), b.locationProperty ().get (), newVal );
			locationProperty.setValue ( circle.center () );
		} ) ;
	}

	@Override
	public void advance ()
	{
		// Do Nothing.  The bindings do all the calculations.
	}

}
