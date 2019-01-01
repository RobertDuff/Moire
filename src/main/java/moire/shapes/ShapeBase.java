package moire.shapes;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;
import utility.geometry.Point;

public abstract class ShapeBase implements Shape
{
	protected static ObjectProperty<Color> backgroundColorProperty = new SimpleObjectProperty<Color>();

	public static ObjectProperty<Color> backgroundColorProperty()
	{
		return backgroundColorProperty;
	}
	
	protected ObjectProperty<Color> colorProperty = new SimpleObjectProperty<Color>();
	protected List<ObjectProperty<Point>> pointProperties = new ArrayList<>();

	public ObjectProperty<Color> colorProperty()
	{
		return colorProperty;
	}

	public List<ObjectProperty<Point>> pointProperties ()
	{
		return pointProperties;
	}

	public ObjectProperty<Point> newPointProperty()
	{
		ObjectProperty<Point> prop = new SimpleObjectProperty<>();
		pointProperties.add ( prop );
		
		return prop;
	}
}
