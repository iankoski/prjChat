package client;

import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class RequestNickDialog extends Dialog<String>
{
	TextField txtNick = new TextField();
	ButtonType loginButtonType = new ButtonType("OK", ButtonData.OK_DONE);
	
	public RequestNickDialog()
	{
		super();
		super.setTitle("What's ur NickName ?");
		txtNick.setPromptText("Your NickName");
		
		GridPane gp = new GridPane();
		gp.add(new Label("NickName"), 0, 0);
		gp.add(txtNick, 0, 1);
		super.getDialogPane().setContent(gp);
		
		super.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		
		super.setResultConverter((button)->{
			if(button == loginButtonType)
				return txtNick.getText();
			return null;
		});
	}
}
