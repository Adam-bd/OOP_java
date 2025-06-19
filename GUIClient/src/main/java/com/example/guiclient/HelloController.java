package com.example.guiclient;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HelloController {
    ClientReceiver receiver;

    public void setClientReceiver(ClientReceiver clientReceiver){
        this.receiver = clientReceiver;
    }

    public void broadcast(String message){
        outputTextArea.appendText(message + "\n");
    }

    @FXML
    private TextArea outputTextArea;
    @FXML
    private TextField inputTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private Button loginButton;

    @FXML
    protected void onSendButtonClick() {
        outputTextArea.setText(outputTextArea.getText() + "\n" + inputTextField.getText());
        inputTextField.clear();
    }

    @FXML
    protected void onLoginButtonClick(){
        receiver.login(loginTextField.getText());
        loginTextField.clear();
        loginButton.setDisable(true);
    }
}