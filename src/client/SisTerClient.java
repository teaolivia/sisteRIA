/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisterclient;

/**
 *
 * @author LUCKY
 */

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Scanner;
import org.json.JSONObject;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SisTerClient {
    //private static Random random;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        /*String sentence;
        String modifiedSentence;
        BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
        Socket clientSocket = new Socket("localhost", 6789);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);
        clientSocket.close(); */
        Client c = new Client(0,9999);
        c.connectToServer("localhost", 9999);
        c.sendMessageToServer("{\"method\": \"join\",\"username\": \"sister\",\"udp_address\": \"192.168.1.3\",\"udp_port\": 9999}");
        
        c.sendMessageToClient("localhost", 8888,"{\"method\":\"prepare_proposal\",\"proposal_id\": ["+c.getProposalNum()+","+c.getPlayerID()+"]}");
        
        public void vote(){
            
        }

        public void send_request(R){

        }
    
}
