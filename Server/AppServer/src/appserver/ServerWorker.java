/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


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
        String lineS,line,trim;
        String fileName = "output.txt";
        String fileName2 = "easylist.txt";
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
            while ((lineS = in.readLine()) != null) {
                System.out.println(lineS);
                if(lineS.equals("Start")){
                    // Get all information from mitmproy
                    String command = "cmd /c start " + "mitmdump > output.txt";
                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "mitmdump > output.txt");
                    builder.redirectErrorStream(true);
                    Process pr = builder.start();//rt.exec(command);
                    out.println("Iniciado");
                    out.flush();
                }
                else if(lineS.equals("Stop")){
                    Process rt2 = Runtime.getRuntime().exec("TASKKILL /F /IM mitmdump.exe");
                    
                    //Read Traffic generated from user
                    FileReader fileReader = new FileReader(fileName);
                    
                    try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                        while ((line = bufferedReader.readLine()) != null) {
                            if(line.contains("GET")){
                                trim = line.substring(line.lastIndexOf("GET") + 4);
                                System.out.println(trim);
                                urls.add(trim);
                            }
                        }
                    }
                    
                    if(!urls.isEmpty()){
                        FileReader fileReader2 = new FileReader(fileName2);
                        //Read AdsDB from file
                        try (BufferedReader bufferedReader2 = new BufferedReader(fileReader2)) {
                            while ((line = bufferedReader2.readLine()) != null) {
                                adsDB.add(line);
                            }
                        }
                        out.println("Foram lidos o tráfego e base de dados");
                        out.flush();
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
