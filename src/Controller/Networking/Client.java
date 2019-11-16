package Controller.Networking;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import Controller.DiceController;
import Model.Player;

public class Client extends Thread 
{
	   protected Socket socket;
	   private final Server server;
	   private int player;
	   private OutputStream outputStream;

	   public Client(Socket clientSocket, Server server, int player) {
	        this.socket = clientSocket;
	        System.out.println("creating socket");
	        this.server = server;
	        this.player = player;
	   }
	   public void run() {
		   System.out.println("running");
	        InputStream inp = null;
	        BufferedReader brinp = null;
	        DataOutputStream out = null;
	        try {
	            inp = socket.getInputStream();
	            brinp = new BufferedReader(new InputStreamReader(inp));
	            out = new DataOutputStream(socket.getOutputStream());
	        } catch (IOException e) {
	            return;
	        }
	        String line;
	        while (true) {
	            try {
	            	System.out.println("client is runnign");
	                line = brinp.readLine();
 	                if (line.equals("roll")) {
		            	System.out.println("number of player is " + server.getCurrentPlayer());
		            	System.out.println("this player num is actually" + player);
		            	// make sure other players can't roll when it is player's turn
		            	if (server.getCurrentPlayer() == player) {
		                	handleRoll();
		            	}
 	                } else if (line.equals("stop")) {
		            	if (server.getCurrentPlayer() == player) {
	                	handleStop();
		            	}
	                }
	                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
	                    socket.close();
	                    System.out.println("closing");
	                    return;
	                } else {
	                    System.out.println("enttered here");
	                    out.writeBytes(line + "\n\r");
	                    out.flush();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	                return;
	            }
	        }

	   }

    private void handleRoll() {
        if (!server.diceScreen) return;
        //if (server.getCurrentPlayer() != player - 1) return;
        server.diceRoll();
    }
    private void handleStop() {
        if (!server.diceScreen) return;
       // if (server.getCurrentPlayer() != player - 1) return;
        server.diceStop();
    }
    void send(String msg) throws IOException {
        outputStream.write(msg.getBytes());
    }

}
