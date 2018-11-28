package server;

import java.io.IOException;

public class ServerApp 
{
	public static void main(String[] args) throws IOException {
		new Thread(new Server(4045)).start();
	}
}
