package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

	private static Socket socket;
	private static PrintWriter out;
	private static BufferedReader in;
	private static boolean isConnected = false;
	private static int port = 4321;
	
	private static InetSocketAddress[] addresses = new InetSocketAddress[252];
	
	public static boolean createConnection(){
		//set all possible ips
		try {
			String ip[] = InetAddress.getLocalHost().getHostAddress().split("\\.");
			String shortenIP = "";
			for(int i = 0; i < ip.length - 1; i ++) shortenIP += ip[i] + ".";
			for(int i = 2; i < 254; i++){
				addresses[i - 2] = 
						new InetSocketAddress(InetAddress.getByName(shortenIP + String.valueOf(i)), port);
			}
		} catch (UnknownHostException e1) {
			System.out.println("Couldn't get address!");
		}
		
		System.out.println("Looking for connection!");
		
		for(int i = 0; i < addresses.length; i++){//2 - 253
			if(isConnected) break;
			isConnected = testConnection(i);
		}


		return isConnected;
	}
	
	private static boolean testConnection(int index){
		socket = new Socket();
		try{
			if(isConnected) return false;
			System.out.println(addresses[index].getAddress());
			socket.connect(addresses[index], 50);
			out = new PrintWriter(socket.getOutputStream(), true);
			out.flush();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			return true;
		} catch (UnknownHostException e) {
			System.out.println("Unknow host");
			return false;
		} catch  (IOException e) {
			System.out.println("Could not connect to socket address: " + 
					addresses[index].getAddress().getHostAddress());
			return false;
		}
	}
	
	public static void sendMessage(String text){
		out.println(text);
		out.flush();
	}
	
	public static String readMessages(){
		String lines = "";
		try {
			String line;
			while(socket.isConnected() && in.ready() && (line = in.readLine()) != null)
				lines += line + "\n";
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!socket.isConnected() || socket.isClosed() || !socket.isOutputShutdown()) System.exit(0);
		return lines;
		
	}
	
	public static boolean isConnected(){
		return isConnected;
	}
	
	public static void closeConnection(){
		try {
			if(in != null)
				in.close();
			if(out != null)
				out.close();
			if(socket != null)
				socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
