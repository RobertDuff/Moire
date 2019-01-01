package moire.boundaries;

import javafx.beans.binding.Bindings;

public class BoundaryBuilder
{
	public static class IncompleteBoundaryDataException extends Exception
	{
		private static final long serialVersionUID = 1L;

		public IncompleteBoundaryDataException ( String message )
		{
			super ( message );
		}		
	}
	
	protected class Value
	{
		public double value;
		public boolean isProportional = false;
		
		public Value ( double value )
		{
			this.value = value;
		}
		
		public Value ( double value, boolean isProportional )
		{
			this ( value );
			this.isProportional = isProportional;
		}
	};
	
	protected Boundary parent = null;
	
	protected Value l = null;
	protected Value t = null;
	protected Value r = null;
	protected Value b = null;
	protected Value w = null;
	protected Value h = null;
	
	public void parent ( Boundary parent )
	{
		this.parent = parent;
	}
	
	//
	// Left
	//
	
	public BoundaryBuilder left ( double value )
	{
		l = new Value ( value );		
		return this;
	}
	
	public BoundaryBuilder left ( double value, boolean isProportional )
	{
		l = new Value ( value, isProportional );
		return this;
	}
	
	public BoundaryBuilder leftIsProportional()
	{
		l.isProportional = true;
		return this;
	}
	
	public BoundaryBuilder leftIsProportional ( boolean isProportional )
	{
		l.isProportional = isProportional;
		return this;
	}
	
	//
	// Top
	//
	
	public BoundaryBuilder top ( double value )
	{
		t = new Value ( value );		
		return this;
	}
	
	public BoundaryBuilder top ( double value, boolean isProportional )
	{
		t = new Value ( value, isProportional );
		return this;
	}
	
	public BoundaryBuilder topIsProportional()
	{
		t.isProportional = true;
		return this;
	}
	
	public BoundaryBuilder topIsProportional ( boolean isProportional )
	{
		t.isProportional = isProportional;
		return this;
	}
	
	//
	// Right
	//
	
	public BoundaryBuilder right ( double value )
	{
		r = new Value ( value );		
		return this;
	}
	
	public BoundaryBuilder right ( double value, boolean isProportional )
	{
		r = new Value ( value, isProportional );
		return this;
	}
	
	public BoundaryBuilder rightIsProportional()
	{
		r.isProportional = true;
		return this;
	}
	
	public BoundaryBuilder rightIsProportional ( boolean isProportional )
	{
		r.isProportional = isProportional;
		return this;
	}
	
	//
	// Bottom
	//
	
	public BoundaryBuilder bottom ( double value )
	{
		b = new Value ( value );		
		return this;
	}
	
	public BoundaryBuilder bottom ( double value, boolean isProportional )
	{
		b = new Value ( value, isProportional );
		return this;
	}
	
	public BoundaryBuilder bottomIsProportional()
	{
		b.isProportional = true;
		return this;
	}
	
	public BoundaryBuilder bottomIsProportional ( boolean isProportional )
	{
		b.isProportional = isProportional;
		return this;
	}
	
	//
	// Width
	//
	
	public BoundaryBuilder width ( double value )
	{
		w = new Value ( value );		
		return this;
	}
	
	public BoundaryBuilder width ( double value, boolean isProportional )
	{
		w = new Value ( value, isProportional );
		return this;
	}
	
	public BoundaryBuilder widthIsProportional()
	{
		w.isProportional = true;
		return this;
	}
	
	public BoundaryBuilder widthIsProportional ( boolean isProportional )
	{
		w.isProportional = isProportional;
		return this;
	}
	
	//
	// Height
	//
	
	public BoundaryBuilder height ( double value )
	{
		h = new Value ( value );		
		return this;
	}
	
	public BoundaryBuilder height ( double value, boolean isProportional )
	{
		h = new Value ( value, isProportional );
		return this;
	}
	
	public BoundaryBuilder heightIsProportional()
	{
		h.isProportional = true;
		return this;
	}
	
