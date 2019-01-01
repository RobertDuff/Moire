package moire.builders.yaml;

import java.util.Map;

import moire.ModelData;
import moire.boundaries.Boundary;
import moire.boundaries.BoundaryBuilder.IncompleteBoundaryDataException;
import moire.builders.BuilderException;

public class YamlBoundaryBuilder implements moire.builders.BoundaryBuilder
{
	protected Map<String,Object> params;

	@SuppressWarnings ( "unchecked" )
	public YamlBoundaryBuilder ( Object top ) throws BuilderException
	{
		params = ( Map<String,Object> ) top;
	}

	@Override
	public Boundary build ( ModelData modelData ) throws BuilderException
	{
		String name = null;
		
		if ( params.containsKey ( "Name" ) )
			name = params.get ( "Name" ).toString ();

		moire.boundaries.BoundaryBuilder builder = new moire.boundaries.BoundaryBuilder();

		if ( params.containsKey ( "Parent" ) )
			builder.parent ( modelData.boundary ( params.get ( "Parent" ).toString () ) );
		else
			builder.parent ( modelData.topBoundary () );
		
		// Left
		
		Object l = params.get ( "Left" );
		if ( l.getClass ().equals ( String.class ) )
			builder.left ( percentage ( l ), true );
		else
			builder.left ( ( ( Number ) l ).doubleValue () );
		
		// Top
		
		Object t = params.get ( "Top" );
		if ( t.getClass ().equals ( String.class ) )
			builder.top ( percentage ( t ), true );
		else
			builder.top ( ( ( Number ) t ).doubleValue () );
		
		// Right
		
		Object r = params.get ( "Right" );
		if ( r != null )
		{
			if ( r.getClass ().equals ( String.class ) )
				builder.right ( percentage ( r ), true );
			else
				builder.right ( ( ( Number ) r ).doubleValue () );
		}
		
		// Bottom
		
		Object b = params.get ( "Bottom" );
		if ( b != null )
		{
			if ( b.getClass ().equals ( String.class ) )
				builder.bottom ( percentage ( b ), true );
			else
				builder.bottom ( ( ( Number ) b ).doubleValue () );
		}
		
		// Width
		
		Object w = params.get ( "Width" );
		if ( w != null )
		{
			if ( w.getClass ().equals ( String.class ) )
				builder.width ( percentage ( w ), true );
			else
				builder.width ( ( ( Number ) w ).doubleValue () );
		}
		
		// Height
		
		Object h = params.get ( "Height" );
		if ( h != null )
		{
			if ( h.getClass ().equals ( String.class ) )
				builder.height ( percentage ( h ), true );
			else
				builder.height ( ( ( Number ) h ).doubleValue () );
		}

		Boundary boundary;
		
		try
		{
			boundary = builder.build ();
		}
		catch ( IncompleteBoundaryDataException e )
		{
			throw new BuilderException ( "Boundary Build Failed", e );
		}
		
		modelData.addBoundary ( boundary, name );
		return boundary;
	}	
	
	protected double percentage ( Object spec )
	{
		String s = spec.toString ();
		
		return Double.valueOf ( s.substring ( 0, s.length ()-1 ) ) / 100;
	}
}
