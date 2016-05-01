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
import org.json.JSONObject;

public class Server implements Runnable
{
<<<<<<< HEAD
	private Paxos paxos;
	private String host;
=======
    private String host;
>>>>>>> ea38ce9783fbf5ff22ca116817d787e2b4816894
    private int port;
    private Socket socket;
    private final String DEFAULT_HOST = "localhost";
    private ServerSocket serverSocket;
    private int numPlayer;
    private boolean playing;
    //PrintStream streamToClient;
    BufferedReader streamFromClient;
    Socket fromClient;
    static int count = 0;
    Thread thread;

	public Server() 
	{
		this.host = "localhost";
		this.port = 9999;
		this.socket = null;

		try{
			serverSocket = new ServerSocket(1001);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
        
        public Server(String _host, int _port){
            try{
                host = _host;
                port = _port;
                serverSocket = new ServerSocket(_port);
                socket = serverSocket.accept();
                playing = false;
            }catch(IOException e){
                
            }
            
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

	public JSONObject receiveJSON() throws IOException
	{
		InputStream in = socket.getInputStream();
		ObjectInputStream i = new ObjectInputStream(in);
		JSONObject line = null;
		try {
			line = (JSONObject) i.readObject();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}

		return line;
            
	}

	public void sendJSON(JSONObject jsonObj) throws IOException
	{
		JSONObject jsonObject2 = new JSONObject();
		
		OutputStream out = socket.getOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(out);
		o.writeObject(jsonObject2);
		out.flush();
	}

	public void runServer()
	{
		try{
			while(true){
				fromClient = serverSocket.accept();
				count++;
				//streamFromClient = new BufferedReader(fromClient.getInputStream());

				InputStreamReader in = new InputStreamReader((fromClient.getInputStream()));
				//PrintStream streamToClient = new PrintStream(fromClient.getInputStream());
				String str = streamFromClient.readLine();
				System.out.println(str);
				//streamToClient.println("Halo, "+str);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			try {
				fromClient.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
<<<<<<< HEAD
=======
        
        public String receiveMessage() throws IOException{
            String temp = null;
            BufferedReader inFromClient =new BufferedReader(new InputStreamReader(socket.getInputStream()));
            temp = inFromClient.readLine();
            return temp;
        }
        public void sendMessage(String out) throws IOException{
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
            String response = out + '\n';
            outToClient.writeBytes(response); 
        }
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       	
>>>>>>> ea38ce9783fbf5ff22ca116817d787e2b4816894

}