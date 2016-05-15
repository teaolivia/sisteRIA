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

import java.util.LinkedList;
import java.util.Stack;

public class Server implements Runnable
{

	private int totalProcesses;
	private int numProposers;
	private int numAcceptors;
	private int numLearners;
	private int decision=-1;

	LinkedList<String>[] queues;

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

	public int getnumLearners(){
		return numLearners
	}
       	
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
