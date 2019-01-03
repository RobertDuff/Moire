package moire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import moire.builders.ModelLoader;
import moire.builders.yaml.YamlModelLoader;


public class Moire extends Application
{
	public static final String FXML_FILE = "Moire.fxml";
	
	@Override
	public void start ( Stage primaryStage ) throws Exception
	{
		FXMLLoader loader = new FXMLLoader ( ClassLoader.getSystemResource ( FXML_FILE ) );
		Parent parent = loader.load ();
		
		MoireController controller = loader.getController ();
		
		ModelLoader modelLoader = new YamlModelLoader ();
		controller.setModelLoader ( modelLoader );
		
		primaryStage.titleProperty ().bind ( controller.titleProperty () );
		
		Scene scene = new Scene ( parent, 1400, 1000 );
		primaryStage.setScene ( scene );
		primaryStage.show ();
	}

	public static void main ( String[] args )
	{
		launch ( args );
	}
}
