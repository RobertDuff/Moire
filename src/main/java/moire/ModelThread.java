package moire;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ModelThread extends Thread
{
	protected ObjectProperty<Model> modelProperty = new SimpleObjectProperty<> ();
	protected DoubleProperty speedProperty = new SimpleDoubleProperty ();
	protected BooleanProperty runningProperty = new SimpleBooleanProperty();

	public ModelThread ()
	{
		setDaemon ( true );
	}

	public ObjectProperty<Model> modelProperty()
	{
		return modelProperty;
	}
	
	public DoubleProperty speedProperty()
	{
		return speedProperty;
	}
	
	public BooleanProperty runningProperty()
	{
		return runningProperty;
	}

	@Override
	public void run ()
	{
		while ( !isInterrupted () )
		{
			long delay = 102 - speedProperty.longValue ();
			
			try
			{
				Thread.sleep ( delay );
				
				if ( !runningProperty.get () )
					continue;
				
				Model model = modelProperty.get ();
				
				if ( model == null )
					continue;
				
				model.advance ();
			}
			catch ( InterruptedException e )
			{
				e.printStackTrace();
			}
		}
	}	
}
