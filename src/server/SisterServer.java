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
import org.json.JSONException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author LUCKY
 */
public class SisterServer {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) throws Exception {
            System.out.println("Masukan address");
            BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
            String address = inFromUser.readLine().toString();
            
            System.out.println("Masukan Port");
            inFromUser = new BufferedReader( new InputStreamReader(System.in));
            int port = Integer.parseInt(inFromUser.readLine().toString());
            Server s = new Server(address,port);
            s.server2client();
            //Client c = new Client(0,9999);
            //c.peerAsServer();
            
<<<<<<< HEAD
=======
            while(true)
            {
               Socket connectionSocket = welcomeSocket.accept();
               BufferedReasder inFromClient =new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
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
            private int sequenceID;
            private int processID;

            Client c = new Client(1,9999);
            c.receiveMessageFromClient("localhost", 8888);

            // creating consensus
            public void consensus(){
              
            }

>>>>>>> 50cd7dce6ca5ad2a4fe6fb38066efe574e692b88
    }
    
}
