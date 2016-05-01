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
	private int n; // number of request

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

	public boolean IsUnique(){
		// check the uniqueness of ID
	}

	public void SortRole(){
		// assign and sort role
	}

	public void Propose(){
		// prepare
	}

	public void Agree(){

	}

	public void Commit(){

	}

	public void Accept(){

	}

	public void Reject(){

	}

	public boolean Fail(){

	}

}