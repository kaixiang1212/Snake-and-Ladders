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
        while ( (line = reader.readLine()) != null ) {
            String[] token = line.split(" ");
            String cmd = token[0];
            if ("quit".equalsIgnoreCase(cmd) || "exit".equals(cmd)) break;
            else if ("roll".equalsIgnoreCase(cmd)) {
                handleRoll();
            } else if ("stop".equalsIgnoreCase(cmd)) {
                handleStop();
            } else if ("next".equalsIgnoreCase(cmd)) {
                handleNextToken();
            } else if ("change".equalsIgnoreCase(cmd)) {
                handleNameChange(token);
            }else if ("".equals(cmd)) {
            } else {
                msg = "Unknown command: " + cmd + "\n";
                send(msg);
            }
        }
        closeSocket();
    }

    private void handleNextToken() {
        if (!server.playerCustomiseScreen) return;
        server.nextToken(player);
    }

    private void handleRoll() {
        if (!server.diceScreen) return;
        if (server.getCurrentPlayer() != player - 1) return;
        server.diceRoll();
    }

    private void handleStop() {
        if (!server.diceScreen) return;
        if (server.getCurrentPlayer() != player - 1) return;
        server.diceStop();
    }

    private void handleNameChange(String[] token){
        if (!server.playerCustomiseScreen) return;
        StringBuilder sb = new StringBuilder();
        for (int i=1;i < token.length;i++){
            sb.append(token[i]);
        }
        server.setPlayerName(player, sb.toString());
    }

    void send(String msg) throws IOException {
        outputStream.write(msg.getBytes());
    }

    private void closeSocket() throws IOException {
        server.removePlayer(this);
        clientSocket.close();
    }

    int getPlayerNo(){ return player; }

}
