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
    private String ipAddress;
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
            proposerSocket.setSoTimeout(timeout);
            String receivedResponse = null;
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            proposerSocket.receive(receivePacket);
            receivedResponse = new String(receivePacket.getData());
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
            String request = null;
            String respond = null;
            DatagramSocket serverSocket = new DatagramSocket(port);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.setSoTimeout(timeout+10000);
            JSONObject tes = null;
            boolean getmessage = false;
            try{
                serverSocket.receive(receivePacket);
                request = new String( receivePacket.getData());
                System.out.println("RECEIVED: " + request);
                tes = new JSONObject(request);
                getmessage= true;
            }catch(SocketTimeoutException e){
                respond = "{\"status\": \"fail\",\"description\": \"rejected\"}";
                System.out.println("Timeout " + e +respond);
            }finally{
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
                        InetAddress IPAddress = receivePacket.getAddress();
                        //int port = receivePacket.getPort();
                        System.out.println("aawawa");
                        sendData = respond.getBytes();
                        System.out.println(respond);
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                        serverSocket.send(sendPacket);
                        System.out.println(IPAddress.toString());
                        System.out.println(port);
                }
            }
        }
    }
}
