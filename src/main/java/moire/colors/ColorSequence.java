package moire.colors;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.paint.Color;

public abstract class ColorSequence
{
	protected ObjectProperty<Color> colorProperty = new SimpleObjectProperty<>();
	
	public ObjectProperty<Color> colorProperty()
	{
		return colorProperty;
	}
	
	public abstract void advance();
}
