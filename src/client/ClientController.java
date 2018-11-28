package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import common.Message;
import common.MessageType;
import common.NetSocket;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class ClientController 
{
	private NetSocket net = new NetSocket(this::messageReceiver);
	private ClientChatUI chatUI = null;
	private String nick = null;
	public void setClientChatUI(ClientChatUI ui)
	{
		this.chatUI = ui;
		ui.setOnSendAction((e)->bSendAction());
	}
	
	@SuppressWarnings("unchecked")
	private void messageReceiver(Message msg)
	{
		switch(msg.getType())
		{
		case CONNECTED_USERS:
			chatUI.refreshConnectedUsers((ArrayList<String>)msg.getContent());
			break;
		case ADD_CONTACT:
			chatUI.addContact(msg.getContent());
			break;
		case REMOVE_CONTACT:
			chatUI.removeContact(msg.getContent());
			break;
		default:
			chatUI.showMessage(msg.getSender(), msg.getContent());
			break;
		}
	}
	
	private boolean connect()
	{
		try 
		{
			net.connect("localhost", 4045);
			return true;
		} 
		catch (IOException e) 
		{
			showErrorMessage(e.getMessage());
			return false;
		}
	}
	
	public void showErrorMessage(String txt)
	{
		Dialog<String> error = new Dialog<String>();
		error.setContentText("Connection Error: " + txt);
		error.getDialogPane().getButtonTypes().add(ButtonType.OK);
		error.showAndWait();
	}
	
	public void go()
	{
		if(!connect())
			System.exit(0);
		
		RequestNickDialog nickDialog = new RequestNickDialog();
		Optional<String> result = nickDialog.showAndWait();
		
		if(!result.isPresent())
			System.exit(0);
		this.nick = result.get();
		sendMessage(new Message(MessageType.CONNECT, null, null, nick));
		
	}
	
	private void sendMessage(Message msg) {
		try 
		{
			net.send(msg);
		} 
		catch (IOException e) 
		{
			showErrorMessage(e.getMessage());
			System.exit(0);
		}
	}
	private void bSendAction() {
		
		sendMessage(new Message(MessageType.SEND_TO, this.nick, chatUI.getDestinatary(), chatUI.getMessage()));
		
	}
}
