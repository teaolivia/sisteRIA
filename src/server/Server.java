import java.net.URL;
import java.net.URLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Server 
{
	public Server() 
	{

	}

	public boolean isWerewolf()
	{
		boolean yes = false;
		return yes;
	}



	public void connect(String host, int port) throws IOException
	{
		this.host = hostl
		this.port = port;
		socket = new Socket(host, port);
		System.out.println("Game sudah dimulai... V^__^V");

	}

	public JSONObject receiveJSON() throws IOException
	{

	}

	public static void main(String[] args) throws Exception 
	{

	}
}