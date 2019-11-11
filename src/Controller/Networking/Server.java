package Controller.Networking;

import Controller.DiceController;
import Controller.PlayerCustomizationController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private ArrayList<ClientHandler> clientList;
    private int numPlayer;
    private int[] player;
    private PlayerCustomizationController playerCustomizationController;
    private boolean playerCustomiseScreen = false;
    private DiceController diceController;
    private boolean diceScreen = false;

    public Server(){
        clientList = new ArrayList<>();
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

    @Override
    public void run() {
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

    public void setPlayers(int numPlayer) {
        this.numPlayer = numPlayer;
        this.player = new int[numPlayer];
    }

    public void setCustomisableController(PlayerCustomizationController playerCustomizationController){
        this.playerCustomiseScreen = true;
        this.diceScreen = false;
        this.playerCustomizationController = playerCustomizationController;
    }

    public void nextToken(int player){
        if (!playerCustomiseScreen) return;
        playerCustomizationController.playerChangeToken(player);
    }

    public void setDiceController(DiceController diceController){
        this.diceScreen = true;
        this.playerCustomiseScreen = false;
        this.diceController = diceController;
    }

    void playerRoll(int player){
        if (!diceScreen) return;
        if (diceController.getCurrentPlayerNum() != player - 1) return;
        diceController.rollButtonClicked();
    }

    void playerStop(int player){
        if (!diceScreen) return;
        if (diceController.getCurrentPlayerNum() != player - 1) return;
        diceController.stopButtonClicked();
    }
}
