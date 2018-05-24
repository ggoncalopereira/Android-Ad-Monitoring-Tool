/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author JPVS
 */
public class AdHandler extends Thread{

    TreeSet<String> urls;
    ArrayList<String> adsDB;

    
    public AdHandler(TreeSet<String> urls, ArrayList<String> adsDB) {
        this.urls = urls;
        this.adsDB = adsDB;
    }
    
    @Override
    public void run(){
        
        System.out.println("Handler");
        
        Iterator<String> iterator = urls.iterator();
        Iterator<String> iterator2 = adsDB.iterator();
        
        while(iterator.hasNext()) {
            String url = iterator.next();
            System.out.println("AD: " + url);
            while(iterator2.hasNext()){
                String ad = iterator2.next();
                //System.out.println("AD Compare: " + ad);
                if(url.contains(ad)){
                    System.out.println("Found Ad");
                }
            }
        }  
        System.out.println("Finished");
    }
}
