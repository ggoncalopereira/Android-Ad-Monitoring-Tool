/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 *
 * @author JPVS
 */
public class ServerWorker extends Thread{

    TreeSet<String> urls;
    ArrayList<String>  adsDB;
    Socket cs;

    public ServerWorker(Socket s, TreeSet<String> urls, ArrayList<String> adsDB) {
        this.cs = s;
        this.urls = urls;
        this.adsDB = adsDB;
    }
    
    @Override
    public void run(){
        int counter = 0;
        String lineS,line,trim;
        String fileName = "output.txt";
        String fileName2 = "adlist.txt";
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
            while ((lineS = in.readLine()) != null) {
                System.out.println(lineS);
                if(lineS.toLowerCase().equals("start")){
                    // Get all information from mitmproy
                    String command = "cmd /c start " + "mitmdump > output.txt";
                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "mitmdump > output.txt");
                    builder.redirectErrorStream(true);
                    Process pr = builder.start();//rt.exec(command);
                    out.println("Iniciado");
                    out.flush();
                }
                else if(lineS.toLowerCase().equals("stop")){
                    Process rt2 = Runtime.getRuntime().exec("TASKKILL /F /IM mitmdump.exe");
                    
                    //Read Traffic generated from user
                    FileReader fileReader = new FileReader(fileName);
                    
                    try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                        while ((line = bufferedReader.readLine()) != null) {
                            if(line.contains("GET")){
                                trim = line.substring(line.lastIndexOf("GET") + 4);
                                //System.out.println(trim);
                                urls.add(trim);
                            }
                        }
                    }
                    
                    if(!urls.isEmpty()){
                        FileReader fileReader2 = new FileReader(fileName2);
                        //Read AdsDB from file
                        try (BufferedReader bufferedReader2 = new BufferedReader(fileReader2)) {
                            while ((line = bufferedReader2.readLine()) != null) {
                                if(!line.equals("\n")) adsDB.add(line);
                            }
                        }
                        
                        System.out.println("AdsDB list size: " + adsDB.size());

                        out.println("Foram lidos o tráfego e base de dados");
                        out.flush();
                        Iterator<String> iterator = urls.iterator();
                        Iterator<String> iterator2 = adsDB.iterator();
                        
                        while(iterator.hasNext()) {
                            String url = iterator.next();
                            while(iterator2.hasNext()){
                                String ad = iterator2.next();
                                if(url.contains(ad) && (ad.length() > 1)){
                                    System.out.println("URL: " + url);
                                    counter++;
                                    System.out.println("    returned the ad: " + ad);
                                    break;
                                }
                            }
                            iterator2 = adsDB.iterator();
                        }
                        
                        System.out.println("Total amount of " + counter + " ads");
                        
                        System.exit(0);
                    }else{
                        out.println("Sem Trafego");
                        out.flush();
                         System.exit(0);
                    }
                   
                }  
            }
            in.close();
            cs.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
}
