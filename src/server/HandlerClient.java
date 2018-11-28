package server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


import common.Message;
import common.MessageType;
import common.NetSocket;

public class HandlerClient 
{
	String nick;
	int id;
	NetSocket netSocket;
	ChatController controller = null;

	public HandlerClient(int id, Socket socket) 
	{
		this.id = id;
		this.netSocket = new NetSocket(socket, this::processMessage);
	}

	public void setController(ChatController controller) 
	{
		this.controller = controller;
	}

	private void disconnect() 
	{
		controller.disconnect(this.id);
		netSocket.close();
		System.out.println(id + " was disconnected");
	}
	
	public void receive(Message msg) 
	{
		sendToFront(msg);
	}
	
	private void sendToFront(Message msg) {
		try {
			netSocket.send(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void refreshContact(String nick, boolean active) 
	{
		
		sendToFront(new Message(active ? MessageType.ADD_CONTACT : MessageType.REMOVE_CONTACT, "Server", this.nick, nick)); 
		
		
	}
	
	public void refreshContactList(ArrayList<String> contactList) 
	{
		sendToFront(new Message(MessageType.CONNECTED_USERS, "Server", this.nick, contactList)); 
		
	}

	private void processMessage(Message msg) 
	{
		switch (msg.getType()) 
		{
		case CONNECT:
			nick = (String) msg.getContent();
			controller.identify(this.id, nick);
			System.out.println(id +" " + nick + " connected");
			break;
		case DISCONNECT:
			disconnect();
			break;
		case SEND_ALL:
			break;
		case SEND_TO:
			controller.send(msg);
			break;
		default:
			

		}
	}

	public String getNick() 
	{
		return nick;
	}

	public int getId() 
	{
		return id;
	}
	
	

}
