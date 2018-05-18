/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author JPVS
 */
public class MitMproxyHandler extends Thread{

    TreeSet<String> urls;
    ArrayList<String>  adsDB;
    
    public MitMproxyHandler(TreeSet<String> urls, ArrayList<String> adsDB) {
        this.urls = urls;
        this.adsDB = adsDB;
    }
    
    @Override
    public void run(){

        
        //ads database
        String fileName = "easylist.txt";
        
        //to create this file first do mitmdump > output.txt
        //then execute this
        String fileName2 = "output.txt";
        String line,trim = null;

        try {
            
            FileReader fileReader = new FileReader(fileName);
            
            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                while ((line = bufferedReader.readLine()) != null) {
                    //System.out.println(line);
                    adsDB.add(line);
                }
            }
            
            System.out.println("A ler links visitados");
            
            FileReader fileReader2 = new FileReader(fileName2);
            
            try (BufferedReader bufferedReader = new BufferedReader(fileReader2)) {
                while ((line = bufferedReader.readLine()) != null) {
                    if(line.contains("GET")){
                        trim = line.substring(line.lastIndexOf("GET") + 4);
                        System.out.println(trim);
                        urls.add(trim);
                    }
                    Thread.sleep(80);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MitMproxyHandler.class.getName()).log(Level.SEVERE, null, ex);
            }         
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }           
    }
}
