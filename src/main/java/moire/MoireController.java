package moire;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import moire.builders.ModelLoader;
import moire.shapes.Artist;


public class MoireController implements Initializable
{
	@FXML
	public MenuItem newMenuItem;
	
	@FXML
	public MenuItem openMenuItem;
	
	@FXML
	public MenuItem saveMenuItem;
	
	@FXML
	public MenuItem saveAsMenuItem;
	
	@FXML
	public MenuItem randomMenuItem;
	
	@FXML
	public MenuItem closeMenuItem;
	
	@FXML
	public MenuItem exitMenuItem;
	
	@FXML
	public MenuItem clearMenuItem;
	
	@FXML
	public Button startStopButton;
	
	@FXML
	public Button stepButton;
	
	@FXML
	public Slider speedSlider;
	
	@FXML
	public Slider lengthSlider;
	
	@FXML
	public AnchorPane canvasPane;
	
	@FXML
	public Canvas canvas;
	
	protected ModelLoader modelLoader;
	protected ObjectProperty<Model> modelProperty = new SimpleObjectProperty<Model>();
	protected BooleanProperty runningProperty = new SimpleBooleanProperty();
	protected ListChangeListener<List<Artist>> drawListener;
	protected ObjectProperty<Color> backgroundColorProperty = new SimpleObjectProperty<> ();
	
	protected ModelThread modelThread;
	
	public ObjectProperty<Model> modelProperty ()
	{
		return modelProperty;
	}

	public void setModelLoader ( ModelLoader modelLoader )
	{
		this.modelLoader = modelLoader;
	}
	
	@Override
	public void initialize ( URL location, ResourceBundle resources )
	{
		backgroundColorProperty.set ( Color.BLACK );
		
		canvas.widthProperty ().bind ( canvasPane.widthProperty () );
		canvas.heightProperty ().bind ( canvasPane.heightProperty () );
		
		canvas.widthProperty ().addListener ( ( c, o, n ) -> clear() );
		canvas.heightProperty ().addListener ( ( c, o, n ) -> clear() );
		
		modelProperty.addListener ( ( p, o, n ) -> 
		{			
			clear();

			if ( o != null )
			{
				o.backgroundColorProperty ().unbind ();
				o.widthProperty ().unbind ();
				o.heightProperty ().unbind ();
				o.lengthProperty ().unbind ();
				
				o.framesProperty().removeListener ( drawListener );
				drawListener = null;
			}
			
			if ( n != null )
			{
				n.backgroundColorProperty ().bind ( backgroundColorProperty );
				n.widthProperty ().bind ( canvas.widthProperty () );
				n.heightProperty ().bind ( canvas.heightProperty () );
				n.lengthProperty ().bind ( lengthSlider.valueProperty () );
				
				drawListener = c ->
				{
					while ( c.next() )
					{
						if ( c.wasPermutated () )
						{}
						else if ( c.wasUpdated () )
						{}
						else
						{
							Platform.runLater ( () ->
							{
								for ( List<Artist> frame : c.getRemoved () )
									for ( Artist artist : frame )
										artist.erase ( canvas.getGraphicsContext2D () );

								for ( List<Artist> frame : c.getAddedSubList () )
									for ( Artist artist : frame )
										artist.draw ( canvas.getGraphicsContext2D () );
							} );
						}
					}
				};
				
				n.framesProperty ().addListener ( drawListener );
			}
		} );
		
		modelThread = new ModelThread ();
		
		modelThread.modelProperty().bind ( modelProperty );
		modelThread.runningProperty ().bind ( runningProperty );
		modelThread.speedProperty ().bind ( speedSlider.valueProperty () );
		
		modelThread.start ();
		
		startStopButton.setOnAction ( e -> 
		{
			runningProperty.set ( !runningProperty.get () );
		} );
		
		startStopButton.textProperty ().bind ( Bindings.when ( runningProperty ).then ( "Stop" ).otherwise ( "Start" ) );
		
		startStopButton.disableProperty ().bind ( modelProperty.isNull () );
		
		stepButton.disableProperty ().bind ( Bindings.or ( modelProperty.isNull (), runningProperty ) );
		
		stepButton.setOnAction ( e -> 
		{
			if ( modelProperty.get () != null )
				modelProperty.get ().advance ();
		} );
		
		openMenuItem.setOnAction ( e ->
		{
				try
				{
		            FileChooser fileChooser = new FileChooser();
		            
		            fileChooser.setTitle ( "Select a Moire Model File" );
		            fileChooser.setInitialDirectory ( new File ( ClassLoader.getSystemResource ( "models" ).getPath () ) );
		            fileChooser.getExtensionFilters ().addAll (
		                    new FileChooser.ExtensionFilter ( "Model Files", "*.yaml" ),
		                    new FileChooser.ExtensionFilter ( "All Files", "*.*" )
		                    );
		            
		            File modelFile = fileChooser.showOpenDialog ( canvas.getScene ().getWindow () );
		            
		            if ( modelFile != null )
		            {
		                runningProperty.set ( false );
		                modelProperty.setValue ( modelLoader.load ( modelFile ).build () );
		            }
				}
				catch ( Exception exp )
				{
					exp.printStackTrace();
					Alert alert =  new Alert ( AlertType.ERROR, exp.getMessage (), ButtonType.OK );
					
					alert.showAndWait ();
				}
		} );
		
		closeMenuItem.setOnAction ( e -> 
		{
			runningProperty.set ( false );
			modelProperty.setValue ( null );
		} );

		closeMenuItem.disableProperty ().bind ( modelProperty.isNull () );

		clearMenuItem.setOnAction ( e -> 
		{
			clear();
		} );
				
		exitMenuItem.setOnAction ( e -> 
		{
			runningProperty.set ( false );
			Platform.exit ();
		} );		
	}
	
	protected void clear()
	{
		GraphicsContext gc = canvas.getGraphicsContext2D ();
		gc.setFill ( backgroundColorProperty.get () );
		gc.fillRect ( 0, 0, canvas.getWidth (), canvas.getHeight () );
	}
}
