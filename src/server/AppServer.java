package server;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppServer extends Application
{
	ServerController controller = null;
	ServerUI serverUI = null;
	
	public AppServer()
	{
		//Nothing;
	}
	
	@Override
	public void init()
	{
		controller = new ServerController();
		serverUI = new ServerUI(controller);
	}

	@Override
	public void start(Stage stage) throws Exception 
	{
		stage.setTitle("Chat by UTFPR Dumies");
		Scene scene = new Scene(serverUI);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		stage.setOnCloseRequest((we) -> System.exit(0));
		
		
	}
	
	public static void main(String[] args) throws IOException 
	{
		launch();
	}
}
