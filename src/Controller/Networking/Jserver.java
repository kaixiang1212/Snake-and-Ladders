package Controller.Networking;

import java.io.*;
import java.net.*;
import java.util.*;
public class Jserver{
public static void main(String args[]) throws IOException{
    ServerSocket s=new ServerSocket(9002);

    try{
    	while (true) {
        Socket ss=s.accept();
        PrintWriter pw = new PrintWriter(ss.getOutputStream(),true);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader br1 = new BufferedReader(new InputStreamReader(ss.getInputStream()));
        InputStream input = ss.getInputStream();
        //String str[20];
        //String msg[20];
        System.out.println("Client connected..");
        
        	String line;
        	while ((line = br1.readLine()) != null) {
        		System.out.println(line);
        	}
            //System.out.println("Enter command:");
//            System.out.println(in);
           // System.out.println(input.read());
    	}
    }
    finally{s.close();}
    }
}