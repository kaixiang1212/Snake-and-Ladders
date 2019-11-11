package Controller.Networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private ArrayList<ClientHandler> clientList;
    private int numPlayer;
    private int[] player;

    private Server(int numPlayer){
        clientList = new ArrayList<>();
        this.numPlayer = numPlayer;
        this.player = new int[numPlayer];
    }

    List<ClientHandler> getClientList(){
        return clientList;
    }

    int getNumClient(){
        return clientList.size();
    }

    int getNumPlayer() {
        return numPlayer;
    }

    private int getNextSlot(){
        for (int i=0;i < numPlayer;i++){
            if (player[i] != 1) return i + 1;
        }
        return -1;
    }

    private void addPlayer(int slot, Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, slot, clientSocket);
        player[slot-1] = 1;

        String msg = "Player " + slot + " joined the game\n";
        for (ClientHandler client : clientList){
            client.send(msg);
        }

        clientList.add(clientHandler);
        clientHandler.start();
        System.out.print(msg);
    }

    void removePlayer(ClientHandler clientHandler) throws IOException {
        int i = clientHandler.getPlayerNo();
        player[i-1] = 0;
        clientList.remove(clientHandler);

        String msg = "Player " + i + " left the game\n";
        for (ClientHandler client : clientList){
            client.send(msg);
        }

        System.out.print(msg);
    }

    public void start() {
        int port = 8888;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                int slot = getNextSlot();
                if (slot != -1) {
                    addPlayer(slot, clientSocket);
                } else {
                    clientSocket.getOutputStream().write("Maximum player exceeded\n".getBytes());
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(3);
        server.start();
    }

}
