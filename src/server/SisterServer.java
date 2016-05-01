/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sisterserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import org.json.JSONObject;

/**
 *
 * @author LUCKY
 */
public class SisterServer {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) throws Exception {
           /* String clientSentence;
            String capitalizedSentence;
            ServerSocket welcomeSocket = new ServerSocket(6789);
            
            while(true)
            {
               Socket connectionSocket = welcomeSocket.accept();
               BufferedReader inFromClient =new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
               DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
               clientSentence = inFromClient.readLine();
               System.out.println("Received: " + clientSentence);
               capitalizedSentence = clientSentence.toUpperCase() + '\n';
               outToClient.writeBytes(capitalizedSentence); 

            }*/
            /*Server s = new Server("localhost",9999);
            String request = s.receiveMessage();
            System.out.println(request);
            JSONObject j = new JSONObject(request);
            System.out.println(j.get("method"));*/
            Client c = new Client(1,9999);
            c.receiveMessageFromClient("localhost", 8888);
    }
    
}
