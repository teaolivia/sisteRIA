/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisterclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import java.net.UnknownHostException;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author LUCKY
 */
public class Client {
    private int playerID;
    private int port;
    private int proposalNum;
    private final int timeout=15000; //in milliseconds 
    private boolean isProposer;
    private String ipAddress; //ipsendiri
    private String serverAddress;
    private Random random;
    private int previouskpu;
    private Socket clientSocket;
    private boolean tempc2s;
    private boolean tempc2c;
 
    
    public Client(int _playerid, int _port){
        playerID = _playerid;
        port = _port;
        proposalNum = 0;
        isProposer = false;
        random = new Random();
        previouskpu = -1;
        tempc2s = true;
        tempc2c = true;
        serverAddress = "localhost";
    }
    public void setIsProposer(boolean _bool){
        isProposer = _bool;
    }
    public void setPort(int _port){
        port = _port;
    }
    void sendMessageToClient(String _addr, int port, String message) throws SocketException, UnknownHostException, IOException{
        DatagramSocket senderSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(_addr);
        byte[] sendData = new byte[1024];
        sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        double rand = random.nextDouble();
        if (rand < 0.85) {
            senderSocket.send(sendPacket);
        }
        senderSocket.close();
    }
    void receiveMessageFromClient(String _addr, int port) throws SocketException, IOException{
        byte[] receiveData = new byte[1024];  
        DatagramSocket receiverSocket = new DatagramSocket(port);
        receiverSocket.setReuseAddress(true);
        InetAddress IPAddress = InetAddress.getByName(_addr);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        receiverSocket.receive(receivePacket);
        String response = new String(receivePacket.getData());
        System.out.println("RESPONSE:" + response);
        //receiverSocket.close(); 
    }
    
    public void peerAsClient() throws UnknownHostException, SocketException, IOException{
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        String input,sentence="";
        while(true){
            BufferedReader inFromUser =new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            peermenu();
            input = inFromUser.readLine();
            switch(input){
                case "1" : sentence = "{\"method\": \"prepare_proposal\",\"proposal_id\": ["+proposalNum+","+playerID+"] }"; break;
                case "2" : sentence = "{\"method\": \"accept_proposal\",\"proposal_id\": ["+proposalNum+","+playerID+"], \"kpu_id\" : 0 }";break;
                case "3" : sentence = "{\"method\": \"vote_werewolf\",\"player_id\": 2 }";break;
                case "4" : sentence = "{\"method\": \"vote_civilian\",\"player_id\": 2 }";break;
                default : sentence ="";break;
            }
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            if (sentence != ""){
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);                
                String modifiedSentence = new String(receivePacket.getData());
                System.out.println("FROM SERVER:" + modifiedSentence);
            }
            
            clientSocket.close(); 
        }
    }
    void peermenu(){
        System.out.println("1.prepare proposal");
        System.out.println("2.accept proposal");
        System.out.println("3.vote werewolf");
        System.out.println("4.vote_civillian");

        
    }
    public void peerAsServer() throws SocketException, IOException{
        DatagramSocket serverSocket = new DatagramSocket(port);
            String response,method;
            while(tempc2c)
               {
                    byte[] receiveData = new byte[1024];
                    byte[] sendData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(receivePacket);
                    String request = new String( receivePacket.getData());
                    System.out.println("RECEIVED: " + request);
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();
                    if (isJSON(request)){
                        JSONObject j = new JSONObject(request);
                        method = j.get("method").toString();
                        switch(method){
                            case "prepare_proposal" : response = "{\"status\": \"ok\",\"description\":\"accepted\", \"previous_accepted\" : "+previouskpu+"}";break;
                            case "accept_proposal"  : response = "{\"status\": \"ok\",\"description\":\"accepted\"}";break;
                            case "vote_werewolf" :  response = "{\"status\": \"ok\",\"description\":\"Vote Accepted\"}";break;
                            case "vote_civilian": response ="{\"status\": \"ok\",\"description\":\"Vote Accepted\"}";break;
                            default: response = ""; break;
                        }
                        sendData = response.getBytes();
                        DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        
                    }else{
                        tempc2c=false;
                    }
                    
               } 
    }
    public void client2server() throws IOException{
        String message="",response="",input;
        while(tempc2s){
            client2servermenu();
            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
            clientSocket = new Socket(serverAddress, port);
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            input = inFromUser.readLine();
            switch(input){
                case "1" : message = "{\"method\": \"join\",\"username\": \"sister\",\"udp_address\": \"192.168.1.3\",\"udp_port\": 9999}";break;
                case "2" : message = "{\"method\": \"leave\"}";break;
                case "3" : message = "{\"method\": \"ready\"}";break;
                case "4" : message = "{\"method\": \"client_address\"}";break;
                case "5" : message = "{\"method\": \"vote_result_civilian\",\"vote_status\": 1,\"player_killed\": 4,\"vote_result\": [[0, 1], [1, 2]]}";break;
                default : message = "hua";tempc2s = false;break;
            }
            outToServer.writeBytes(message + '\n');
            if (message == "hua"){}
            else{
                response = inFromServer.readLine();
                System.out.println("response: " + response);
            }
            clientSocket.close();
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
    public void client2servermenu(){
        System.out.println("1.join");
        System.out.println("2.leave");
        System.out.println("3.ready");
        System.out.println("4.list client");
        System.out.println("5.civ killed");
    }
    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getProposalNum() {
        return proposalNum;
    }

    public void setProposalNum(int proposalNum) {
        this.proposalNum = proposalNum;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public int getPreviouskpu() {
        return previouskpu;
    }

    public void setPreviouskpu(int previouskpu) {
        this.previouskpu = previouskpu;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
