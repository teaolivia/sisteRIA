package sisterserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Stack;

public class Server implements Runnable
{
<<<<<<< HEAD
//private Paxos paxos;
    private String host;
    private int port;
    private Socket socket;
    private final String DEFAULT_HOST = "localhost";
    private ServerSocket serverSocket;
    private int numPlayer;
    private boolean playing;
    private boolean temps2c;
//PrintStream streamToClient;
    BufferedReader streamFromClient;
    Socket fromClient;
    static int count = 0;
    Thread thread;
    
=======
>>>>>>> 50cd7dce6ca5ad2a4fe6fb38066efe574e692b88

	private int totalProcesses;
	private int numProposers;
	private int numAcceptors;
	private int numLearners;
	private int decision=-1;

<<<<<<< HEAD
		try{
			serverSocket = new ServerSocket(1001);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
        
        public Server(String _host, int _port){
            host = _host;
            port = _port;
            playing = false;
            numPlayer= 0;
            temps2c = true;
            
        }
	public boolean isWerewolf()
	{
		boolean yes = false;
		// for werewolf
		return yes;
	}

	public void connect(String host, int port) throws IOException
	{
		this.host = host;
		this.port = port;
		socket = new Socket(host, port);
		System.out.println("Game sudah dimulai... V^__^V");

	}
=======
	LinkedList<String>[] queues;
>>>>>>> 50cd7dce6ca5ad2a4fe6fb38066efe574e692b88

	@SuppressWarnings("unchecked")
	public Server(int numProposers, int numAcceptors, int numLearners){
		totalProcesses = numProposers + numAcceptors + numLearners;
		queues = new LinkedList[totalProcesses];
		for (int i=0; i<totalProcesses; i++) {
			queues[i] = new LinkedList<String>();
		}
		this.numProposers = numProposers;
		this.numAcceptors = numAcceptors;
		this.numLearners = numLearners;
	}

	public int getnumAcceptors(){
		return numAcceptors;
	}

	public int getnumProposers(){
		return numProposers;
	}

<<<<<<< HEAD
       
        
        public void server2client() throws IOException{
            String response,request,method;
            ServerSocket serverSocket = new ServerSocket(port);
            
            while(temps2c)
            {
               Socket connectionSocket = serverSocket.accept();
               BufferedReader inFromClient =new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
               DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
               request = inFromClient.readLine();
               System.out.println("Received: " + request);
               
               if (isJSON(request)){
                   JSONObject j = new JSONObject(request);
                   method = j.get("method").toString();
                    switch(method){
                        case "join" : response = "{\"status\": \"ok\",\"player_id\":"+numPlayer+"}";numPlayer++;break;
                        case "leave"  : response = "{\"status\": \"ok\"}";break;
                        case "ready" :  response = "{\"status\": \"ok\",\"description\":\"Waiting other player to start\"}";break;
                        case "client_address": response ="not";break;
                        case "vote_result_civilian": response = "{\"status\": \"ok\",\"description\":\"civ Killed\"}";break;
                        default: response = ""; break;
                    }
                    response = response + '\n';
                    outToClient.writeBytes(response); 
               }else{
                   temps2c=false;
               }
               

            }
        }
        private boolean isJSON(String test){
            try{
                new JSONObject(test);
            }catch(JSONException ex){
                try{
                    new  JSONArray(test);
                }catch(JSONException ex2){
                    return false;
                }
            }
            return true;
        }
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
=======
	public int getnumLearners(){
		return numLearners
	}
>>>>>>> 50cd7dce6ca5ad2a4fe6fb38066efe574e692b88
       	
    // untuk channel communication dari proses processID
    public Channel getChannel(int processID){
    	if (processID <0 || processID >= totalProcesses) {
    		throw new Error ("Invalid process ID");
    	}
    	Channel c = new Channel();
    	c.index = processID;
    	c.network = this;
    	return c;
    }


}
