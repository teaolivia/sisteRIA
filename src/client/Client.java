/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisterclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.json.JSONObject;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
    private Random random;
    private int previouskpu;
    private Socket clientSocket;
    
    public Client(int _playerid, int _port){
        playerID = _playerid;
        port = _port;
        proposalNum = 0;
        isProposer = false;
        random = new Random();
        previouskpu = -1;
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
        System.out.println("FROM SERVER:" + response);
        receiverSocket.close(); 
    }
    void connectToServer(String _ipAddr, int _port ) throws IOException{
        clientSocket = new Socket(_ipAddr, _port);
    }
    void sendMessageToServer(Object _objIn) throws IOException{
        OutputStream out = clientSocket.getOutputStream();
        ObjectOutputStream oOut = new ObjectOutputStream(out);
        oOut.writeObject(_objIn);
    }
    void receiveMessageFromServer() throws IOException, ClassNotFoundException{
        Object oTemp = null;
        InputStream in = clientSocket.getInputStream();
        ObjectInputStream oIn = new ObjectInputStream(in);
        oTemp = (Object) oIn.readObject();
        if(oTemp instanceof JSONObject){
            System.out.println(((JSONObject)oTemp).toString());
        }else{
            System.out.println("error/fail");
        }
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
