package moire.builders.yaml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import moire.boundaries.Value;
import moire.builders.BuilderException;

public class YamlValueBuilder
{
    public static final Pattern PERCENTAGE_PATTERN = Pattern.compile ( "\\s*(\\d+)\\s*%\\s*" );
    public static final Pattern FRACTION_PATTERN = Pattern.compile ( "\\s*(\\d+)\\s*/\\s*(\\d+)\\s*" );
    
    public static Value build ( Object spec ) throws BuilderException
    {
        if ( spec == null )
            return null;
        
        if ( String.class.isAssignableFrom ( spec.getClass () ) )
        {
            Matcher m;
            
            m = PERCENTAGE_PATTERN.matcher ( ( String  ) spec );
            
            if ( m.matches () )
                return new Value ( Double.valueOf ( m.group ( 1 ) ) / 100, true );
            
            m = FRACTION_PATTERN.matcher ( ( String ) spec );
            
            if ( m.matches () )
                return new Value ( Double.valueOf ( m.group ( 1 ) ) / Double.valueOf ( m.group ( 2 ) ), true ) ;
        }
        else if ( Number.class.isAssignableFrom ( spec.getClass () ) )
            return new Value ( ( ( Number ) spec ).doubleValue () );
        
        throw new BuilderException ( "Cannot interpret Value: " + spec.toString () );
    }
}
