package client;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public interface ClientChatUI 
{
	public void refreshConnectedUsers(ArrayList<String> lst);
	public void addContact(String nick);
	public void removeContact(String nick);
	public void setOnSendAction(EventHandler<ActionEvent> handler);
	public String getMessage();
	public String getDestinatary();
	public void showMessage(String sender, String content);
	
}
