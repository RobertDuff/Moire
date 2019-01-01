import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.yaml.snakeyaml.Yaml;

import moire.builders.BuilderException;
import moire.builders.yaml.YamlModelBuilder;


public class ModelBuilderTest
{
	public static class MsgMatcher extends BaseMatcher<String>
	{
		public String regex;
		public Pattern pattern;
		
		public MsgMatcher ( String regex )
		{
			this.regex = regex;
			pattern = Pattern.compile ( regex );
		}
		
		@Override
		public boolean matches ( Object item )
		{
			java.util.regex.Matcher m = pattern.matcher ( item.toString () );
			return m.find ();
		}

		@Override
		public void describeTo ( Description description )
		{
			description.appendText ( "Should match: " + regex );
		}
	}
	
	public static final String YAML_FILE = "ModelBuilderTest.yaml";
	public static Map<String,Object> specs = new HashMap<>();
	
	@SuppressWarnings ( "unchecked" )
	@BeforeClass
	public static void beforeClass()
	{
		specs = ( Map<String,Object> ) new Yaml().load ( ClassLoader.getSystemResourceAsStream ( YAML_FILE ) );
	}

	@Rule
	public ExpectedException exp = ExpectedException.none();
	
	@Test
	public void testBadBoundaryType () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Unknown Boundary Type" ));
		new YamlModelBuilder ( specs.get ( "BadBoundaryType" ) ).build ();
	}

	@Test
	public void testBadColorType () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Unknown Color Sequence Type" ));
		new YamlModelBuilder ( specs.get ( "BadColorType" ) ).build ();
	}

	@Test
	public void testBadPathType () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Unknown Path Type" ));
		new YamlModelBuilder ( specs.get ( "BadPathType" ) ).build ();
	}

	@Test
	public void testBadShapeType () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Unknown Shape Type" ));
		new YamlModelBuilder ( specs.get ( "BadShapeType" ) ).build ();
	}

	@Test
	public void testBadBoundaryName () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "No Boundary named" ));
		new YamlModelBuilder ( specs.get ( "BadBoundaryName" ) ).build ();
	}
	
	@Test
	public void testBadColorName () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Model Build Failed" ));
		new YamlModelBuilder ( specs.get ( "BadColorName" ) ).build ();
	}
	
	@Test
	public void testBadPathNameInPath () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "No Path named" ));
		new YamlModelBuilder ( specs.get ( "BadPathNameInPath" ) ).build ();
	}
	
	@Test
	public void testBadPathNameInShape () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "No Path named" ));
		new YamlModelBuilder ( specs.get ( "BadPathNameInShape" ) ).build ();
	}
	
	@Test
	public void testMissingRadius () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Radius" ));
		new YamlModelBuilder ( specs.get ( "MissingRadius" ) ).build ();
	}
	
	@Test
	public void testPathCountForCircle () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Circle Shape must have" ));
		new YamlModelBuilder ( specs.get ( "PathCountForCircle" ) ).build ();
	}
	
	@Test
	public void testPathCountForCurve () throws BuilderException, IOException
	{
		exp.expect ( BuilderException.class );
		exp.expect ( new MsgMatcher ( "Curve Shape must have" ));
		new YamlModelBuilder ( specs.get ( "PathCountForCurve" ) ).build ();
	}	
}
