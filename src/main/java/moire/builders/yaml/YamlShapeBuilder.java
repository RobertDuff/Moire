package moire.builders.yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.scene.paint.Color;
import moire.ModelData;
import moire.builders.BuilderException;
import moire.builders.ShapeBuilder;
import moire.colors.ColorSequence;
import moire.colors.SingleColorSequence;
import moire.paths.Path;
import moire.shapes.CircleShape;
import moire.shapes.CurveShape;
import moire.shapes.DotShape;
import moire.shapes.LineShape;
import moire.shapes.OvalShape;
import moire.shapes.PolygonShape;
import moire.shapes.RectangleShape;
import moire.shapes.Shape;
import moire.shapes.ShapeBase;
import moire.shapes.TargetShape;


public class YamlShapeBuilder implements ShapeBuilder
{
	protected Map<String,Object> params;
	
	@SuppressWarnings ( "unchecked" )
	public YamlShapeBuilder ( Object top )
	{
		params = ( Map<String,Object> ) top;
	}

	@Override
	public Shape build ( ModelData modelData ) throws BuilderException
	{
		ShapeBase shape = null;
		
		// Color
		
		ColorSequence color = null;
		Object cSpec = params.get ( "Color" );
		
		if ( cSpec == null )
			throw new BuilderException ( "Shapes must specify a color sequence" );
		
		if ( cSpec.getClass ().equals ( String.class ) )
		{
			color = modelData.colorSequence ( cSpec.toString () );
			
			if ( color == null )
				color = new SingleColorSequence ( Color.web ( cSpec.toString () ) );
		}
		else
			color = new YamlColorSequenceBuilder ( cSpec ).build ( modelData );
		
		// Paths
		
		List<Path> paths = getPaths ( modelData );
		
		// Shape
		
		String type = params.get ( "Type" ).toString ();
		
        if ( type.equals ( "Target" ) )
        {           
            if ( paths.size () != 1 )
                throw new BuilderException ( "Target Shape must have 1 path, had " + paths.size () );
            
            TargetShape ts = new TargetShape ();
            shape = ts;
            
            if ( params.containsKey ( "Radius" ) )
                ts.circleRadiusProperty ().setValue ( ( Number ) params.get ( "Radius" ) );
            
            if ( params.containsKey ( "Length" ) )
                ts.lineLengthProperty ().setValue ( ( Number ) params.get ( "Length" ) );
        }
        else if ( type.equals ( "Dot" ) )
        {           
            if ( paths.size () != 1 )
                throw new BuilderException ( "Dot Shape must have 1 path, had " + paths.size () );
            
            DotShape ds = new DotShape ();
            shape = ds;
            
            if ( params.containsKey ( "Radius" ) )
                ds.radiusProperty ().setValue ( ( Number ) params.get ( "Radius" ) );
        }
		else if ( type.equals ( "Line" ) )
		{
			if ( paths.size () != 2 )
				throw new BuilderException ( "Line Shape must have 2 paths, had " + paths.size () );
			
			shape = new LineShape ();
		}
		else if ( type.equals ( "Rectangle" ) )
		{
			if ( paths.size () != 2 )
				throw new BuilderException ( "Rectangle Shape must have 2 paths, had " + paths.size () );
			
			shape = new RectangleShape ();
		}
		else if ( type.equals ( "Oval" ) )
		{
			if ( paths.size () != 2 )
				throw new BuilderException ( "Oval Shape must have 2 paths, had " + paths.size () );
			
			shape = new OvalShape ();
		}
		else if ( type.equals ( "Polygon" ) )
		{
			if ( paths.size () < 3 )
				throw new BuilderException ( "Polygon Shape must have at least 3 paths, had " + paths.size () );
			
			shape = new PolygonShape ();
		}
		else if ( type.equals ( "Circle" ) )
		{
			CircleShape cs = new CircleShape();
			shape = cs;
			
			switch ( paths.size () )
			{
				case 1:
					
					if ( !params.containsKey ( "Radius" ) )
						throw new BuilderException ( "A Circle Shape with only 1 path must specify a Radius" );
					
					cs.radiusProperty ().setValue ( ( Number ) params.get ( "Radius" ) );
					
					break;
					
				case 2: // Nothing more to do.
					break;
					
				default:
					throw new BuilderException ( "Circle Shape must have 1 or 2 paths, had " + paths.size () );
			}
		}
		else if ( type.equals ( "Curve" ) )
		{
			if ( paths.size () != 4 )
				throw new BuilderException ( "Curve Shape must have 4 paths, had " + paths.size () );
			
			shape = new CurveShape ();
		}
		else
			throw new BuilderException ( "Unknown Shape Type: " + type );
		
		shape.colorProperty ().bind ( color.colorProperty () );
		
		for ( Path path : paths )
			shape.newPointProperty ().bind ( path.locationProperty () );
		
		modelData.addShape ( shape );
		
		return shape;
	}

	private List<Path> getPaths ( ModelData modelData ) throws BuilderException
	{
		List<Path> paths = new ArrayList<>();
		
		if ( params.containsKey ( "Path" ) )
			paths.add ( pathRef ( params.get ( "Path" ), modelData ) );
		else
		{
			@SuppressWarnings ( "unchecked" )
			List<Object> specs = ( List<Object> ) params.get ( "Paths" );

			for ( Object pathRef : specs )
				paths.add ( pathRef ( pathRef, modelData ) );
		}
		
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
}
