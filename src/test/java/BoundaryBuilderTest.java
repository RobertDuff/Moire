import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import moire.ModelData;
import moire.boundaries.Boundary;
import moire.builders.BuilderException;
import moire.builders.yaml.YamlBoundaryBuilder;


public class BoundaryBuilderTest
{
	public static final double TOLERANCE = 0.00000001;
	public static final Yaml yaml = new Yaml();
	
	ModelData modelData;
	
	@Before
	public void before() throws BuilderException
	{
		modelData = new ModelData();
		
		String yamlText = "{ Name: X, Left: 25, Top: 25, Right: 25, Bottom: 25 }";
		Object spec = yaml.load ( yamlText );
		
		new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		small();
	}
	
	private void small()
	{
		modelData.topBoundary.widthProperty ().set ( 100 );
		modelData.topBoundary.heightProperty().set ( 200 );
	}
	
	private void big()
	{
		modelData.topBoundary.widthProperty ().set ( 1000 );
		modelData.topBoundary.heightProperty().set ( 2000 );
	}
	
	@Test
	public void testTop()
	{
		assertEquals (   0, modelData.topBoundary().leftBorder (),   TOLERANCE );
		assertEquals (   0, modelData.topBoundary().topBorder (),    TOLERANCE );
		assertEquals ( 100, modelData.topBoundary().rightBorder (),  TOLERANCE );
		assertEquals ( 200, modelData.topBoundary().bottomBorder (), TOLERANCE );
		assertEquals ( 100, modelData.topBoundary().width (),        TOLERANCE );
		assertEquals ( 200, modelData.topBoundary().height (),       TOLERANCE );
		
		big();
		
		assertEquals (    0, modelData.topBoundary().leftBorder (),   TOLERANCE );
		assertEquals (    0, modelData.topBoundary().topBorder (),    TOLERANCE );
		assertEquals ( 1000, modelData.topBoundary().rightBorder (),  TOLERANCE );
		assertEquals ( 2000, modelData.topBoundary().bottomBorder (), TOLERANCE );
		assertEquals ( 1000, modelData.topBoundary().width (),        TOLERANCE );
		assertEquals ( 2000, modelData.topBoundary().height (),       TOLERANCE );
	}
	
	@Test
	public void testFullSize () throws BuilderException
	{
		String yamlText = "{ Left: 0, Top: 0, Right: 0, Bottom: 0 }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals (   0, boundary.leftBorder (),   TOLERANCE );
		assertEquals (   0, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 100, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 200, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals (    0, boundary.leftBorder (),   TOLERANCE );
		assertEquals (    0, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 1000, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 2000, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testInsets () throws BuilderException
	{
		String yamlText = "{ Left: 10, Top: 30, Right: 20, Bottom: 40 }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals (  10, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  30, boundary.topBorder (),    TOLERANCE );
		assertEquals (  80, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 160, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals (   10, boundary.leftBorder (),   TOLERANCE );
		assertEquals (   30, boundary.topBorder (),    TOLERANCE );
		assertEquals (  980, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 1960, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testProportionalInsets () throws BuilderException
	{
		String yamlText = "{ Left: 10%, Top: 30%, Right: 20%, Bottom: 40% }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals (  10, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  60, boundary.topBorder (),    TOLERANCE );
		assertEquals (  20, boundary.rightBorder (),  TOLERANCE );
		assertEquals (  80, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals ( 100, boundary.leftBorder (),   TOLERANCE );
		assertEquals ( 600, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 200, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 800, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testSized () throws BuilderException
	{
		String yamlText = "{ Left: 10, Top: 20, Width: 30, Height: 40 }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals ( 10, boundary.leftBorder (),   TOLERANCE );
		assertEquals ( 20, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 40, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 60, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals ( 10, boundary.leftBorder (),   TOLERANCE );
		assertEquals ( 20, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 40, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 60, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testSizedProportional () throws BuilderException
	{
		String yamlText = "{ Left: 10%, Top: 20%, Width: 30%, Height: 40% }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals (  10, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  40, boundary.topBorder (),    TOLERANCE );
		assertEquals (  40, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 120, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals (  100, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  400, boundary.topBorder (),    TOLERANCE );
		assertEquals (  400, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 1200, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testFullInX () throws BuilderException
	{
		String yamlText = "{ Parent: X, Left: 0, Top: 0, Right: 0, Bottom: 0 }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals (  25, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  25, boundary.topBorder (),    TOLERANCE );
		assertEquals (  75, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 175, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals (   25, boundary.leftBorder (),   TOLERANCE );
		assertEquals (   25, boundary.topBorder (),    TOLERANCE );
		assertEquals (  975, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 1975, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testInsetsInX () throws BuilderException
	{
		String yamlText = "{ Parent: X, Left: 10, Top: 30, Right: 20, Bottom: 40 }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals (  35, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  55, boundary.topBorder (),    TOLERANCE );
		assertEquals (  55, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 135, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals (   35, boundary.leftBorder (),   TOLERANCE );
		assertEquals (   55, boundary.topBorder (),    TOLERANCE );
		assertEquals (  955, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 1935, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testProportionalInsetsInX () throws BuilderException
	{
		String yamlText = "{ Parent: X, Left: 10%, Top: 30%, Right: 20%, Bottom: 40% }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals ( 30, boundary.leftBorder (),   TOLERANCE );
		assertEquals ( 70, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 35, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 85, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals ( 120, boundary.leftBorder (),   TOLERANCE );
		assertEquals ( 610, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 215, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 805, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testSizedInX () throws BuilderException
	{
		String yamlText = "{ Parent: X, Left: 10, Top: 20, Width: 30, Height: 40 }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals ( 35, boundary.leftBorder (),   TOLERANCE );
		assertEquals ( 45, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 65, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 85, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals ( 35, boundary.leftBorder (),   TOLERANCE );
		assertEquals ( 45, boundary.topBorder (),    TOLERANCE );
		assertEquals ( 65, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 85, boundary.bottomBorder (), TOLERANCE );
	}
	
	@Test
	public void testSizedProportionalInX () throws BuilderException
	{
		String yamlText = "{ Parent: X, Left: 10%, Top: 20%, Width: 30%, Height: 40% }";
		Object spec = yaml.load ( yamlText );
		
		Boundary boundary = new YamlBoundaryBuilder ( spec ).build ( modelData );
		
		assertEquals (  30, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  55, boundary.topBorder (),    TOLERANCE );
		assertEquals (  45, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 115, boundary.bottomBorder (), TOLERANCE );
		
		big();
		
		assertEquals (  120, boundary.leftBorder (),   TOLERANCE );
		assertEquals (  415, boundary.topBorder (),    TOLERANCE );
		assertEquals (  405, boundary.rightBorder (),  TOLERANCE );
		assertEquals ( 1195, boundary.bottomBorder (), TOLERANCE );
	}
}
