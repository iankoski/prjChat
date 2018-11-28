package server;

import common.Message;

public interface ChatController {
public void send(Message msg);
public void disconnect(int id);
public void identify(int id, String nick);
}
