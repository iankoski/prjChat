package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Server implements Runnable
{

	private int port;
	private boolean alive = false;
	private ConnectionListener connectionListener = null;
	
	
	public Server(int port) throws IOException
	{
		this.port = port;
	}
	
	public void close() 
	{
		alive = false;
	}
	public void setConnectionListener(ConnectionListener connectionListener) 
	{
		this.connectionListener = connectionListener;
	}

	@Override
	public void run() 
	{
		alive = true;
		System.out.println("Server is listen for connections!!");
		try(ServerSocket server = new ServerSocket(port))
		{
			server.setSoTimeout(60 * 10000);  
		
			while(alive) 
			{
				try 
				{
					Socket socket = server.accept();
					System.out.println("Connection recived " + socket.getInetAddress());
					this.connectionListener.connectionStablished(socket);
				}
				catch (SocketTimeoutException ste) 
				{
					ste.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
	}
}
