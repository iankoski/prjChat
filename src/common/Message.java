package common;

import java.io.Serializable;

public final class Message implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String sender;
	private String destinatary;
	private Object content;
	private MessageType type;
	
	public Message(MessageType type, String sender, String destinatary, Object content) 
	{

		this.sender = sender;
		this.destinatary = destinatary;
		this.content = content;
		this.type = type;
	}

	public String getSender() {
		return sender;
	}

	public String getDestinatary() {
		return destinatary;
	}
	@SuppressWarnings("uncheked")
	public <E> E getContent() {
		return (E)content;
	}

	public MessageType getType() {
		return type;
	}
	
	

}


