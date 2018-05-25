/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appserver;

import java.io.*;
import java.net.Socket;

/**
 *
 * @author JPVS
 */
public class Client{
    
    public static void main(String argv[]) throws IOException, InterruptedException  {
        String answer;
        Socket cs = new Socket("127.0.0.1", 9999);
        
        PrintWriter out = new PrintWriter(cs.getOutputStream(),true);
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(cs.getInputStream()));
        System.out.println("Start para iniciar o MitmProxy e Stop para parar o MitmProxy");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String option = inFromUser.readLine();
        out.println(option);
        out.flush();
        
        while((answer = inFromServer.readLine())!=null){
            if(answer.equals("Iniciado")){
                System.out.print("Setting up MitMProxy");
                long startTime = System.currentTimeMillis();
                long currentTime = System.currentTimeMillis();
                while(currentTime - startTime < 20000){
                    currentTime = System.currentTimeMillis();
                    Thread.sleep(5000);
                    System.out.print(".");
                }
                System.out.println("\nStop para parar o MitmProxy");
                String option2 = inFromUser.readLine();
                out.println(option2);
                out.flush();
            }else{
                System.out.println(answer);
                break;
            }
        }
        cs.shutdownOutput();
        inFromServer.close();
        out.close();
        cs.close();
    }
    
    
}
