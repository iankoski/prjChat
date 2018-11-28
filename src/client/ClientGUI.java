package client;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

public class ClientGUI extends Scene implements ClientChatUI
{
	private TextArea txtArea = new TextArea();
	private ObservableList<String> listOfContacts = FXCollections.observableArrayList();
	private ListView<String> listView = new ListView<>(listOfContacts);
	private TextField txtMsg = new TextField();
	private Button bSend = null;
	
	public ClientGUI()
	{
		super(new BorderPane());
		createGui();
	}

	private void createGui() 
	{
		BorderPane root = (BorderPane) super.getRoot();
		ScrollPane sp = new ScrollPane(txtArea);
		txtArea.setPrefSize(386, 1000);
		sp.setPrefSize(400, 600);
		root.setCenter(sp);
		
		root.setRight(listView);
		
		bSend = new Button("SEND");
		
		FlowPane fp = new FlowPane();
		fp.setHgap(10);
		txtMsg.setPrefSize(550, 30);
		fp.getChildren().addAll(txtMsg, bSend);
		
		root.setBottom(fp);
	}

	@Override
	public void refreshConnectedUsers(ArrayList<String> lst) 
	{
		Platform.runLater(()->{
			listOfContacts.clear();
			listOfContacts.addAll(lst);
			
		});
		
	}

	@Override
	public void addContact(String nick) {
		Platform.runLater(()->listOfContacts.add(nick));
		
		
		
	}

	@Override
	public void removeContact(String nick) {
		Platform.runLater(()->listOfContacts.remove(nick));
		
	}

	@Override
	public void setOnSendAction(EventHandler<ActionEvent> handler) {
		bSend.setOnAction(handler);
		
	}

	@Override
	public String getMessage() {
		
		return this.txtMsg.getText();
	}

	@Override
	public String getDestinatary() {
		
		return this.listView.getSelectionModel().getSelectedItem();
	}

	@Override
	public void showMessage(String sender, String content) {
		txtArea.appendText(sender + " : " + content);
	}

	
}
