package moire.builders.yaml;

import java.util.Map;

import moire.ModelData;
import moire.boundaries.Boundary;
import moire.builders.BuilderException;

public class YamlBoundaryBuilder extends moire.builders.BoundaryBuilder
{
	protected String boundaryName;
	protected ModelData modelData;
	
	@SuppressWarnings ( "unchecked" )
	public YamlBoundaryBuilder ( Object top, ModelData data ) throws BuilderException
	{
	    modelData = data;
	    
	    Map<String,Object> params = ( Map<String,Object> ) top;
		
        if ( params.containsKey ( "Name" ) )
            boundaryName = params.get ( "Name" ).toString ();
        
        Boundary parent = modelData.rootBoundary ();
        
        if ( params.containsKey ( "Parent" ) )
            parent = modelData.boundary ( params.get ( "Parent" ).toString () );

        parent ( parent );
        
        left   ( YamlValueBuilder.build ( params.get ( "Left"   ) ) );
        top    ( YamlValueBuilder.build ( params.get ( "Top"    ) ) );
        right  ( YamlValueBuilder.build ( params.get ( "Right"  ) ) );
        bottom ( YamlValueBuilder.build ( params.get ( "Bottom" ) ) );
        width  ( YamlValueBuilder.build ( params.get ( "Width"  ) ) );
        height ( YamlValueBuilder.build ( params.get ( "Height" ) ) );
	}

	@Override
	public Boundary build () throws BuilderException
	{	    		
        Boundary boundary = super.build();
        
        if ( boundaryName != null )
            modelData.addBoundary ( boundaryName, boundary );
		
		return boundary;
	}	
}
