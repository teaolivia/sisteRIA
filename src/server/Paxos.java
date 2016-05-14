import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.HashMap;

import java.lang.Thread;

public class Paxos 
{
	Server server;
	DatagramSocket clientSocket;
	private int n; // number of request

	public Paxos(){
		this.
	}
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

	public void runPaxos() {
		int n_proses = server.getnumProposers() + server.getnumAcceptors() + server.getnumLearners();
		for (int i=0; i<n_proses; i++){
			Thread t = new Thread(this, ""+i);
			t.start();
		}
	}	

	public int nextProposalNumber(int PID, int cur_Pnum){
		int i = 0;
		while (true) {
			if (i<=cur_Pnum){
				i++;
			} else {
				if (i%server.getnumProposers()==PID)
					return i;
				else
					i++;
			}
			if (i==MAX_PROPNUM)
				return -1;
		}
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