package server;

import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class ServerUI extends BorderPane
{
	TextField tfPort;
	Button bActive;
	Button bShuttdown;
	TableView<HandlerClient> tvUsers;
	ServerController controller = null;
	
	public ServerUI(ServerController controller) 
	{
		this.controller = controller;
		createGUI();
		bActive.setOnAction((ae)->activeHandler());
		bShuttdown.setOnAction((ae)->shuttdownHundler());
	}
	
	private void createGUI()
	{
		GridPane gp = new GridPane();
		gp.add(new Label("Port"), 0, 0, 2, 1);
		tfPort = new TextField("4045");
		gp.add(tfPort, 0, 0, 2, 1);
		
		bActive = new Button("Activate ");
		bActive.setMinWidth(80);
		gp.add(bActive, 2, 0);
		
		bShuttdown = new Button("Shuttdown");
		bShuttdown.setMinWidth(80);
		bShuttdown.setDisable(true);
		gp.add(bShuttdown, 2, 1);
		
		super.setTop(gp);
		
		tvUsers = new TableView();
		
		super.setCenter(tvUsers);
	}
	
	private void activeHandler()
	{
		try
		{
			int port = Integer.parseInt(tfPort.getText());
			controller.start(port);
			bActive.setDisable(true);
			bShuttdown.setDisable(false);
		}
		catch(NumberFormatException nfe)
		{
			tfPort.selectAll();
			Platform.runLater(()->tfPort.requestFocus());
		}
		catch(IOException e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
	
	private void shuttdownHundler()
	{
		controller.shuttDown();
	}
}
