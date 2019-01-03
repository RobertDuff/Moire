package moire.builders.yaml;

import java.util.Map;

import moire.ModelData;
import moire.boundaries.Boundary;
import moire.boundaries.Value;
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
		
		// Left
		
        Value left   = YamlValueBuilder.build ( params.get ( "Left" ) );
        Value top    = YamlValueBuilder.build ( params.get ( "Top" ) );
        Value right  = YamlValueBuilder.build ( params.get ( "Right" ) );
        Value bottom = YamlValueBuilder.build ( params.get ( "Bottom" ) );
        Value width  = YamlValueBuilder.build ( params.get ( "Width" ) );
        Value height = YamlValueBuilder.build ( params.get ( "Height" ) );
        
        Boundary parent = modelData.rootBoundary ();
        
        if ( params.containsKey ( "Parent" ) )
            parent = modelData.boundary ( params.get ( "Parent" ).toString () );
		
        Boundary boundary = new Boundary ( parent, left, top, right, bottom, width, height );
        
		modelData.addBoundary ( name, boundary );
		
		return boundary;
	}	
}
