package moire.paths;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;

import moire.boundaries.Boundary;
import utility.geometry.LineSegment;
import utility.geometry.Point;

public class PolygonPath extends Path
{	
	protected Iterable<Point> points;
	protected Iterator<Point> pointIterator;
	protected LineSegment segment;
	protected double step;
	protected double pos;
	
	public PolygonPath ( Boundary boundary, List<Point> points, double step )
	{
		super ( boundary );
		
		this.points = Iterables.cycle ( points );
		pointIterator = this.points.iterator ();
		
		this.step = step;
		
		// Don't Call Advance, since the points are bound and will not be initialized yet.
		locationProperty.setValue ( points.get ( 0 ) );
	}

	@Override
	public void advance ()
	{
	    if ( segment == null )
	    {
	        segment = new LineSegment ( pointIterator.next (), pointIterator.next () );
	        pos = 0;
	    }
	    
	    if ( pos >= 1.0 )
	    {
	        // Use the end point of the previous segment as the starting point of the next segment.
	        segment = new LineSegment ( segment.b (), pointIterator.next () );
	        
	        pos = 0.0;
	    }
	    
		locationProperty.setValue ( segment.along ( pos ) );
		pos += step;
	}
}
