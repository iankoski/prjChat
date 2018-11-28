package client;

import java.io.IOException;

import common.Message;
import common.MessageType;
import common.NetSocket;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;


public class AppCliente extends Application
{
	ClientGUI ui = null;
	ClientController controller = null;
	
	public  AppCliente() {
		ui = new ClientGUI();
		controller = new ClientController();
		controller.setClientChatUI(ui);
		
	}
	@Override
	public void start(Stage stage) throws Exception 
	{
		stage.setScene(ui);
		stage.sizeToScene();
		stage.show();
		stage.setOnCloseRequest((we)-> System.exit(0));
		controller.go();
	}
	
	public static void main(String[] args) throws IOException
	{
		launch(args);
		
		/*
		NetSocket net = new NetSocket((msg)->System.out.println(msg.getContent()));
		
		net.connect("localhost", 4045);
		
		Message msg = new Message(MessageType.CONNECT, "eu", "você", "É VÉI");
		net.send(msg);
		
		msg = new Message(MessageType.DISCONNECT, "eu", "você", "É VÉI");
		net.send(msg);
		*/
	}
}
