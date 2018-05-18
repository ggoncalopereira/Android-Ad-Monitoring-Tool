/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.util.ArrayList;
import java.util.TreeSet;


public class AppServer {

    static TreeSet<String> urls;
    static ArrayList<String> adsDB;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
         
        urls = new TreeSet<>();
        adsDB = new ArrayList<>();
        // Get all information from mitmproy
        MitMproxyHandler thread1 = new MitMproxyHandler(urls,adsDB);
        thread1.start();
        
        Thread.sleep(10000);
        //init thread to check if urls are or not ad
        
        AdHandler thread2 = new AdHandler(urls,adsDB);
        thread2.start();
        /*
        // init TCP Connection and send it to client
        AndroidConnection thread3 = new AndroidConnection(urls);
        thread3.start();
        */
    }
    
}