	public BoundaryBuilder heightIsProportional ( boolean isProportional )
	{
		h.isProportional = isProportional;
		return this;
	}
	
	//
	//
	//
	
	public Boundary build() throws IncompleteBoundaryDataException
	{
		validate();
		
		final Boundary boundary = new Boundary();

		//
		// Left
		//
		if ( l.isProportional )
			boundary.leftBorderProperty ().bind ( Bindings.createDoubleBinding ( () -> { return parent.leftBorder () + parent.width () * l.value; }, parent.leftBorderProperty (), parent.rightBorderProperty ) );
		else
			boundary.leftBorderProperty ().bind ( Bindings.add ( parent.leftBorderProperty (), l.value ) );
			
		//
		// Top
		//
		
		if ( t.isProportional )
			boundary.topBorderProperty().bind ( Bindings.createDoubleBinding ( () -> { return parent.topBorder () + parent.height() * t.value; }, parent.topBorderProperty (), parent.bottomBorderProperty () ) );
		else
			boundary.topBorderProperty ().bind ( Bindings.add ( parent.topBorderProperty (), t.value ) );
		
		//
		// Right
		//
		
		if ( r != null )
		{
			if ( r.isProportional )
				boundary.rightBorderProperty ().bind ( Bindings.createDoubleBinding ( () -> { return parent.leftBorder() + parent.width () * r.value; }, parent.leftBorderProperty (), parent.rightBorderProperty () ) );
			else
				boundary.rightBorderProperty ().bind ( Bindings.subtract ( parent.rightBorderProperty (), r.value ) );
		}
		
		//
		// Bottom
		//
		
		if ( b != null )
		{
			if ( b.isProportional )
				boundary.bottomBorderProperty ().bind ( Bindings.createDoubleBinding ( () -> { return parent.topBorder() + parent.height () * b.value; }, parent.topBorderProperty (), parent.bottomBorderProperty () ) );
			else
				boundary.bottomBorderProperty ().bind ( Bindings.subtract ( parent.bottomBorderProperty (), b.value ) );
		}
		
		//
		// Width
		//
		
		if ( w != null )
		{
			if ( w.isProportional )
				boundary.rightBorderProperty ().bind ( Bindings.createDoubleBinding ( () -> { return boundary.leftBorderProperty().get () + parent.width () * w.value; }, parent.leftBorderProperty (), parent.rightBorderProperty () ) );
			else
				boundary.rightBorderProperty.bind ( Bindings.add ( boundary.leftBorderProperty(), w.value ) );
		}
		
		//
		// Height
		//
		
		if ( h != null )
		{
			if ( h.isProportional )
				boundary.bottomBorderProperty ().bind ( Bindings.createDoubleBinding ( () -> { return boundary.topBorderProperty().get () + parent.height () * h.value; }, parent.topBorderProperty (), parent.bottomBorderProperty () ) );
			else
				boundary.bottomBorderProperty.bind ( Bindings.add ( boundary.topBorderProperty(), h.value ) );
		}
		
		return boundary;
	}
	
	protected void validate() throws IncompleteBoundaryDataException
	{
		StringBuilder msg = new StringBuilder();
		
		if ( parent == null )	msg.append ( "\n\tParent Bouundary Undefined" );
		
		if ( l == null ) msg.append ( "\n\tLeft Position Undefined" );
		if ( t == null ) msg.append ( "\n\tTop Position Undefined" );
		
		if ( r == null && w == null ) msg.append ( "Both Right Position and Width Undefined" );
		if ( b == null && h == null ) msg.append ( "Both Bottom Position and Height Undefined" );
		
		if ( r != null && w != null ) msg.append ( "Cannot define both Right Position and Width" );
		if ( b != null && h != null ) msg.append ( "Cannot define both Bottom Position and Height" );
		
		if ( msg.length () > 0 )
			throw new IncompleteBoundaryDataException ( "Bad Boundary Data:" + msg.toString () );
	}
}
