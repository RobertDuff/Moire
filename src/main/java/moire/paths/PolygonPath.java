package moire.paths;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Iterables;

import moire.boundaries.Boundary;
import utility.geometry.Point;

public class PolygonPath extends Path
{	
	protected Iterable<Point> points;
	protected Iterator<Point> point;
	
	public PolygonPath ( Boundary boundary, List<Point> points )
	{
		super ( boundary );
		
		this.points = Iterables.cycle ( points );
		point = this.points.iterator ();
		
		advance();
	}

	@Override
	public void advance ()
	{
		locationProperty.setValue ( point.next () );
	}
}
