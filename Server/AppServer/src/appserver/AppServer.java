/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.net.*;
import java.util.ArrayList;
import java.util.TreeSet;


public class AppServer {

    static TreeSet<String> urls;
    static ArrayList<String> adsDB;
    static ServerSocket ss;
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        ss = new ServerSocket(9999);
        urls = new TreeSet<>();
        adsDB = new ArrayList<>();

        while(true){
            Socket cs = ss.accept();
            System.out.println("Nova conexão");
            ServerWorker thread = new ServerWorker(cs,urls,adsDB);
            thread.start();
        }
    }
}
