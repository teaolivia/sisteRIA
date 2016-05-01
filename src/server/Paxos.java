import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Paxos 
{
	DatagramSocket clientSocket;

	public void sendRequest(String sentence, String host, int port)
	{
		clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress();
		byte[] sendData = new byte[1024];
		System.out.println(sentence);
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket();
		clientSocket.send(sendPacket);
	}

}