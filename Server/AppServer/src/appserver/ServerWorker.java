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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.math.BigDecimal;

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
        int total = 0;
        BigDecimal perc;
        String lineS,line,trim;
        String fileName = "output.txt";
        String fileName2 = "adlist.txt";
        String fileTime = "timeStamp.txt";
        Process pr = null;
        Calendar cal = Calendar.getInstance();
        long time_init = 0, time_end;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
            PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
            lineS = in.readLine();
            System.out.println(lineS);
            //while(in.readLine().toLowerCase().equals("stop"))
            if(lineS.toLowerCase().equals("start")){
                // Get all information from mitmproxy
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "mitmdump > output.txt");
                //ProcessBuilder builder = new ProcessBuilder("/bin/bash", "/c", "mitmdump > output.txt");
                builder.redirectErrorStream(true);
                pr = builder.start();
                out.println("Iniciado");
                out.flush();
                time_init = System.currentTimeMillis();
                File file = new File(fileTime);
                try (FileWriter fileWriter = new FileWriter(file)) {
                    String s = Objects.toString(time_init, null);
                    fileWriter.write(s);
                    fileWriter.flush();
                }
                
                System.out.println("Init time: " + time_init);
            }
            else if(lineS.toLowerCase().equals("stop")){
                Process rt2 = Runtime.getRuntime().exec("TASKKILL /F /IM mitmdump.exe");
                // Read Traffic generated from user
                FileReader fileReader = new FileReader(fileName);
                 // Gather GETS
                try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                    while ((line = bufferedReader.readLine()) != null) {
                        if(line.contains("GET")){
                            trim = line.substring(line.lastIndexOf("GET") + 4);
                            //System.out.println(trim);
                            urls.add(trim);
                            total++;
                        }
                    }
                }
                 // Output.txt has URLs
                if(!urls.isEmpty()){
                    FileReader fileReader2 = new FileReader(fileName2);
                    //Read AdsDB from file
                    try (BufferedReader bufferedReader2 = new BufferedReader(fileReader2)) {
                        while ((line = bufferedReader2.readLine()) != null) {
                            if(!line.equals("\n"))
                                adsDB.add(line);
                        }
                    }

                    System.out.println("AdsDB list size: " + adsDB.size());

                    out.println("OK");
                    out.flush();
                    Iterator<String> iterator = urls.iterator();
                    Iterator<String> iterator2 = adsDB.iterator();

                    while(iterator.hasNext()) {
                    String url = iterator.next();
                        while(iterator2.hasNext()){
                        String ad = iterator2.next();
                            if(url.contains(ad) && (ad.length() > 1)) {
                                System.out.println("URL: " + url);
                                counter++;
                                System.out.println("    returned the ad: " + ad);
                                break;
                            }
                        }
                        iterator2 = adsDB.iterator();
                    }

                    System.out.println("Total amount of " + counter + " ads");
                    System.out.println("Total amount of " + total + " gets");

                    double aux = ((float) counter/ (float) total)*100;
                    perc = new BigDecimal(aux);
                    perc = perc.setScale(2, BigDecimal.ROUND_HALF_UP);

                    System.out.println("Percentage " + perc + " %");
                    
                    FileReader fileReader3 = new FileReader(fileTime);
                    try (BufferedReader bufferedReader3 = new BufferedReader(fileReader3)) {
                        while ((line = bufferedReader3.readLine()) != null) {
                            time_init = Long.parseLong(line);
                        }
                    }
                    time_end = System.currentTimeMillis();
                    long time_diff = Math.abs(time_end - time_init);
                    System.out.println("End time: " + time_end);
                    out.println(counter + ";" + total + ";" + perc + ";" + time_diff);
                    System.out.println("Diff time: " + time_diff);
                    out.flush();

                    // Process rt2 = Runtime.getRuntime().exec("TASKKILL /F /IM mitmdump.exe");
                    if(pr != null)
                        pr.destroy();

                in.close();
                cs.close();

                System.exit(0);
                    
                }

                // No URLs
                else {
                    out.println("Sem Trafego");
                    out.flush();
                    System.exit(0);
                }                
            }
                            
            } catch (IOException ex) {  
                Logger.getLogger(ServerWorker.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
}