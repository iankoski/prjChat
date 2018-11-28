package server;

import java.net.Socket;

public interface ConnectionListener {
	public void connectionStablished(Socket socket);

}
