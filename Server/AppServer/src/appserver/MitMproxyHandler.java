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
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author JPVS
 */
public class MitMproxyHandler extends Thread{

    TreeSet<String> urls;
    
    public MitMproxyHandler(TreeSet<String> urls) {
        this.urls = urls;
    }
    
    @Override
    public void run(){

        //to create this file first do mitmdump > output.txt
        //then exectue this
        String fileName = "output.txt";
        String line,trim = null;

        try {
            FileReader fileReader = new FileReader(fileName);

            try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
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
