package moire.paths;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import moire.boundaries.Boundary;
import utility.geometry.Point;

public abstract class Path
{
	protected Boundary boundary;
	protected ObjectProperty<Point> locationProperty = new SimpleObjectProperty<>();
	
	protected Path ( Boundary boundary )
	{
		this.boundary = boundary;
	}
	
	public abstract void advance ();
	
	public ObjectProperty<Point> locationProperty()
	{
		return locationProperty;
	}
}
