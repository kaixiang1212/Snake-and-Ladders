package Controller.Networking;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {

    private final Socket clientSocket;
    private final Server server;
    private OutputStream outputStream;
    private int player;

    ClientHandler(Server server, int player, Socket clientSocket) throws IOException {
        this.player = player;
        this.server = server;
        this.clientSocket = clientSocket;
        outputStream = clientSocket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            try {
                closeSocket();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void handleClientSocket() throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String msg = "You are player " + player + "\n";
        msg = msg + "Current players " + server.getNumClient() + "/" + server.getNumPlayer() + "\n";
        send(msg);

        String line;
        while ( (line = reader.readLine()) != null ){
            String[] token = line.split(" ");
            String cmd = token[0];
            if ("quit".equalsIgnoreCase(cmd)|| "exit".equals(cmd)) break;
            else if ("roll".equalsIgnoreCase(cmd)){
                handleRoll();
            } else if ("next".equalsIgnoreCase(cmd)){
                handleNextToken();
            } else if ("".equals(cmd)){

            } else {
                msg = "Unknown command: " + cmd + "\n";
                send(msg);
            }
        }
        closeSocket();
    }

    private void handleNextToken() { }

    private void handleRoll() { }

    void send(String msg) throws IOException {
        outputStream.write(msg.getBytes());
    }

    private void closeSocket() throws IOException {
        server.removePlayer(this);
        clientSocket.close();
    }

    int getPlayerNo(){
        return player;
    }

}
