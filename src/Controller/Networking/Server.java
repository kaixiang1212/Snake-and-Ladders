package Controller.Networking;

import Controller.DiceController;
import Controller.PlayerCustomizationController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private ArrayList<ClientHandler> clientList;
    private int numPlayer;
    private int[] player;
    private PlayerCustomizationController playerCustomizationController;
    boolean playerCustomiseScreen;
    private DiceController diceController;
    boolean diceScreen;
    private ServerSocket serverSocket;
    private int port = 8888;

    public Server() throws IOException {
        diceScreen = false;
        playerCustomiseScreen = false;
        clientList = new ArrayList<>();
        try  {
            serverSocket = new ServerSocket(port);
        } catch (Exception e){
            System.exit(-1);
        }
    }

    List<ClientHandler> getClientList(){ return clientList; }

    int getNumClient(){ return clientList.size(); }

    int getNumPlayer() { return numPlayer; }

    private int getNextSlot(){
        for (int i=0;i < numPlayer;i++){
            if (player[i] != 1) return i + 1;
        }
        return -1;
    }

    private void addPlayer(int slot, Socket clientSocket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(this, slot, clientSocket);
        player[slot-1] = 1;
        clientList.add(clientHandler);
        clientHandler.start();
        onPlayerConnected(slot);
    }

    void removePlayer(ClientHandler clientHandler) throws IOException {
        int i = clientHandler.getPlayerNo();
        player[i-1] = 0;
        clientList.remove(clientHandler);

        onPlayerDisconnect(i);
    }

    @Override
    public void run() {
        int port = 8888;
        Socket clientSocket;
        try {
            while ((clientSocket = serverSocket.accept() )!= null) {
                int slot = getNextSlot();
                if (slot != -1) {
                    addPlayer(slot, clientSocket);
                } else {
                    clientSocket.getOutputStream().write("Maximum player exceeded\n".getBytes());
                    clientSocket.close();
                }
            }
        } catch (SocketException e){
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

    void nextToken(int player){ playerCustomizationController.playerChangeToken(player); }

    public void setDiceController(DiceController diceController){
        this.diceScreen = true;
        this.playerCustomiseScreen = false;
        this.diceController = diceController;
    }

    void diceRoll(){ diceController.rollButtonClicked(); }

    void diceStop(){ diceController.stopButtonClicked(); }

    private void onPlayerDisconnect(int player) throws IOException {
        String msg = "Player " + player + " left the game\n";
        for (ClientHandler client : clientList){
            client.send(msg);
        }
        System.out.println(msg);
    }

    private void onPlayerConnected(int player) throws IOException {
        String msg = "Player " + player + " joined the game\n";
        for (ClientHandler client : clientList){
            client.send(msg);
        }
        System.out.print(msg);
    }

    int getCurrentPlayer(){ return diceController.getCurrentPlayerNum(); }

    public void onPlayerSelection(){ return; }

    public void onGameStart(){ return; }

    public void onPlayerChange(int player){ return; }

    void setPlayerName(int player, String name) {
        playerCustomizationController.playerChangeName(player, name);
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
