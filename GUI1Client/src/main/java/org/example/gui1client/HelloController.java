package org.example.gui1client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    private ClientReceiver clientReceiver;

    public void setClientReceiver(ClientReceiver clientReceiver){
        this.clientReceiver = clientReceiver;
    }

    public void broadcast(String message){
        outputTextArea.appendText(message + "\n");
    }
}

@FXML
private TextField inputTextField;
@FXML
private TextArea outputTextArea;
@FXML
private TextField loginTextField;
@FXML
private Button loginButton;

@FXML
protected void onSendButtonClick() {
//        outputTextArea.setText(outputTextArea.getText() +"\n" +inputTextField.getText());
//        inputTextField.clear();

    clientReceiver.broadcast(inputTextField.getText());
    inputTextField.clear();
}

@FXML
protected void onLoginButtonClick(){
    clientReceiver.login(loginTextField.getText());
    loginTextField.clear();
    loginButton.setDisable(true);
}
}