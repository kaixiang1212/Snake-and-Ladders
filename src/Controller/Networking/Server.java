package Controller.Networking;

import Controller.DiceController;
import Controller.PlayerCustomizationController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

	private ArrayList<Client> clientList;
	private int numPlayer;
	private static int playersCount  = 0;
	private int[] player;
	private PlayerCustomizationController playerCustomizationController;
	boolean playerCustomiseScreen;
	private DiceController diceController;
	boolean diceScreen;
	private ServerSocket serverSocket;
	private volatile boolean running;
	
	public Server() {
		diceScreen = false;
		playerCustomiseScreen = false;
		clientList = new ArrayList<>();
	}

	List<Client> getClientList() {
		return clientList;
	}

	int getNumClient() {
		return clientList.size();
	}

	int getNumPlayer() {
		return numPlayer;
	}

	private int getNextSlot() {
		for (int i = 0; i < numPlayer; i++) {
			if (player[i] != 1)
				return i + 1;
		}
		return -1;
	}

	private void addPlayer(int slot, Socket clientSocket) throws IOException {
		player[slot - 1] = 1;
		playersCount++;
        Client newClient = new Client(this, slot - 1, clientSocket);
		newClient.start();
        clientList.add(newClient);
		onPlayerConnected(slot);
	}

	void removePlayer(Client client) throws IOException {
		int i = client.getPlayerNo();
		player[i - 1] = 0;
		clientList.remove(client);

		onPlayerDisconnect(i);
	}


	@Override
	public void run() {
		running = true;
		int port = 8000;
		try {
			serverSocket = new ServerSocket(port);
			while (running) {
                Socket clientSocket = serverSocket.accept();

			    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
			    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			    BufferedReader br1 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			    InputStream input = clientSocket.getInputStream();
                System.out.println("Client connected..");

				int slot = getNextSlot();
				if (slot != -1) {
				    addPlayer(slot, clientSocket);
			    } else {
				    clientSocket.getOutputStream().write("Maximum player exceeded\n".getBytes());
				    clientSocket.close();
				}
			}

		} catch (SocketException se){

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void setPlayers(int numPlayer) {
		this.numPlayer = numPlayer;
		this.player = new int[numPlayer];
	}

	public void setCustomisableController(PlayerCustomizationController playerCustomizationController) {
		this.playerCustomiseScreen = true;
		this.diceScreen = false;
		this.playerCustomizationController = playerCustomizationController;
	}

	void nextToken(int player) {
		playerCustomizationController.playerChangeToken(player);
	}

	public void setDiceController(DiceController diceController) {
		this.diceScreen = true;
		this.playerCustomiseScreen = false;
		this.diceController = diceController;
	}

	void diceRoll() {
		diceController.rollButtonClicked();
	}

	void diceStop() {
		diceController.stopButtonClicked();
	}

	private void onPlayerDisconnect(int player) throws IOException {
		String msg = "Player " + player + " left the game\n";
		for (Client client : clientList) {
			client.send(msg);
		}
		System.out.print(msg);
	}

	private void onPlayerConnected(int player) throws IOException {
		String msg = "Player " + player + " joined the game\n";
		
		for (Client client : clientList) {
			client.send(msg);
		}
		System.out.print(msg);
	}

	int getCurrentPlayer() {
		return diceController.getCurrentPlayerNum();
	}

	public void onPlayerSelection() {
		return;
	}

	void setPlayerName(int player, String name) {
		playerCustomizationController.playerChangeName(player, name);
	}

	public void kill() throws IOException {
		ArrayList<Client> toRemove = new ArrayList<Client>();
		toRemove.addAll(clientList);
		for(Client client : toRemove) {
			client.closeSocket();
			client.end();
		}
		if (serverSocket != null) serverSocket.close();
		running = false;
	}

    void updateState() {
		if (clientList.size() != numPlayer) return;
        for (Client client : clientList){
            if (!client.ready()) return;
        }
        try {
            playerCustomizationController.createGameButtonClicked();
        } catch (Exception ignored){
        }
    }
}
