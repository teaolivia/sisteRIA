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
            
    }
    
}
