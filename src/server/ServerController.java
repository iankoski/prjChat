package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import common.Message;
import common.MessageType;
import common.NetSocket;

public class ServerController implements ConnectionListener, ChatController 
{

	private Map<Integer, HandlerClient> mapId = new Hashtable<>();
	private Map<String, HandlerClient> mapNick = new Hashtable<>();
	private static int idCount = 0;
	Server server = null;
	
	public void start(int port) throws IOException
	{
		server = new Server(port);
		server.setConnectionListener(this);
		new Thread(server).start();
	}
	
	public void shuttDown()
	{
		server.close();
		// Notificar todos os que estiverem conversando;
		mapNick.values().forEach((hc) -> hc.receive(new Message(MessageType.DISCONNECT, "SERVER", hc.nick, "Server SHUTDOWN")));
		
	}
	
	

	@Override
	public void send(Message msg) 
	{
		if(msg.getType() == MessageType.SEND_TO) 
		{
			HandlerClient hc = mapNick.get(msg.getDestinatary());
			hc.receive(msg);
			return;
		}
		
		synchronized (mapNick) 
		{
			Collection<HandlerClient> lst = mapNick.values();
			lst.forEach((hc)->hc.receive(msg));
			
		}
	}

	@Override
	public void disconnect(int id) 
	{

		HandlerClient hc;
		synchronized (mapId) 
		{
			hc = mapId.remove(id);
		}
		synchronized (mapId) 
		{
			mapNick.remove(hc.getNick());
		}
		
		notifyDisconnection(hc.getNick());
	}
	
	private void notifyDisconnection(String nick) 
	{
		synchronized (mapNick) 
		{
			 mapNick.values().forEach((hc)->hc.refreshContact(nick, false));			
		}
	}

	@Override
	public void identify(int id, String nick) 
	{
		
		synchronized (mapNick) {
			mapNick.values().forEach((hc)->hc.refreshContact(nick, true));
		}
		HandlerClient hc = mapId.get(id);
		hc.refreshContactList(new ArrayList<String>(mapNick.keySet()));
		mapNick.put(nick, mapId.get(id));
		
	}

}
