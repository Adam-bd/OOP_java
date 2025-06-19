import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private Server server;
    private PrintWriter writer;
    private String clietName = null;
    public Socket getSocket(){
        return socket;
    }
    public ClientThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }

    public void run(){
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            String message;
            while((message = reader.readLine()) != null){
                String prefix = message.substring(0, 2);
                String postfix = message.substring(2);
                switch (prefix){
                    case "LO" -> login(postfix);
                    case "BR" -> server.broadcast(this,postfix);
                    case "WH" -> server.whisper(this,postfix);
                    case "ON" -> server.online(this);
                    case "FI" -> server.sendFile(this,postfix);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(String message){
        writer.println(message);
    }
    public String getClientName(){
        return clietName;
    }
    public void login(String name){
        clietName = name;
        server.online(this);
        server.broadcastLogin(this);
    }
}
