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
        System.out.println("Masukan Port");
        BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
        int port = Integer.parseInt(inFromUser.readLine().toString());
        Client c = new Client(0,port);
        c.client2server();
        //c.peerAsClient();
        
    }
    
}
