package moire.paths;

import javafx.beans.binding.Bindings;
import moire.boundaries.Boundary;
import moire.builders.BuilderException;
import utility.geometry.Point;

public class StationaryPathBuilder
{
	protected Boundary boundary = null;
	
	protected double x = Double.NaN;
	protected boolean xIsProportional = false;
	
	protected double y = Double.NaN;
	protected boolean yIsProportional = false;
	
	public StationaryPathBuilder boundary ( Boundary boundary )
	{
		this.boundary = boundary;
		return this;
	}
	
	public StationaryPathBuilder x ( double x )
	{
		this.x = x;
		return this;
	}
	
	public StationaryPathBuilder x ( double x, boolean isProportional )
	{
		this.x = x;
		this.xIsProportional = isProportional;
		
		return this;
	}
	
	public StationaryPathBuilder xIsProportional()
	{
		xIsProportional = true;
		return this;
	}
	
	public StationaryPathBuilder xIsProportional ( boolean isProportional )
	{
		xIsProportional = isProportional;
		return this;
	}
	
	public StationaryPathBuilder y ( double y )
	{
		this.y = y;
		return this;
	}
	
	public StationaryPathBuilder y ( double y, boolean isProportional )
	{
		this.y = y;
		this.yIsProportional = isProportional;
		
		return this;
	}
	
	public StationaryPathBuilder yIsProportional()
	{
		yIsProportional = true;
		return this;
	}
	
	public StationaryPathBuilder yIsProportional ( boolean isProportional )
	{
		yIsProportional = isProportional;
		return this;
	}
	
	public StationaryPath build() throws BuilderException
	{
		validate();
		
		StationaryPath path = new StationaryPath ( boundary );
		
		if ( xIsProportional && yIsProportional )
			path.locationProperty ().bind ( Bindings.createObjectBinding ( () -> { return new Point ( boundary.left () + boundary.width () * x, boundary.top () + boundary.height () * y ); }, boundary.leftProperty (), boundary.topProperty (), boundary.rightProperty (), boundary.bottomProperty () ) );
		else if ( xIsProportional && !yIsProportional )
			path.locationProperty ().bind ( Bindings.createObjectBinding ( () -> { return new Point ( boundary.left () + boundary.width () * x, boundary.top () + y ); }, boundary.leftProperty (), boundary.topProperty (), boundary.rightProperty (), boundary.bottomProperty () ) );
		else if ( !xIsProportional && yIsProportional )
			path.locationProperty ().bind ( Bindings.createObjectBinding ( () -> { return new Point ( boundary.left () + x, boundary.top () + boundary.height () * y ); }, boundary.leftProperty (), boundary.topProperty (), boundary.rightProperty (), boundary.bottomProperty () ) );
		else // Neither Proportional
			path.locationProperty ().bind ( Bindings.createObjectBinding ( () -> { return new Point ( boundary.left () + x, boundary.top () + y); }, boundary.leftProperty (), boundary.topProperty (), boundary.rightProperty (), boundary.bottomProperty () ) );
		
		return path;
	}
	
	protected void validate() throws BuilderException
	{
		StringBuilder msg = new StringBuilder();
		
		if ( boundary == null ) msg.append ( "\n\tBoundary Undefined" );
		
		if ( x == Double.NaN ) msg.append ( "\n\tX Undefined" );
		if ( y == Double.NaN ) msg.append ( "\n\tY Undefined" );
		
		if ( msg.length () > 0 )
			throw new BuilderException ( "Stationary Path Data Incomplete:" + msg.toString () );
	}
}
