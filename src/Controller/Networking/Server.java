package Controller.Networking;

import Controller.DiceController;
import Controller.PlayerCustomizationController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
		Client newClient = new Client(clientSocket , this, slot - 1);
		newClient.start();
		onPlayerConnected(slot);
	}
/*
	void removePlayer(Client clientHandler) throws IOException {
		int i = clientHandler.getPlayerNo();
		player[i - 1] = 0;
		clientList.remove(clientHandler);

		onPlayerDisconnect(i);
	}
*/
	@Override
	public void run() {
		int port = 5001;
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				
				PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				BufferedReader br1 = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				InputStream input = clientSocket.getInputStream();
				// String str[20];
				// String msg[20];
				System.out.println("Client connected..");
				

				String line = br1.readLine();
				//System.out.println(line + "line is");
				/*
				 * while ((line = br1.readLine()) != null) { System.out.println(line); }
				 */
				/*
				 * System.out.println(line); if ("roll".equalsIgnoreCase(line)) {
				 * System.out.println("entered"); if (!this.diceScreen) return; this.diceRoll();
				 * }
				 */
				// System.out.println("Enter command:");
				// System.out.println(in);
				// System.out.println(input.read());

				// OutputStream outputstream = clientSocket.getOutputStream();
				// outputstream.write("helo world\n".getBytes());
				
				 int slot = getNextSlot(); 
				 if (slot != -1) { 
					 addPlayer(slot, clientSocket);
			     }
				 else {
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
		System.out.println(msg);
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

	public void onGameStart() {
		return;
	}

	public void onPlayerChange(int player) {
		return;
	}

	void setPlayerName(int player, String name) {
		playerCustomizationController.playerChangeName(player, name);
	}
}
