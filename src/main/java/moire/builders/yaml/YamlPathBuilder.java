package moire.builders.yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.beans.binding.Bindings;
import moire.ModelData;
import moire.boundaries.Boundary;
import moire.boundaries.Value;
import moire.builders.BuilderException;
import moire.builders.PathBuilder;
import moire.paths.BindablePoint;
import moire.paths.BouncingPath;
import moire.paths.InscribedCircleCenterPath;
import moire.paths.MidPointPath;
import moire.paths.OutscribedCircleCenterPath;
import moire.paths.OvalPath;
import moire.paths.Path;
import moire.paths.PolygonPath;
import moire.paths.RandomBouncingPath;
import moire.paths.ShadowPath;
import moire.paths.StationaryPath;
import utility.geometry.Point;


public class YamlPathBuilder implements PathBuilder
{
	protected Map<String,Object> params;
	
	@SuppressWarnings ( "unchecked" )
	public YamlPathBuilder ( Object top )
	{
		params = ( Map<String,Object> ) top;
	}

	@Override
	public Path build ( ModelData modelData ) throws BuilderException
	{
		Path path = null;
		
		Boundary boundary = null;
		
		String name = null;
		
		if ( params.containsKey ( "Name" ) )
			name = params.get ( "Name" ).toString ();
		
		if ( params.containsKey ( "Boundary" ) )
		{
			Object bSpec = params.get ( "Boundary" );
			
			if ( bSpec.getClass ().equals ( String.class ) )
			{
				boundary = modelData.boundary ( bSpec.toString () );
				
				if ( boundary == null )
					throw new BuilderException ( "No Boundary named " + bSpec.toString () );
			}
			else
				boundary = new YamlBoundaryBuilder ( bSpec, modelData ).build();
		}
		else
			boundary = modelData.rootBoundary ();
		
		String type = params.get ( "Type" ).toString ();
		
		if ( type.equals ( "Stationary" ) )
		{
		    BindablePoint point = new BindablePoint ();
		    
            Value xValue = YamlValueBuilder.build ( params.get ( "X" ) );
            Value yValue = YamlValueBuilder.build ( params.get ( "Y" ) );
            
            final double x = xValue.value;
            final double y = yValue.value;
            final Boundary b = boundary;
            
            if ( xValue.isProportional )
                point.xProperty ().bind ( Bindings.createDoubleBinding ( () -> b.left () + x * b.width (), b.leftProperty (), b.widthProperty () ) );
            else
                point.xProperty ().bind ( Bindings.createDoubleBinding ( () -> b.left () + x, b.leftProperty () ) ); 
            
            if ( yValue.isProportional )
                point.yProperty ().bind ( Bindings.createDoubleBinding ( () -> b.top () + y * b.height (), b.topProperty (), b.heightProperty () ) );
            else
                point.yProperty ().bind ( Bindings.createDoubleBinding ( () -> b.top () + y, b.topProperty () ) ); 
            
            path = new StationaryPath ( boundary );
            path.locationProperty ().set ( point );
		}
		else if ( type.equals ( "Bouncing" ) )
		{
			double x = ( ( Number ) params.get ( "X" ) ).doubleValue ();
			double y = ( ( Number ) params.get ( "Y" ) ).doubleValue ();
			
			path = new BouncingPath ( boundary, x, y );
		}
		else if ( type.equals ( "RandomBouncing" ) )
		{
			double x = ( ( Number ) params.get ( "X" ) ).doubleValue ();
			double y = ( ( Number ) params.get ( "Y" ) ).doubleValue ();
			
			path = new RandomBouncingPath ( boundary, x, y );
		}
		else if ( type.equals ( "Polygon" ) )
		{
			@SuppressWarnings ( "unchecked" )
			List<Map<String,Object>> points = ( List<Map<String,Object>> ) params.get ( "Points" );
			
			List<Point> pl = new ArrayList<>();
			
			for ( Map<String,Object> point : points )
			{
	            BindablePoint p = new BindablePoint ();
	            
	            Value xValue = YamlValueBuilder.build ( point.get ( "X" ) );
	            Value yValue = YamlValueBuilder.build ( point.get ( "Y" ) );
	            
	            final double x = xValue.value;
	            final double y = yValue.value;
	            final Boundary b = boundary;
	            
	            if ( xValue.isProportional )
	                p.xProperty ().bind ( Bindings.createDoubleBinding ( () -> b.left () + x * b.width (), b.leftProperty (), b.widthProperty () ) );
	            else
	                p.xProperty ().bind ( Bindings.createDoubleBinding ( () -> b.left () + x, b.leftProperty () ) ); 
	            
	            if ( yValue.isProportional )
	                p.yProperty ().bind ( Bindings.createDoubleBinding ( () -> b.top () + y * b.height (), b.topProperty (), b.heightProperty () ) );
	            else
	                p.yProperty ().bind ( Bindings.createDoubleBinding ( () -> b.top () + y, b.topProperty () ) ); 

	            pl.add ( p );
			}
			
			double step = 1.0;
			
			if ( params.containsKey ( "Step" ) )
			{
			    Value stepValue = YamlValueBuilder.build ( params.get ( "Step" ) );
			    
			    if ( stepValue.isProportional )
			        step = stepValue.value;
			    else
			        step = 1.0 / stepValue.value;
			}
			
			path = new PolygonPath ( boundary, pl, step );
		}
		else if ( type.equals ( "Oval" ) )
		{
			double phi = ( ( Number ) params.get ( "Phi" ) ).doubleValue ();
			
			path = new OvalPath ( boundary, Math.toRadians ( phi ) );
		}
		else if ( type.equals ( "MidPoint" ) )
		{
			List<Path> paths = getPaths ( params.get ( "Paths" ), modelData );
			
			if ( paths.size () != 2 )
				throw new BuilderException ( "MidPoint Path must have exactly 2 Reference Paths, had " + paths.size () );
			
			path = new MidPointPath ( boundary, paths.get ( 0 ), paths.get ( 1 ) );
		}
		else if ( type.equals ( "Shadow" ) )
		{
			Path ref = pathRef ( params.get ( "Path" ), modelData );
			double x = ( ( Number ) params.get ( "X" ) ).doubleValue ();
			double y = ( ( Number ) params.get ( "Y" ) ).doubleValue ();
			
			path = new ShadowPath ( boundary, ref, x, y );
		}
		else if ( type.equals ( "InscribedCenter" ) )
		{
			List<Path> paths = getPaths ( params.get ( "Paths" ), modelData );
			
			if ( paths.size () != 3 )
				throw new BuilderException ( "InscribedCenter Path must have exactly 3 Reference Paths, had " + paths.size () );
			
			path = new InscribedCircleCenterPath ( boundary, paths.get ( 0 ), paths.get ( 1 ), paths.get ( 2 ) );
		}
		else if ( type.equals ( "OutscribedCenter" ) )
		{
			List<Path> paths = getPaths ( params.get ( "Paths" ), modelData );
			
			if ( paths.size () != 3 )
				throw new BuilderException ( "OutscribedCenter Path must have exactly 3 Reference Paths, had " + paths.size () );
			
			path = new OutscribedCircleCenterPath ( boundary, paths.get ( 0 ), paths.get ( 1 ), paths.get ( 2 ) );
		}
		else
			throw new BuilderException ( "Unknown Path Type: " + type );

		modelData.addPath ( path, name );
		return path;
	}

	private List<Path> getPaths ( Object spec, ModelData modelData ) throws BuilderException
	{
		List<Path> paths = new ArrayList<>();
		
		@SuppressWarnings ( "unchecked" )
		List<Object> specs = ( List<Object> ) spec;
		
		for ( Object pathRef : specs )
			paths.add ( pathRef ( pathRef, modelData ) );
		
		return paths;
	}
	
	private Path pathRef ( Object ref, ModelData modelData ) throws BuilderException
	{
		if ( ref.getClass ().equals ( String.class ) )
		{
			Path path = modelData.path ( ref.toString () );
			
			if ( path == null )
				throw new BuilderException ( "No Path named " + ref.toString () );
			
			return path;
		}
		
		return new YamlPathBuilder ( ref ).build ( modelData );
	}
	
	protected double percentage ( Object spec )
	{
		String s = spec.toString ();
		
		return Double.valueOf ( s.substring ( 0, s.length ()-1 ) ) / 100;
	}
}
