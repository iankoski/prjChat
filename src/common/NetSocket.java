package common;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class NetSocket {
	private Socket socket = null;
	private ObjectOutputStream oos = null;

	
	private MessageReceiver messageReceiver = null;
	
	public NetSocket() {}
	
	public NetSocket(MessageReceiver mr) {
		this.messageReceiver = mr;
	}

	public NetSocket(Socket socket, MessageReceiver mr) {
		this.socket = socket;
		this.setMessageReceiver(mr);
		new Thread(new MessageReader()).start();
	}

	public void setMessageReceiver(MessageReceiver messageReceiver) {
		this.messageReceiver = messageReceiver;
	}

	public void connect(String host, int port) throws UnknownHostException, IOException {
		this.socket = new Socket(host, port);
		new Thread(new MessageReader()).start();
	}
	public void close() 
	{
		try 
		{
			this.socket.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public void send(Message msg) throws IOException 
	{
		if(oos == null) 
			oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(msg);
		oos.flush();
	}
	
	private class MessageReader implements Runnable 
	{
		
		
		@Override
		public void run() 
		{
			try(ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) 
			{
				while(!socket.isClosed()) 
				{
					try
					{
						Message msg =  (Message) ois.readObject();
						// Notifica destinatario
						messageReceiver.push(msg);
					}
					catch(SocketException e)
					{
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					} 
					catch (ClassNotFoundException e) 
					{
						e.printStackTrace();
					}
				}
			}
			catch(EOFException e) 
			{
				System.out.println("EOF");
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
