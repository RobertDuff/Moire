package moire;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.paint.Color;
import moire.colors.ColorSequence;
import moire.paths.Path;
import moire.shapes.Artist;
import moire.shapes.Shape;
import moire.shapes.ShapeBase;

public class Model
{
	protected ObjectProperty<Color> backgroundColorProperty = new SimpleObjectProperty<> ();
	protected IntegerProperty lengthProperty = new SimpleIntegerProperty ();
	
	ModelData data;
	
	protected	ListProperty<List<Artist>> framesProperty = new SimpleListProperty<>( FXCollections.observableArrayList () );
	
	public Model ( ModelData data )
	{
		this.data = data;
		
		ShapeBase.backgroundColorProperty ().bind ( backgroundColorProperty );
	}
	
	public ObjectProperty<Color> backgroundColorProperty()
	{
		return backgroundColorProperty;
	}

	public IntegerProperty lengthProperty ()
	{
		return lengthProperty;
	}

	public ListProperty<List<Artist>> framesProperty ()
	{
		return framesProperty;
	}
	
	public DoubleProperty widthProperty ()
	{
		return data.topBoundary.widthProperty();
	}

	public DoubleProperty heightProperty ()
	{
		return data.topBoundary.heightProperty ();
	}

	public void advance()
	{
		for ( Path path : data.allPaths )
			path.advance ();
		
		for ( ColorSequence color : data.allColorSequences )
			color.advance ();
		
		List<Artist> frame = new ArrayList<>();
		
		for ( Shape shape : data.allShapes )
			frame.add ( shape.artist () );
		
		if ( framesProperty ().get ().size () >= lengthProperty.get () )
			framesProperty ().get ().remove ( 0, framesProperty ().get ().size () - lengthProperty.get () + 1 );
		
		framesProperty.get ().add ( frame );
	}
	
	@Override
	public String toString ()
	{
		return "Model [lengthProperty=" + lengthProperty + ", data=" + data
				+ ", framesProperty=" + framesProperty + "]";
	}
}
