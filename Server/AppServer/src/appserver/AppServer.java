/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.util.TreeSet;


public class AppServer {

    static TreeSet<String> urls;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         
        urls = new TreeSet<>();
        // Get all information from mitmproy
        MitMproxyHandler thread1 = new MitMproxyHandler(urls);
        thread1.start();
        /*
        // init TCP Connection and send it to client
        AndroidConnection thread2 = new AndroidConnection(urls);
        thread2.start();
        */
    }
    
}
