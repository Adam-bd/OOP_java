import java.io.*;
import java.net.Socket;
import java.nio.file.Path;

public class ClientThread extends Thread {
    private Socket socket;
    private PrintWriter writer;
    private boolean running;

    public ClientThread(String address, int port) {
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
            String message;
            while ((message = reader.readLine()) != null) {
//                if(message.startsWith("FI"))
//                    receiveFile(message.substring(2));
//                else
                System.out.println(message);
                //runCommand(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String message) {
        writer.println(message);
    }

    public void login(String name) {
        writer.println("LO " + name);
    }

    public void broadcast(String message) {
        writer.println("BR " + message);
    }

    public void whisper(String message) {
        writer.println("WH " + message);
    }

    public void online() {
        writer.println("ON ");
    }
}