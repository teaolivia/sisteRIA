/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisterclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
    public void prepareProposal() throws SocketException, UnknownHostException, IOException{
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];  
        if(isProposer){
            DatagramSocket proposerSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            String message  = "{\"method\":\"prepare_proposal\",\"proposal_id\": ["+proposalNum+","+playerID+"]}";
            sendData = message.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            double rand = random.nextDouble();
            if (rand < 0.85) {
                proposerSocket.send(sendPacket);
            }
            //proposerSocket.setSoTimeout(timeout);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            proposerSocket.receive(receivePacket);
            String receivedResponse = new String(receivePacket.getData());
            System.out.println("FROM SERVER:" + receivedResponse);
            proposerSocket.close();
            boolean waiting = true;
            /*while(waiting){
                try{
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    proposerSocket.receive(receivePacket);
                    receivedResponse = new String(receivePacket.getData());
                    System.out.println("FROM SERVER:" + receivedResponse);
                    waiting = false;
                    proposerSocket.close()
                }catch(SocketTimeoutException e){
                    receivedResponse = "{\"status\": \"error\",\"description\": \"No Response\"}";
                    System.out.println("Timeout " + e +receivedResponse);
                    proposerSocket.close();
                }
            }*/
        }else{
            String message = null;
            String respond = null;
            DatagramSocket serverSocket = new DatagramSocket(port);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.setSoTimeout(timeout+10000);
            JSONObject tes = null;
            InetAddress IPAddress=null;
            boolean getmessage = false;
            try{
                serverSocket.receive(receivePacket);
                message = new String( receivePacket.getData());
                IPAddress = receivePacket.getAddress();
                System.out.println("RECEIVED: " + message);
                tes = new JSONObject(message);
                getmessage= true;
                if (getmessage){
                    if(tes.get("method").toString().compareTo("prepare_proposal")==0){
                        System.out.println("aawawa");
                        Scanner input = new Scanner(System.in);
                        int in = Integer.parseInt(input.next()) ;
                            switch(in){
                                case 1 : respond = "{\"status\": \"ok\",\"description\": \"accepted\",\"previous_accepted\":"+previouskpu+"}"; break;
                                case 2 : respond = "{\"status\": \"fail\",\"description\": \"rejected\"}"; break;
                            }
                        }
                        //int port = receivePacket.getPort();
                        System.out.println("aawawa");
                        sendData = respond.getBytes();
                        System.out.println(respond);
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendPacket);
                        System.out.println(IPAddress.toString());
                        System.out.println(port);
                }
            }catch(SocketTimeoutException e){
                respond = "{\"status\": \"fail\",\"description\": \"rejected\"}";
                System.out.println("Timeout " + e +respond);
            }
        }
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
        InetAddress IPAddress = InetAddress.getByName(_addr);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        receiverSocket.receive(receivePacket);
        String response = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + response);
        receiverSocket.close(); 
    }
    
}
